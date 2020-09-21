import java.math.BigInteger;
// Probabilistic 
class JavaProbablePrime{

   /**
   * @param BiInteger num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(BigInteger num){
    return num.isProbablePrime(1); 
  }

  /**
   * @param int num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(int num){
    return isPrime(Utility.bigInt(num)); 
  }
}