<Query Kind="Statements">
  <Namespace>System.Numerics</Namespace>
</Query>

var memo = new Dictionary<(BigInteger, int), BigInteger>();

Func<object, BigInteger, int, BigInteger> pow = (me, @base, exponent) => {
    if (exponent <= 0) {
        if (exponent == 0) return BigInteger.One;
        
        throw new ArgumentOutOfRangeException(
                paramName: nameof(exponent),
                message: "Negative exponents are not supported.");
    }
    
    if (memo.TryGetValue((@base, exponent), out var result)) return result;
    
    var me_casted = (Func<object, BigInteger, int, BigInteger>)me;
    result = me_casted(me, @base, exponent / 2);
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
