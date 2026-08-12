class Solution {
     int[][] t;
    public int solve(int i, int j, int[] nums) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return nums[i];
        }
         
        if (t[i][j] != -1) {
            return t[i][j];
        } 

        int takei = nums[i] + Math.min(solve(i + 2, j, nums), solve(i + 1, j - 1, nums));
        int takej = nums[j] + Math.min(solve(i, j - 2, nums), solve(i + 1, j - 1, nums));
        return t[i][j] = Math.max(takei, takej);
    }

    public boolean stoneGame(int[] piles) {
        int n = piles.length;
         
         t = new int[n][n];

       for (int i = 0; i < n; i++) {
            Arrays.fill(t[i], -1);
        }
        int total = Arrays.stream(piles).sum();

        int player1 = solve(0, n - 1, piles);
        int player2 = total - player1;

        return player1 >= player2;
    }
}