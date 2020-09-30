<Query Kind="Program">
  <Namespace>System.Numerics</Namespace>
</Query>

delegate TResult SelfApplicator<T1, T2, TResult>(SelfApplicator<T1, T2, TResult> me, T1 arg1, T2 arg2);

private static void Main()
{
    var memo = new Dictionary<(BigInteger, int), BigInteger>();
    
    SelfApplicator<BigInteger, int, BigInteger> pow = (me, @base, exponent) => {
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
}
