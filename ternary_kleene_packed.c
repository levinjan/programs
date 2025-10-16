// ternary_kleene_packed.c
// Compile with: cc -O2 -Wall ternary_kleene_packed.c -o ternary && ./ternary

#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stddef.h>

// -----------------------------------------------------------------------------
// Encoding (your requested mapping)
// -----------------------------------------------------------------------------
/*
   Bits (b1 b0)  Enc value  Meaning
   0 0 -> 0       0          0
   0 1 -> 1       1          +1
   1 0 -> 2       2          -1
   1 1 -> 3       3          invalid/reserved
*/

// convenience names for encodings
enum { TRIT_ENC_ZERO = 0, TRIT_ENC_PLUS = 1, TRIT_ENC_MINUS = 2, TRIT_ENC_INVALID = 3 };

// -----------------------------------------------------------------------------
// Packed dynamic array type (4 trits per byte)
// -----------------------------------------------------------------------------
typedef struct {
    size_t size;   // number of trits
    uint8_t *data; // bytes, each stores 4 trits (2 bits each)
} trit_array;

// allocate a new trit_array with `n` trits (initialized to zero)
trit_array *trit_array_new(size_t n) {
    trit_array *arr = malloc(sizeof(trit_array));
    if (!arr) return NULL;
    arr->size = n;
    size_t bytes = (n + 3) / 4;
    arr->data = calloc(bytes, 1); // zero => all trits start as 00 (zero)
    if (!arr->data) { free(arr); return NULL; }
    return arr;
}

void trit_array_free(trit_array *arr) {
    if (!arr) return;
    free(arr->data);
    free(arr);
}

// -----------------------------------------------------------------------------
// Low-level access: get/set single trit (encoded form 0..3)
// -----------------------------------------------------------------------------
static inline uint8_t trit_get_enc(const trit_array *arr, size_t idx) {
    if (idx >= arr->size) return TRIT_ENC_INVALID;
    size_t byte_index = idx >> 2;         // /4
    unsigned shift = (unsigned)((idx & 3) * 2);
    return (uint8_t)((arr->data[byte_index] >> shift) & 0x3);
}

static inline void trit_set_enc(trit_array *arr, size_t idx, uint8_t enc) {
    if (idx >= arr->size) return;
    size_t byte_index = idx >> 2;
    unsigned shift = (unsigned)((idx & 3) * 2);
    arr->data[byte_index] &= (uint8_t)~(0x3u << shift);          // clear
    arr->data[byte_index] |= (uint8_t)((enc & 0x3u) << shift);  // set
}

// Helpers to convert from / to human-readable integer (-1,0,1)
static inline int8_t trit_enc_to_int(uint8_t enc) {
    switch (enc & 0x3u) {
        case TRIT_ENC_MINUS: return -1;
        case TRIT_ENC_ZERO:  return 0;
        case TRIT_ENC_PLUS:  return 1;
        default:             return 0; // treat invalid as zero
    }
}
static inline uint8_t trit_int_to_enc(int8_t v) {
    if (v > 0) return TRIT_ENC_PLUS;
    if (v < 0) return TRIT_ENC_MINUS;
    return TRIT_ENC_ZERO;
}

// -----------------------------------------------------------------------------
// Small lookup tables implementing Kleene logic on encoded trits
// We'll index tables with (a * 3 + b) where a,b in {0,1,2} (enc 0..2).
// -----------------------------------------------------------------------------

// rank maps enc -> ordering index for Kleene: -1 < 0 < +1
// enc: 0 -> ZERO  -> rank 1
//      1 -> PLUS  -> rank 2
//      2 -> MINUS -> rank 0
static const uint8_t kleene_rank[4] = {
    1, // enc 0 -> ZERO -> rank 1
    2, // enc 1 -> PLUS -> rank 2
    0, // enc 2 -> MINUS-> rank 0
    1  // enc 3 invalid -> treat as ZERO rank
};

// AND (Kleene): min(a,b) according to ordering -1 < 0 < 1
// table size 9: idx = a*3 + b
static uint8_t kleene_and_table[9];

