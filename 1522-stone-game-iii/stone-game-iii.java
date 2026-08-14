import java.util.Arrays;

class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        // dp[i] stores the maximum score advantage (Current Player - Opponent) 
        // starting from index i to the end.
        int[] dp = new int[n + 1];
        
        // Traverse backwards from the last stone
        for (int i = n - 1; i >= 0; i--) {
            int maxAdvantage = Integer.MIN_VALUE;
            int currentTakeSum = 0;
            
            // Try taking 1, 2, or 3 stones
            for (int k = 0; k < 3 && i + k < n; k++) {
                currentTakeSum += stoneValue[i + k];
                int opponentAdvantage = dp[i + k + 1];
                maxAdvantage = Math.max(maxAdvantage, currentTakeSum - opponentAdvantage);
            }
            
            dp[i] = maxAdvantage;
        }
        
        // dp[0] represents Alice's relative score advantage over Bob from the start
        if (dp[0] > 0) return "Alice";
        if (dp[0] < 0) return "Bob";
        return "Tie";
    }
}