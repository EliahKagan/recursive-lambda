<Query Kind="Statements">
  <Namespace>System.Numerics</Namespace>
</Query>

// Effectively recursive lambda in C#, "combinator" technique via dynamic
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

var memo = new Dictionary<(BigInteger, int), BigInteger>();

Func<dynamic, BigInteger, int, BigInteger> pow = (me, @base, exponent) => {
    if (exponent <= 0) {
        if (exponent == 0) return BigInteger.One;

        throw new ArgumentOutOfRangeException(
                paramName: nameof(exponent),
                message: "Negative exponents are not supported.");
    }

    if (memo.TryGetValue((@base, exponent), out var result)) return result;

    result = me(me, @base, exponent / 2);
    result *= result;
    if (exponent % 2 != 0) result *= @base;

    memo.Add((@base, exponent), result);
    return result;
};

var ans = pow(pow, 7, 1013);
ans.Dump("computed value");

var known = BigInteger.Pow(7, 1013);
known.Dump("accepted value");

(ans == known).Dump("correct?");
