import sys
import random


def primeTest(n: int):
    isPrime = True
    sqrt = n**(0.5)
    if n == 2:
        return True
    elif ((n % 2) == 0) or (n == 1):
        isPrime = False
    i = 3
    while isPrime and i <= sqrt:
        if (n % i) == 0:
            isPrime = False
        i += 2
    return isPrime


def gcdExtended(a, b):
    if (b == 0):
        return (a, 1, 0)
    else:
        answerp = gcdExtended(b, a % b)
        q = a//b
        gcd = answerp[0]
        # ax + by
        y = answerp[2]
        x = answerp[1] - q*answerp[2]
        return gcd, x, y


def findRandomPrime(lowerBound: int, upperBound: int):
    isPPrime = False
    p = 0
    while not isPPrime:
        if primeTest(p):
            isPPrime = True
        else:
            p = random.randint(lowerBound, upperBound)
    return p


def findE(m: int):
    e = 3
    while gcdExtended(m, e)[0] != 1:
        e += 2
    return e


def cipherText(x: int, e: int, n: int):
    return pow(x, e, n)


def decipherText(y: int, d: int, n: int):
    return pow(y, d, n)


def findPQValues(lowerBound: int, upperBound: int):
    return findRandomPrime(lowerBound, upperBound), findRandomPrime(lowerBound, upperBound)


def RSA():

    rango = [sys.argv[1], sys.argv[2], sys.argv[3]]
    lowerBound = 2**int(rango[0])
    upperBound = 2**int(rango[1])
    isText = False
    intToCipher = None
    try:
        intToCipher = int(sys.argv[3])
    except ValueError:
        print(f"input {sys.argv[3]} is not an integer, using text mode")
    finally:
        # max 6 letras please
        intToCipher = int.from_bytes(sys.argv[3].encode("ascii"), "little")
        isText = True
    print(f"lower bound for p and q: {lowerBound}")
    print(f"upper bound for p and q: {upperBound}")
    if isText:
        print(f"input received: {sys.argv[3]}")
    else:
        print(f"input received: {intToCipher}")
    print("-----------------------------------------------------------------")
    p, q = findPQValues(lowerBound, upperBound)
    print(f"value for p: {p}")
    print(f"value for q: {q}")
    n = p*q
    print(f"value for n=p*q: {n}")
    m = (p-1)*(q-1)
    print(f"value for m=(p-1)*(q-1): {m}")
    e = findE(m)
    print(f"value for e: {e}")
    d = pow(e, -1, m)
    print(f"value for d: {d}")
    print("-----------------------------------------------------------------")
    cipheredInt = cipherText(intToCipher, e, n)
    decipheredInt = decipherText(cipheredInt, d, n)
    print(f"encrypted Value: {cipheredInt}")
    if isText:
        decipheredText = decipheredInt.to_bytes(sys.getsizeof(decipheredInt), "little").decode("ascii")
        print(f"de-encrypted Text: {decipheredText}")
    else:
        print(f"de-encrypted Value: {decipheredInt}")


RSA()
