import java.math.BigInteger;
/*
* Sources: 
*     1. https://www.geeksforgeeks.org/aks-primality-test/
*     2. https://en.wikipedia.org/wiki/AKS_primality_test#History_and_running_time
* Nature: Deterministic 
* Theoretical Time Complexity : [O(log(n)^6), O(log(n)^12)] ~= O(log(n)^10.5) 
*/
class AgrawalKayalSaxena {
  /**
   * @param BigInteger num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(BigInteger num){
      if(num.mod(Utility.bigInt(2)).equals(BigInteger.ZERO))
        return false;
      for (int i = 3; i * i <= num.intValue(); i=i+2){
        if (num.mod(BigInteger.valueOf(i)).equals(BigInteger.ZERO))
          return false;
      }
      return true;
  }

  static boolean AKSPrimalityTest(BigInteger num){
    if (num.compareTo(Utility.bigInt(1) == 0 || num.compareTo(Utility.bigInt(1)) == -1))
      return false;
    if (num.compareTo(Utility.bigInt(3) == 0 || num.compareTo(Utility.bigInt(3) == -1)))
      return true;
    if (num.mod(Utility.bigInt(2)).equals(BigInteger.ZERO) || num.mod(Utility.bigInt(3)).equals(BigInteger.ZERO))
      return false;
    for (long i = 5; i * i < num.intValue(); i+=6){
      if (num.intValue() % i == 0 || num.intValue() % (i + 2) == 0)
        return false;
    }
    return true;
  }

  /**
   * @param int num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(int num){
    return isPrime(Utility.bigInt(num)); 
  }
} 