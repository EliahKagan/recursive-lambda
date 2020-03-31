import java.util.function.IntUnaryOperator;

final class RecursiveAnonymousClassTrivialUser {
    public static void main(final String[] args) {
        var fib = new IntUnaryOperator() {
            @Override
            public int applyAsInt(int n) {
                return n < 2 ? n : applyAsInt(n - 2) + applyAsInt(n - 1);
            }
        };

        System.out.println(fib.applyAsInt(10));
    }
}