// OR (Kleene): max(a,b)
static uint8_t kleene_or_table[9];

// NOT (Kleene): 0 -> 0, +1 -> -1, -1 -> +1
static const uint8_t kleene_not_table[4] = {
    TRIT_ENC_ZERO,   // NOT(0) = 0
    TRIT_ENC_MINUS,  // NOT(+1) = -1
    TRIT_ENC_PLUS,   // NOT(-1) = +1
    TRIT_ENC_ZERO    // invalid -> treat as zero
};

// initialize the small tables (call once)
static void init_kleene_tables(void) {
    // Build mapping from enc (0..2) to rank (0..2)
    for (int a = 0; a < 3; ++a) {
        for (int b = 0; b < 3; ++b) {
            uint8_t ra = kleene_rank[a];
            uint8_t rb = kleene_rank[b];
            // AND = smaller rank -> pick the corresponding enc
            if (ra < rb) kleene_and_table[a * 3 + b] = (uint8_t)a;
            else kleene_and_table[a * 3 + b] = (uint8_t)b;
            // OR = larger rank
            if (ra > rb) kleene_or_table[a * 3 + b] = (uint8_t)a;
            else kleene_or_table[a * 3 + b] = (uint8_t)b;
        }
    }
}

// -----------------------------------------------------------------------------
// Scalar trit ops (operate on single encoded trits)
// -----------------------------------------------------------------------------
static inline uint8_t trit_not_enc(uint8_t a_enc) {
    return kleene_not_table[a_enc & 3u];
}

static inline uint8_t trit_and_enc(uint8_t a_enc, uint8_t b_enc) {
    return kleene_and_table[(a_enc & 3u) * 3 + (b_enc & 3u)];
}

static inline uint8_t trit_or_enc(uint8_t a_enc, uint8_t b_enc) {
    return kleene_or_table[(a_enc & 3u) * 3 + (b_enc & 3u)];
}

// -----------------------------------------------------------------------------
// Element-wise array ops (dst <- op(a,b) or dst <- NOT(src))
// They work byte-by-byte, processing 4 packed trits per byte.
// -----------------------------------------------------------------------------

// Element-wise NOT: dst[i] = NOT(src[i])
// If dst == src, it works in-place.
void trit_array_not(trit_array *dst, const trit_array *src) {
    size_t n = src->size;
    size_t bytes = (n + 3) / 4;
    for (size_t bi = 0; bi < bytes; ++bi) {
        uint8_t in = src->data[bi];
        uint8_t out = 0;
        for (unsigned t = 0; t < 4; ++t) {
            size_t trit_index = (bi << 2) + t;
            if (trit_index >= n) break;
            uint8_t enc = (in >> (t * 2)) & 0x3u;
            uint8_t res = trit_not_enc(enc);
            out |= (uint8_t)(res << (t * 2));
        }
        dst->data[bi] = out;
    }
}

// Element-wise AND (dst <- a AND b). Works for overlapping buffers if dst != a && dst != b,
// but it is safe when dst==a or dst==b because we write per-byte independently.
void trit_array_and(trit_array *dst, const trit_array *a, const trit_array *b) {
    size_t n = a->size < b->size ? a->size : b->size;
    size_t bytes = (n + 3) / 4;
    for (size_t bi = 0; bi < bytes; ++bi) {
        uint8_t ba = a->data[bi];
        uint8_t bb = b->data[bi];
        uint8_t out = 0;
        for (unsigned t = 0; t < 4; ++t) {
            size_t trit_index = (bi << 2) + t;
            if (trit_index >= n) break;
            uint8_t enca = (ba >> (t * 2)) & 0x3u;
            uint8_t encb = (bb >> (t * 2)) & 0x3u;
            uint8_t res = trit_and_enc(enca, encb);
            out |= (uint8_t)(res << (t * 2));
        }
        dst->data[bi] = out;
    }
}

