
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Free servers: [weight, index]
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                return Integer.compare(a[1], b[1]);
            }
        );

        // Busy servers: [freeTime, weight, index]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Long.compare(a[0], b[0]);
                }
                if (a[1] != b[1]) {
                    return Long.compare(a[1], b[1]);
                }
                return Long.compare(a[2], b[2]);
            }
        );

        // Initially all servers are free
        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        // Maintain global time
        long time = 0;

        for (int i = 0; i < m; i++) {

            // Task i arrives at second i
            time = Math.max(time, i);

            // Release completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {

                long[] curr = busy.poll();

                free.offer(new int[]{
                    (int) curr[1],
                    (int) curr[2]
                });
            }

            // If no server is free, wait
            if (free.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers free at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {

                    long[] curr = busy.poll();

                    free.offer(new int[]{
                        (int) curr[1],
                        (int) curr[2]
                    });
                }
            }

            // Select the best available server
            int[] server = free.poll();

            int weight = server[0];
            int index = server[1];

            ans[i] = index;

            // Assign task and calculate finishing time
            busy.offer(new long[]{
                time + tasks[i],
                weight,
                index
            });
        }

        return ans;
    }
}

