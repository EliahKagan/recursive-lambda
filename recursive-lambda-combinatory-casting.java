import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

@FunctionalInterface
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

final class RecursiveLambdaCombinatoryCasting {
    private static final class Key {
        Key(BigInteger base, int exponent) {
            if (base == null) throw new NullPointerException();
            _base = base;
            _exponent = exponent;
        }

        public boolean equals(Key rhs) {
            return rhs != null && _base.equals(rhs._base)
                               && _exponent == rhs._exponent;
        }

        @Override
        public boolean equals(Object other) {
            try {
                return equals((Key)other);
            } catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(_base, _exponent);
        }

        final BigInteger _base;

        final int _exponent;
    }

    public static void main(String[] args) {
        var memo = new HashMap<Key, BigInteger>();

        TriFunction<Object, BigInteger, Integer, BigInteger> pow
                = (me, base, exponent) -> {
            if (exponent <= 0) {
                if (exponent == 0) return BigInteger.ONE;

                throw new IllegalArgumentException(
                        "Negative exponents are not supported.");
            }

            var key = new Key(base, exponent);
            var value = memo.getOrDefault(key, null);
            if (value != null) return value;

            @SuppressWarnings("unchecked")
            var me_casted =
                (TriFunction<Object, BigInteger, Integer, BigInteger>)me;

            value = me_casted.apply(me, base, exponent / 2);
            value = value.multiply(value);
            if (exponent % 2 != 0) value = value.multiply(base);

            memo.put(key, value);
            return value;
        };

        var ans = pow.apply(pow, BigInteger.valueOf(7), 1013);
        System.out.format("Computed value:%n%s%n%n", ans);

        var known = BigInteger.valueOf(7).pow(1013);
        System.out.format("Accepted value:%n%s%n%n", known);

        System.out.format("Correct?%n%b%n", ans.equals(known));
    }
}
