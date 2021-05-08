// Effectively recursive lambda in C++, "combinator" technique
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

#include <cstddef>
#include <iostream>
#include <iterator>
#include <tuple>
#include <unordered_map>
#include <boost/multiprecision/cpp_int.hpp>

int main()
{
    using boost::multiprecision::cpp_int;
    using Key = std::tuple<cpp_int, unsigned>;

    constexpr auto hash = [](const Key& key) {
        constexpr std::size_t seed {127}, multiplier {131'071};
        const auto& [base, exponent] = key;

        auto code = seed;
        code = code * multiplier + std::hash<cpp_int>{}(base);
        code = code * multiplier + exponent;
        return code;
    };

    std::unordered_map<Key, cpp_int, decltype(hash)> memo;

    auto my_pow =
    [&memo](const auto& me, const cpp_int& base, unsigned exponent) {
        if (exponent == 0) return cpp_int{1};

        auto p = memo.find({base, exponent});
        if (p != end(memo)) return p->second;

        auto result = me(me, base, exponent / 2);
        result *= result;
        if (exponent % 2 != 0) result *= base;

        memo[{base, exponent}] = result;
        return result;
    };

    cpp_int ans = my_pow(my_pow, 7, 1013);
    std::cout << "Computed value: " << ans << '\n';

    cpp_int known = pow(cpp_int{7}, 1013);
    std::cout << "Accepted value: " << known << '\n';

    std::cout << "Correct?  " << std::boolalpha << (ans == known) << '\n';
}
