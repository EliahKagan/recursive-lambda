@FunctionalInterface
interface SelfApplicator<T, R> {
    R apply(SelfApplicator<T, R> me, T arg);
}

final class RecursiveLambdaCombinatoryRecursiveGenericInterfaceTrivial {
    public static void main(final String[] args) {
        SelfApplicator<Integer, Integer> fib =
            (me, n) -> n < 2 ? n : me.apply(me, n - 2) + me.apply(me, n - 1);

        System.out.println(fib.apply(fib, 10));
    }
}
