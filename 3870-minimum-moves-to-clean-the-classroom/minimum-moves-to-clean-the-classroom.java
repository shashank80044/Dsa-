class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        // Find starting position and count litter
        int startR = 0;
        int startC = 0;
        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterCount++;
                }
            }
        }

        // If there is no litter, no moves are needed
        if (litterCount == 0) {
            return 0;
        }

        // All litter collected mask
        int allCollected = (1 << litterCount) - 1;

        /*
         * litterId[r][c] tells us which litter number
         * is present at (r,c).
         *
         * -1 means there is no litter there.
         */
        int[][] litterId = new int[m][n];

        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(litterId[i], -1);
        }

        int id = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = id;
                    id++;
                }
            }
        }

        /*
         * visited[row][col][remainingEnergy][mask]
         *
         * mask tells which litter has already been collected.
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        /*
         * Each state contains:
         *
         * [0] = row
         * [1] = column
         * [2] = remaining energy
         * [3] = collected litter mask
         */
        java.util.Queue<int[]> queue =
            new java.util.ArrayDeque<>();

        // Starting state
        queue.offer(new int[] {
            startR,
            startC,
            energy,
            0
        });

        visited[startR][startC][energy][0] = true;

        int moves = 0;

        // Up, Down, Left, Right
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int size = queue.size();

            /*
             * Every state currently in the queue
             * requires exactly 'moves' moves.
             */
            while (size-- > 0) {

                int[] state = queue.poll();

                int r = state[0];
                int c = state[1];
                int currEnergy = state[2];
                int mask = state[3];

                // All litter collected
                if (mask == allCollected) {
                    return moves;
                }

                // No energy means we cannot move
                if (currEnergy == 0) {
                    continue;
                }

                // Try four directions
                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Outside the grid
                    if (nr < 0 || nr >= m ||
                        nc < 0 || nc >= n) {
                        continue;
                    }

                    // Cannot move through obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Moving costs 1 energy
                    int newEnergy = currEnergy - 1;

                    /*
                     * If we arrive at R,
                     * energy becomes full again.
                     */
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    // Current litter mask
                    int newMask = mask;

                    /*
                     * If this cell contains litter,
                     * mark that litter as collected.
                     */
                    if (classroom[nr].charAt(nc) == 'L') {

                        int litterIndex = litterId[nr][nc];

                        newMask =
                            newMask | (1 << litterIndex);
                    }

                    /*
                     * If this complete state hasn't
                     * been visited before, add it.
                     */
                    if (!visited[nr][nc][newEnergy][newMask]) {

                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(new int[] {
                            nr,
                            nc,
                            newEnergy,
                            newMask
                        });
                    }
                }
            }

            // Move to the next BFS level
            moves++;
        }

        // Impossible to collect all litter
        return -1;
    }
}