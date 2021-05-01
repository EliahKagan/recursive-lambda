// Effectively recursive lambda in Java, "combinator" technique via nongeneric
// recursive interface, trivial example, wrapped
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
