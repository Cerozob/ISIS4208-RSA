import datetime
import sys


def findAllPrimesaUpToN(n: int):
    primes = [2]
    currentPrime = 3
    while currentPrime <= n:
        isPrime = True
        for prime in primes:
            if (currentPrime % prime) == 0:
                isPrime = False
        if isPrime:
            primes.append(currentPrime)
        currentPrime += 2
    return primes


inputnumber = sys.argv[1]
before = datetime.datetime.now()
primelist = findAllPrimesaUpToN(int(inputnumber))
after = datetime.datetime.now()
total = after-before
print(f"total time taken: {total}")
print("prime list:")
for prime in primelist:
    print(prime)
