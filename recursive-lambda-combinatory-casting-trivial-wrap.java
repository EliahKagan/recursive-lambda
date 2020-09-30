import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

final class RecursiveLambdaCombinatoryCastingTrivialWrap {
    public static void main(final String[] args) {
        IntUnaryOperator fib = n -> {
            BiFunction<Object, Integer, Integer> f = (me, k) -> {
                @SuppressWarnings("unchecked")
                var me_casted = (BiFunction<Object, Integer, Integer>)me;
                return k < 2 ? k : me_casted.apply(me, k - 2) + me_casted.apply(me, k - 1);
            };

            return f.apply(f, n);
        };

        System.out.println(fib.applyAsInt(10));
    }
}
