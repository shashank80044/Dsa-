class Solution {
    public int stoneGameVIII(int[] stones) {

        int n = stones.length;

        // prefix sum
        int[] prefix = new int[n];

        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // Start from the last possible prefix
        int dp = prefix[n - 1];

        // Work backwards
        for (int i = n - 2; i >= 1; i--) {
            dp = Math.max(dp, prefix[i] - dp);
        }

        return dp;
    }
}