@FunctionalInterface
interface CombinatoryFib {
    int apply(CombinatoryFib me, int n);
}

final class RecursiveLambdaCombinatoryRecursiveInterfaceTrivial {
    public static void main(final String[] args) {
        CombinatoryFib fib =
            (me, n) -> n < 2 ? n : me.apply(me, n - 2) + me.apply(me, n - 1);

        System.out.println(fib.apply(fib, 10));
    }
}
