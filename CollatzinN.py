import math

limit = 6


def collatz(input_value):
    counter = 0
    c = input_value
    while c != 1:
        if c % 2 == 0:
            c /= 2
            counter += 1
        else:
            c = c * 3 + 1
    return counter


def map_to_1(number):
    return math.log2(number) % 1


count = dict()
for i in range(1, 2 ** limit + 1):
    a = map_to_1(i)
    if a not in count.keys():
        count[a] = collatz(i)
print(len(count))
for key in sorted(count.keys()):
    print(key, count[key])