// Element-wise OR
void trit_array_or(trit_array *dst, const trit_array *a, const trit_array *b) {
    size_t n = a->size < b->size ? a->size : b->size;
    size_t bytes = (n + 3) / 4;
    for (size_t bi = 0; bi < bytes; ++bi) {
        uint8_t ba = a->data[bi];
        uint8_t bb = b->data[bi];
        uint8_t out = 0;
        for (unsigned t = 0; t < 4; ++t) {
            size_t trit_index = (bi << 2) + t;
            if (trit_index >= n) break;
            uint8_t enca = (ba >> (t * 2)) & 0x3u;
            uint8_t encb = (bb >> (t * 2)) & 0x3u;
            uint8_t res = trit_or_enc(enca, encb);
            out |= (uint8_t)(res << (t * 2));
        }
        dst->data[bi] = out;
    }
}

// -----------------------------------------------------------------------------
// Constructor: set target trit = AND(AND_inputs) AND OR(OR_inputs)
// Inputs are arrays of indices (size_t). If an input index is out of range, it's ignored.
// -----------------------------------------------------------------------------
void trit_construct(trit_array *arr, size_t target,
                    const size_t *and_inputs, size_t and_count,
                    const size_t *or_inputs, size_t or_count)
{
    if (target >= arr->size) return;

    // initial aggregators:
    // AND aggregator starts as TRUE (+1) -> encoded as TRIT_ENC_PLUS
    uint8_t and_agg = TRIT_ENC_PLUS;
    for (size_t i = 0; i < and_count; ++i) {
        size_t idx = and_inputs[i];
        if (idx >= arr->size) continue;
        uint8_t src = trit_get_enc(arr, idx);
        and_agg = trit_and_enc(and_agg, src);
    }

    // OR aggregator starts as FALSE (-1) -> encoded as TRIT_ENC_MINUS
    uint8_t or_agg = TRIT_ENC_MINUS;
    for (size_t i = 0; i < or_count; ++i) {
        size_t idx = or_inputs[i];
        if (idx >= arr->size) continue;
        uint8_t src = trit_get_enc(arr, idx);
        or_agg = trit_or_enc(or_agg, src);
    }

    // final = AND(and_agg, or_agg)
    uint8_t final_enc = trit_and_enc(and_agg, or_agg);
    trit_set_enc(arr, target, final_enc);
}

// -----------------------------------------------------------------------------
// Example / test
// -----------------------------------------------------------------------------
static void print_trit_array(const trit_array *a, const char *label) {
    printf("%s: [", label ? label : "");
    for (size_t i = 0; i < a->size; ++i) {
        int8_t val = trit_enc_to_int(trit_get_enc(a, i));
        printf("%2d", val);
        if (i + 1 < a->size) putchar(',');
    }
    printf("]\n");
}

int main(void) {
    init_kleene_tables();

    // Create a small example
    trit_array *A = trit_array_new(12);
    trit_array *B = trit_array_new(12);
    trit_array *C = trit_array_new(12);

    // set some base values (by integer)
    trit_set_enc(A, 0, trit_int_to_enc(-1));
    trit_set_enc(A, 1, trit_int_to_enc(0));
    trit_set_enc(A, 2, trit_int_to_enc(1));
    trit_set_enc(A, 3, trit_int_to_enc(1));
    trit_set_enc(A, 4, trit_int_to_enc(0));
    trit_set_enc(A, 5, trit_int_to_enc(-1));

    // B = NOT(A)
    trit_array_not(B, A);

    // C = A AND B (element-wise)
    trit_array_and(C, A, B);

    print_trit_array(A, "A");
    print_trit_array(B, "NOT(A)");
    print_trit_array(C, "A AND NOT(A)");

    // Construct an example trit using lists: target index 6
    // and_inputs = {0,1} (AND of [-1,0] -> -1), or_inputs = {2,3} (OR of [1,1] -> 1)
    size_t and_inputs[] = {0, 1};
    size_t or_inputs[]  = {2, 3};
    trit_construct(A, 6, and_inputs, 2, or_inputs, 2);

    print_trit_array(A, "A after construct (index 6 set)");

    // free
    trit_array_free(A);
    trit_array_free(B);
    trit_array_free(C);

    return 0;
}
