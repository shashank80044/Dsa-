import java.util.Arrays;

class Solution {
    private int n;
    private int[][] dp; 

    private int solveForAlice(int[] piles, int person, int i) {
        if (i >= n) return 0;

        if (dp[person][i] != -1) return dp[person][i];

        int result = (person == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int stones = 0;

        // Added boundary check: i + x - 1 < n
        for (int x = 1; x <= 3 && i + x - 1 < n; x++) {
            stones += piles[i + x - 1];

            if (person == 1) {
                result = Math.max(result, stones + solveForAlice(piles, 0, i + x));
            } else {
                result = Math.min(result, solveForAlice(piles, 1, i + x));
            }
        }

        return dp[person][i] = result;
    }

    public String stoneGameIII(int[] stoneValue) {
        n = stoneValue.length;
        int total = 0;
        for (int j = 0; j < n; j++) {
            total += stoneValue[j]; 
        }

        dp = new int[2][n + 1];
        
        // Fixed 2D array initialization loop
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Fixed method call with 3 arguments instead of 4
        int Alice = solveForAlice(stoneValue, 1, 0);
        int Bob = total - Alice;

        if (Alice > Bob) {
            return "Alice";
        } else if (Bob > Alice) {
            return "Bob";
        } else {
            return "Tie";
        }
    }
}