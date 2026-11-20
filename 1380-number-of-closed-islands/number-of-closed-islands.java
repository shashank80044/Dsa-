class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 0) {
                    if (dfs(grid, i, j)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public boolean dfs(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        // Out of bounds means this island touches the boundary
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return false;
        }

        // Water or already visited
        if (grid[i][j] == 1) {
            return true;
        }

        // Mark this land cell as visited
        grid[i][j] = 1;

        // Explore all four directions
        boolean up = dfs(grid, i - 1, j);
        boolean down = dfs(grid, i + 1, j);
        boolean left = dfs(grid, i, j - 1);
        boolean right = dfs(grid, i, j + 1);

        return up && down && left && right;
    }
}