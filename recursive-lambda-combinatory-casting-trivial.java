import java.util.function.BiFunction;

final class RecursiveLambdaCombinatoryTrivial {
    public static void main(final String[] args) {
        BiFunction<Object, Integer, Integer> fib = (me, n) -> {
            @SuppressWarnings("unchecked")
            var me_casted = (BiFunction<Object, Integer, Integer>)me;
            return n < 2 ? n : me_casted.apply(me, n - 2) + me_casted.apply(me, n - 1);
        };

        System.out.println(fib.apply(fib, 10));
    }
}
