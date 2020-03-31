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

        auto prehash = seed;
        prehash = prehash * multiplier + std::hash<cpp_int>{}(base);
        prehash = prehash * multiplier + exponent;
        return prehash;
    };

    std::unordered_map<std::tuple<cpp_int, unsigned>, cpp_int, decltype(hash)>
    memo {8, hash};

    std::function<cpp_int(cpp_int, unsigned)>
    my_pow = [&memo, &my_pow](const cpp_int& base, unsigned exponent) {
        if (exponent == 0) return cpp_int{1};

        auto key = std::tuple{base, exponent};
        auto p = memo.find(key);
        if (p != end(memo)) return p->second;

        auto result = my_pow(base, exponent / 2);
        result *= result;
        if (exponent % 2 != 0) result *= base;

        memo.emplace(key, result);
        return result;
    };

    auto ans = my_pow(7, 1013);
    std::cout << "Computed value: " << ans << '\n';

    auto known = pow(cpp_int{7}, 1013);
    std::cout << "Accepted value: " << known << '\n';

    std::cout << "Correct?  " << std::boolalpha << (ans == known) << '\n';
}
