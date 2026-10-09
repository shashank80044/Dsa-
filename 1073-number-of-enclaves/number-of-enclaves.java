class Solution {
    public int numEnclaves(int[][] grid) {
                int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];
        Queue<int[]> q = new LinkedList<>();

        // Add all boundary land cells to the queue
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // First row, first column, last row, last column
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    if (grid[i][j] == 1 && vis[i][j] == 0) {
                        q.add(new int[]{i, j});
                        vis[i][j] = 1;
                    }
                }
            }
        }

        // Directions: up, right, down, left
        int[] delrow = {-1, 0, 1, 0};
        int[] delcol = {0, 1, 0, -1};

        // BFS traversal
        while (!q.isEmpty()) {
            int[] cell = q.poll();

            int row = cell[0];
            int col = cell[1];

            // Traverse all four directions
            for (int i = 0; i < 4; i++) {
                int nrow = row + delrow[i];
                int ncol = col + delcol[i];

                if (nrow >= 0 && nrow < n &&
                    ncol >= 0 && ncol < m &&
                    vis[nrow][ncol] == 0 &&
                    grid[nrow][ncol] == 1) {

                    q.add(new int[]{nrow, ncol});
                    vis[nrow][ncol] = 1;
                }
            }
        }

        // Count land cells that cannot reach the boundary
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && vis[i][j] == 0) {
                    cnt++;
                }
            }
        }

        return cnt;
    }
} 

