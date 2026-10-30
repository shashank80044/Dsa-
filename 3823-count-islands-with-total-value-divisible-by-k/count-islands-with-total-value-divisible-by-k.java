
import java.util.*;

class Solution {
    public int countIslands(int[][] grid, int k) {

        int row = grid.length;
        int col = grid[0].length;

        boolean[][] vis = new boolean[row][col];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int res = 0;

        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if (grid[i][j] > 0 && !vis[i][j]) {

                    long sum = 0;

                    q.offer(new int[]{i, j});
                    vis[i][j] = true;

                    while (!q.isEmpty()) {

                        int[] curr = q.poll();

                        int r = curr[0];
                        int c = curr[1];

                        sum += grid[r][c];

                        for (int d = 0; d < 4; d++) {

                            int nr = r + dr[d];
                            int nc = c + dc[d];

                            if (nr >= 0 && nr < row &&
                                nc >= 0 && nc < col &&
                                grid[nr][nc] > 0 &&
                                !vis[nr][nc]) {

                                vis[nr][nc] = true;
                                q.offer(new int[]{nr, nc});
                            }
                        }
                    }

                    if (sum % k == 0) {
                        res++;
                    }
                }
            }
        }

        return res;
    }
}
