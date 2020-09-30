import java.util.function.IntUnaryOperator;

@FunctionalInterface
interface CombinatoryFib {
    int apply(CombinatoryFib me, int n);
}

final class RecursiveLambdaCombinatoryRecursiveInterfaceTrivialWrap {
    public static void main(final String[] args) {
        IntUnaryOperator fib = n -> {
            CombinatoryFib f =
				(me, k) -> k < 2 ? k : me.apply(me, k - 2) + me.apply(me, k - 1);

            return f.apply(f, n);
        };

        System.out.println(fib.applyAsInt(10));
    }
}
