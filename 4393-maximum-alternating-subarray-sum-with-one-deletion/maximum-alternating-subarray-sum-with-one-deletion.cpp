class Solution {
public:
    long long maxAlternatingSum(vector<int>& nums) {
        const long long NEG = LLONG_MIN / 4;

        // dp[deletion][sign]
        // sign = 0 -> next element gets '+'
        // sign = 1 -> next element gets '-'
        long long dp[2][2] = {
            {NEG, NEG},
            {NEG, NEG}
        };

        long long ans = NEG;

        for (long long x : nums) {

            long long ndp[2][2] = {
                {NEG, NEG},
                {NEG, NEG}
            };

            // Use current element
            for (int del = 0; del <= 1; del++) {
                for (int sign = 0; sign <= 1; sign++) {

                    if (dp[del][sign] == NEG)
                        continue;

                    long long value = dp[del][sign];

                    if (sign == 0) {
                        // + x
                        ndp[del][1] =
                            max(ndp[del][1], value + x);
                    } 
                    else {
                        // - x
                        ndp[del][0] =
                            max(ndp[del][0], value - x);
                    }
                }
            }

            // Delete current element
            // The expected sign does NOT change.
            for (int sign = 0; sign <= 1; sign++) {
                if (dp[0][sign] != NEG) {
                    ndp[1][sign] =
                        max(ndp[1][sign], dp[0][sign]);
                }
            }

            // Start a new subarray with nums[i]
            ndp[0][1] = max(ndp[0][1], x);

            // Update answer
            for (int del = 0; del <= 1; del++) {
                for (int sign = 0; sign <= 1; sign++) {
                    ans = max(ans, ndp[del][sign]);
                }
            }

            // Move to next element
            for (int del = 0; del <= 1; del++) {
                for (int sign = 0; sign <= 1; sign++) {
                    dp[del][sign] = ndp[del][sign];
                }
            }
        }

        return ans;
    }
};