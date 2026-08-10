import java.util.Arrays;

class Solution {

    public int solve(int i, int j, int[] nums) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return nums[i];
        }

        int take_i = nums[i] + Math.min(solve(i + 2, j, nums), solve(i + 1, j - 1, nums));
        int take_j = nums[j] + Math.min(solve(i, j - 2, nums), solve(i + 1, j - 1, nums));

        return Math.max(take_i, take_j);
    }

    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;

        int totalScore = Arrays.stream(nums).sum();

        int player1Score = solve(0, n - 1, nums);
        int player2Score = totalScore - player1Score;

        return player1Score >= player2Score;
    }
}