import math

Primes = [2, 3, 5]


def FindNext():
    candidate = Primes[-1]
    lenght = len(Primes)

    while (lenght == len(Primes)):
        prime = True
        max = int(math.ceil(math.sqrt(candidate)))
        candidate += 2
        x = 1
        while max >= Primes[x]:
            if candidate % Primes[x] == 0:
                prime = False
                break
            else:
                x += 1
        if prime:
            Primes.append(candidate)

def findbigger(p):
    for x in Primes:
        if x > p:
            return x
    while (Primes[-1] < p):
        FindNext()
    return Primes[-1]


print(findbigger(100))
print(Primes)
