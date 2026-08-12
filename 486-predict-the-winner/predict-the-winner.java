import java.util.*;

class Solution {

    int[][] t;

    int solve(int i, int j, int[] nums) {

        if (i > j) {
            return 0;
        }

     
        if (i == j) {
            return nums[i];
        }

        
        if (t[i][j] != -1) {
            return t[i][j];
        }

        int take_i = nums[i] + Math.min(
            solve(i + 2, j, nums),
            solve(i + 1, j - 1, nums)
        );

        
        int take_j = nums[j] + Math.min(
            solve(i, j - 2, nums),
            solve(i + 1, j - 1, nums)
        );

       
        return t[i][j] = Math.max(take_i, take_j);
    }

    public boolean predictTheWinner(int[] nums) {

        int n = nums.length;

       
        t = new int[n][n];

  
        for (int i = 0; i < n; i++) {
            Arrays.fill(t[i], -1);
        }

     
        int total_score = 0;

        for (int num : nums) {
            total_score += num;
        }

        int player1_score = solve(0, n - 1, nums);

        
        int player2_score = total_score - player1_score;

        return player1_score >= player2_score;
    }
}