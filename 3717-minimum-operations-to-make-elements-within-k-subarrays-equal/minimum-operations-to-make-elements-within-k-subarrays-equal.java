
import java.util.*;

class Solution {

    class Fenwick {
        int n;
        int[] count;
        long[] sum;

        Fenwick(int n) {
            this.n = n;
            count = new int[n + 1];
            sum = new long[n + 1];
        }

        void update(int i, int val, int freq) {
            while (i <= n) {
                count[i] += freq;
                sum[i] += (long) val * freq;
                i += i & -i;
            }
        }

        int queryCount(int i) {
            int ans = 0;

            while (i > 0) {
                ans += count[i];
                i -= i & -i;
            }

            return ans;
        }

        long querySum(int i) {
            long ans = 0;

            while (i > 0) {
                ans += sum[i];
                i -= i & -i;
            }

            return ans;
        }

        // Find the index of the kth smallest element
        int kth(int k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && count[next] < k) {
                    idx = next;
                    k -= count[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[m++] = sorted[i];
            }
        }

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            ) + 1;
        }

        // Calculate cost of every window
        Fenwick bit = new Fenwick(m);

        long[] cost = new long[n - x + 1];

        // Insert first window
        for (int i = 0; i < x; i++) {
            bit.update(rank[i], nums[i], 1);
        }

        for (int i = 0; i <= n - x; i++) {

            // Find median
            int mid = bit.kth((x + 1) / 2);
            long median = sorted[mid - 1];

            // Sum of elements <= median
            int leftCount = bit.queryCount(mid);
            long leftSum = bit.querySum(mid);

            // Sum of all elements
            long totalSum = bit.querySum(m);

            int rightCount = x - leftCount;
            long rightSum = totalSum - leftSum;

            // Calculate minimum operations
            cost[i] =
                median * leftCount - leftSum
                + rightSum - median * rightCount;

            // Slide the window
            if (i < n - x) {

                bit.update(rank[i], nums[i], -1);

                bit.update(
                    rank[i + x],
                    nums[i + x],
                    1
                );
            }
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                // Choice 1: Skip current element
                dp[i][j] = dp[i - 1][j];

                // Choice 2: Select a window of size x
                if (i >= x && dp[i - x][j - 1] != INF) {

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
