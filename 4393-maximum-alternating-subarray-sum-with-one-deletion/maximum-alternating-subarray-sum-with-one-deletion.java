class Solution {
    public long maxAlternatingSum(int[] nums) {

        final long NEG = Long.MIN_VALUE / 4;

        // dp[deletion][sign]
        //
        // sign = 0 -> next element gets '+'
        // sign = 1 -> next element gets '-'
        long[][] dp = {
            {NEG, NEG},
            {NEG, NEG}
        };

        long ans = NEG;

        for (int x : nums) {

            long[][] ndp = {
                {NEG, NEG},
                {NEG, NEG}
            };

            // Use current element
            for (int del = 0; del <= 1; del++) {

                for (int sign = 0; sign <= 1; sign++) {

                    if (dp[del][sign] == NEG) {
                        continue;
                    }

                    long value = dp[del][sign];

                    if (sign == 0) {

                        // + x
                        ndp[del][1] = Math.max(
                            ndp[del][1],
                            value + x
                        );

                    } else {

                        // - x
                        ndp[del][0] = Math.max(
                            ndp[del][0],
                            value - x
                        );
                    }
                }
            }

            // Delete current element
            // The expected sign does NOT change.
            for (int sign = 0; sign <= 1; sign++) {

                if (dp[0][sign] != NEG) {

                    ndp[1][sign] = Math.max(
                        ndp[1][sign],
                        dp[0][sign]
                    );
                }
            }

            // Start a new subarray with nums[i]
            ndp[0][1] = Math.max(
                ndp[0][1],
                (long) x
            );

            // Update answer
            for (int del = 0; del <= 1; del++) {

                for (int sign = 0; sign <= 1; sign++) {

                    ans = Math.max(
                        ans,
                        ndp[del][sign]
                    );
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
}