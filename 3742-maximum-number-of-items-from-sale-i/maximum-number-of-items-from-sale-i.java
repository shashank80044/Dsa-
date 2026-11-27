class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int[] dp = new int[budget + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            minPrice = Math.min(minPrice, price);

            // Count items that can be obtained for free,
            // including the purchased item itself.
            int cnt = 0;

            for (int[] other : items) {
                if (other[0] % factor == 0) {
                    cnt++;
                }
            }

            // 0/1 Knapsack: buy the first copy of this item.
            for (int j = budget; j >= price; j--) {
                dp[j] = Math.max(dp[j], dp[j - price] + cnt);
            }
        }

        int ans = 0;

        // Spend the remaining budget on the cheapest item.
        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans, dp[i] + (budget - i) / minPrice);
        }

        return ans;
    }
}