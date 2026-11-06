
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs according to request time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Store queries with their original indices
        int[][] q = new int[queries.length][2];

        for (int i = 0; i < queries.length; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        // Sort queries according to their time
        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        int[] ans = new int[queries.length];

        // Frequency of requests for each server
        int[] freq = new int[n + 1];

        // Number of distinct servers receiving requests
        int active = 0;

        int left = 0;
        int right = 0;

        for (int i = 0; i < queries.length; i++) {

            int start = q[i][0] - x;
            int end = q[i][0];

            // Add logs inside the current window
            while (right < logs.length && logs[right][1] <= end) {

                int server = logs[right][0];

                if (freq[server] == 0) {
                    active++;
                }

                freq[server]++;
                right++;
            }

            // Remove logs that are before the window
            while (left < right && logs[left][1] < start) {

                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    active--;
                }

                left++;
            }

            // Servers with zero requests
            ans[q[i][1]] = n - active;
        }

        return ans;
    }
}
