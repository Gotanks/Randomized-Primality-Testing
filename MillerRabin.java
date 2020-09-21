import java.io.*;
import java.math.*;
import java.util.Random;

/*
* Sources: 
*     1. https://www.geeksforgeeks.org/primality-test-set-3-miller-rabin/
*     2. https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test#Complexity
* Nature: Probabilistic, Monte Carlo algorithm
* Base Algorithm: 
* If ( (num & 1) == 0 ) i.e. if num is even return false
* Find d such that 
* (num & 1) == 1 ) i.e. d is odd AND (n-1) = d x 2^r where (n-1) is even and r > 0
* Do: Miller test k times on (n,d) 
* Theoretical Time Complexity:  O(k x log^3(n)) | k = number of trials, n = number 
* under consideration
*/

class MillerRabin {

	/**
	 * @param BigInteger num: The number under consideration.
	 * @param BigInteger trials: The numer of trials performed. The bigger this
	 *                   number, the better the odds.
	 * @return boolean: true if proabbly prime.
	 */
	static public boolean isPrime(BigInteger num, BigInteger trials) {
		// Corner cases: If n <= 4, true if n = 2 or n = 3, otherwise false.
		if (num.compareTo(BigInteger.ONE) <= 0 || num.compareTo(Utility.bigInt(4)) == 0)
			return false;
		if (num.compareTo(Utility.bigInt(3)) <= 0)
			return true;
		BigInteger d = num.subtract(BigInteger.ONE);
		while (d.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0)
			d = d.divide(BigInteger.TWO);
		while (trials.compareTo(BigInteger.ZERO) > 0) {
			if (!millerTest(d, num))
				return false;
			trials = trials.subtract(BigInteger.ONE);
		}
		return true;
	}

	/**
	 * @param BigInteger d: d | (n-1) = d x 2^r
	 * @param BigInteger num: The number under consideration.
	 * @return boolean: true if probably prime.
	 */
	private static boolean millerTest(BigInteger d, BigInteger num) {
		// BigInteger randNum = Utility.randomBigInt(num.subtract(Utility.bigInt(4)), new Random()).add(BigInteger.TWO);
		BigInteger randNum = Utility.randomBigInt(num.sqrt(), new Random()).add(BigInteger.TWO);
		BigInteger x = randNum.modPow(d, num);
		if (x.compareTo(BigInteger.ONE) == 0 || x.compareTo(num.subtract(BigInteger.ONE)) == 0)
			return true;
		while (d.compareTo(num.subtract(BigInteger.ONE)) != 0) {
			x = x.multiply(x).mod(num);
			d = d.multiply(BigInteger.TWO);
			if (x.compareTo(BigInteger.ONE) == 0)
				return false;
			if (x.compareTo(num.subtract(BigInteger.ONE)) == 0)
				return true;
		}
		return false;
	}

	/**
	 * @param int num: The number under consideration.
	 * @return boolean: true if prime.
	 */
	static public boolean isPrime(int num, int trials) {
		return isPrime(Utility.bigInt(num), Utility.bigInt(trials));
	}

}