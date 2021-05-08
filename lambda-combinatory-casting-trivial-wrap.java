// Effectively recursive lambda in Java, "combinator" technique via casting,
// trivial example, wrapped
//
// Copyright (c) 2020 Eliah Kagan
//
// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
// SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION
// OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN
// CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

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
