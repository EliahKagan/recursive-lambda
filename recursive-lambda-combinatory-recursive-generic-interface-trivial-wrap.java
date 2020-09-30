import java.util.function.IntUnaryOperator;

@FunctionalInterface
interface SelfApplicator<T, R> {
    R apply(SelfApplicator<T, R> me, T n);
}

final class RecursiveLambdaCombinatoryRecursiveGenericInterfaceTrivialWrap {
    public static void main(final String[] args) {
        IntUnaryOperator fib = n -> {
            SelfApplicator<Integer, Integer> f =
				(me, k) -> k < 2 ? k : me.apply(me, k - 2) + me.apply(me, k - 1);

            return f.apply(f, n);
        };

        System.out.println(fib.applyAsInt(10));
    }
}
