
import java.io.*; 
import java.math.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

public interface TestExecutor {
	static void runTest() {
		PrimalityTester.runTest();
	}
	static class PrimalityTester {
    private enum Test {Wilson, BruteForce, JavaProbable, MillerRabin, FermatLittle};
    private static class Results {
			EnumMap<Test, List<Long>> data;
			Results() {
				data = new EnumMap<>(Test.class);
			}
		}

		private static final int NUM_TRIALS = 10;
		private static Set<Test> availableTests;

		private static void runTest() {
			availableTests = Stream.of(Test.values()).collect(Collectors.toCollection(HashSet::new));
			for (int numBits = 4; 
					// numBits <= 48
					!availableTests.isEmpty()
					; ++numBits) {
				// BigInteger trials  = Utility.pow(10, (int) (Math.log(i) / Math.log(2)));
				// BigInteger trials  = BigInteger.ONE.shiftLeft(numBits).sqrt();
				BigInteger trials  = Utility.bigInt((numBits + 1) >> 1);
				Results res = new Results();
				for (int i = 0; i < NUM_TRIALS; ++i) {
					BigInteger numBigInteger = new BigInteger(numBits, new Random()).nextProbablePrime();
					// numBigInteger = numBigInteger.multiply(Utility.bigInt(2)).add(BigInteger.ONE); // Only test odd numbers
					if (numBigInteger.bitLength() > numBits) {
						--i;
						continue;
					}
					test(numBigInteger, trials, res);
				}
				printResult(numBits, res, trials);
				res.data.entrySet().stream().forEach(entry -> {
					if (entry.getValue().stream().mapToLong(l -> l).average().getAsDouble() * 1e-9 > 0.1)
						availableTests.remove(entry.getKey());
				});
			}		
			// Utility.measureTime(() -> System.out.println(new BigInteger("2").pow(1579160).multiply(BigInteger.valueOf(449)).subtract(BigInteger.ONE)));
		}

		private static void printResult(int bits, Results res, BigInteger k) {
			StringBuilder sb = new StringBuilder();
			Function<List<Long>, String> listAvg = list -> Utility.formatTime((long)list.stream().mapToLong(l -> l).average().getAsDouble());
			sb.append(String.format("Bit length:   %s (k = %s)%n", bits, k));
			if (res.data.containsKey(Test.Wilson))
				sb.append(String.format("%-14s%s%n", "Wilson's:", listAvg.apply(res.data.get(Test.Wilson))));
			if (res.data.containsKey(Test.BruteForce))
				sb.append(String.format("%-14s%s%n", "Brute-force:", listAvg.apply(res.data.get(Test.BruteForce))));
			if (res.data.containsKey(Test.JavaProbable))
				sb.append(String.format("%-14s%s%n", "Java:", listAvg.apply(res.data.get(Test.JavaProbable))));
			if (res.data.containsKey(Test.MillerRabin))
				sb.append(String.format("%-14s%s%n", "Miller's:", listAvg.apply(res.data.get(Test.MillerRabin))));
			if (res.data.containsKey(Test.FermatLittle))
				sb.append(String.format("%-14s%s%n", "Fermat's:", listAvg.apply(res.data.get(Test.FermatLittle))));
			System.out.print(sb.append("\n").toString());
		}

		private static void test(BigInteger num, BigInteger trials, Results res){
			// Utility.prompt(num);
			BiFunction<List<Long>, Long, List<Long>> addAndReturn = (list, val) -> {
				list.add(val);
				return list;
			};
			if (availableTests.contains(Test.Wilson))
				res.data.put(Test.Wilson, addAndReturn.apply(res.data.getOrDefault(Test.Wilson, new ArrayList<>()), exec("Wilsons Thm:", WilsonsThm::isPrime, num)));
			if (availableTests.contains(Test.BruteForce))
				res.data.put(Test.BruteForce, addAndReturn.apply(res.data.getOrDefault(Test.BruteForce, new ArrayList<>()), exec("Brute-Force:", BruteForce::isPrime, num)));
			if (availableTests.contains(Test.JavaProbable))
				res.data.put(Test.JavaProbable, addAndReturn.apply(res.data.getOrDefault(Test.JavaProbable, new ArrayList<>()), exec("Java's Miller:", JavaProbablePrime::isPrime, num)));
			if (availableTests.contains(Test.MillerRabin))
				res.data.put(Test.MillerRabin, addAndReturn.apply(res.data.getOrDefault(Test.MillerRabin, new ArrayList<>()), exec("Miller-Rabin:", MillerRabin::isPrime, num, trials)));
			if (availableTests.contains(Test.FermatLittle))
				res.data.put(Test.FermatLittle, addAndReturn.apply(res.data.getOrDefault(Test.FermatLittle, new ArrayList<>()), exec("Fermat's Little:", FermatsLittleThm::isPrime, num, trials)));
		}

		private static long exec(String msg, Function<BigInteger, Boolean> func, BigInteger num) {
			return Utility.measureTime(() -> func.apply(num));
		}

		private static long exec(String msg, BiFunction<BigInteger, BigInteger, Boolean> func, BigInteger num, BigInteger trials) {
			return Utility.measureTime(() -> func.apply(num, trials));
		}
	}
}