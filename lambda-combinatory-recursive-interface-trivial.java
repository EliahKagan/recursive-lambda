// Effectively recursive lambda in Java, "combinator" technique via nongeneric
// recursive interface, trivial example
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
