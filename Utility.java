import java.io.*; 
import java.math.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;


class Utility {
  /**
  * @param max : Maximum value for a random BigInteger. 
  * @param randomSource: Seed for rng.  
  * @return Random BigInteger of n bits.   
  */
  static BigInteger randomBigInt(BigInteger max, Random randomSource) {
    BigInteger randomNumber, temp;
    int nlen = max.bitLength();
    BigInteger nm1 = max.subtract(BigInteger.ONE);
    do {
        temp = new BigInteger(nlen + 100, randomSource);
        randomNumber = temp.mod(max);
    } while (max.subtract(randomNumber).add(nm1).bitLength() >= nlen + 100);
    return randomNumber;
  }

  static void prompt(BigInteger big){
    System.out.println("Numer Being Tested:\n" + big);
  }

/*
*@param i: integer value. 
*@return BigInteger: return BigInteger equivlaent of i 
*/
static BigInteger bigInt(int i) { return BigInteger.valueOf(i);}

static ConcurrentHashMap<BigInteger, BigInteger> factorialStorage = new ConcurrentHashMap<>();

static BigInteger memoFactorial(BigInteger num) {
	// if (factorialStorage.containsKey(num)) {
	// 	System.out.println("Used a stored value");
	// 	return factorialStorage.get(num);
	// }
	// else {
	// 	BigInteger retVal = num.equals(BigInteger.ONE) ? num : num.multiply(memoFactorial(num.subtract(BigInteger.ONE)));
	// 	factorialStorage.put(num, retVal);
	// 	return retVal;
	// }


	BigInteger val = num;
	BigInteger factorialBigInteger = BigInteger.ONE;
	while (!num.equals(BigInteger.ZERO)) {
		if (factorialStorage.containsKey(num)) {
			// System.out.println("Used a stored value");
			return factorialStorage.get(num).multiply(factorialBigInteger);
		}
		factorialBigInteger = factorialBigInteger.multiply(num);
		num = num.subtract(BigInteger.ONE);
	}
	factorialStorage.put(val, factorialBigInteger);
    return factorialStorage.get(val);


	// return factorialStorage.computeIfAbsent(num, val -> {
	// 	if (val.equals(BigInteger.ONE))
	// 		return val;
	// 	return val.multiply(memoFactorial(val.subtract(BigInteger.ONE)));
	// });
	// factorialStorage.put(num, factorialBigInteger);
	// if (factorialStorage.size() == 10000) {
	// 	factorialStorage.keySet().stream().sorted().limit(9000).forEach(factorialStorage::remove);
	// 	System.out.println("Cleaned factorial storage.  New size: " + factorialStorage.size());
	// }
    // return factorialStorage.get(num);
}

//  TODO:  Replace this with a call to BigIntegerMath.factorial once we add Guava
static BigInteger factorial(BigInteger num) {
	BigInteger factorialBigInteger = BigInteger.ONE;
	while (!num.equals(BigInteger.ZERO)) {
		factorialBigInteger = factorialBigInteger.multiply(num);
		num = num.subtract(BigInteger.ONE);
	}
    return factorialBigInteger;
}

static String formatTime(long time){
    return String.format("%.6fs", time * 1e-9);
}

static long measureTime(Runnable ra) {
    long start = System.nanoTime();
	ra.run();
	return System.nanoTime() - start;
}

static BigInteger pow (int a, int b) {
	return bigInt(a).pow(b);
}

}