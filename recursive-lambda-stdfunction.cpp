#include <cstddef>
#include <functional>
#include <iostream>
#include <iterator>
#include <tuple>
#include <unordered_map>
#include <boost/multiprecision/cpp_int.hpp>

int main()
{
    using boost::multiprecision::cpp_int;

    constexpr auto hash = [](const std::tuple<cpp_int, unsigned>& key) {
        constexpr std::size_t seed {127}, multiplier {131'071};
        const auto& [base, exponent] = key;

        auto code = seed;
        code = code * multiplier + std::hash<cpp_int>{}(base);
        code = code * multiplier + exponent;
        return code;
    };

    std::unordered_map<std::tuple<cpp_int, unsigned>, cpp_int, decltype(hash)>
    memo {8, hash};

    std::function<cpp_int(cpp_int, unsigned)>
    my_pow = [&memo, &my_pow](const cpp_int& base, unsigned exponent) {
        if (exponent == 0) return cpp_int{1};

        auto p = memo.find({base, exponent});
        if (p != end(memo)) return p->second;

        auto result = my_pow(base, exponent / 2);
        result *= result;
        if (exponent % 2 != 0) result *= base;

        memo[{base, exponent}] = result;
        return result;
    };

    cpp_int ans = my_pow(7, 1013);
    std::cout << "Computed value: " << ans << '\n';

    cpp_int known = pow(cpp_int{7}, 1013);
    std::cout << "Accepted value: " << known << '\n';

    std::cout << "Correct?  " << std::boolalpha << (ans == known) << '\n';
}
