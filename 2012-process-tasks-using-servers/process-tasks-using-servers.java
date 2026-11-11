class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] res = new int[m];

        // Available servers: weight, index
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                return Integer.compare(a[1], b[1]);
            }
        );

        // Busy servers: finishTime, weight, index
        PriorityQueue<int[]> dq = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                if (a[1] != b[1]) {
                    return Integer.compare(a[1], b[1]);
                }
                return Integer.compare(a[2], b[2]);
            }
        );

        // Initially, all servers are available.
        for (int i = 0; i < n; i++) {
            pq.add(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {

            time = Math.max(time, j);

            // Release all servers whose tasks are finished.
            while (!dq.isEmpty() && dq.peek()[0] <= time) {

                int[] arr = dq.poll();

                pq.add(new int[]{arr[1], arr[2]});
            }

            // If no server is available, move time forward.
            if (pq.isEmpty()) {

                time = dq.peek()[0];

                while (!dq.isEmpty() && dq.peek()[0] <= time) {

                    int[] arr = dq.poll();

                    pq.add(new int[]{arr[1], arr[2]});
                }
            }

            // Select the best available server.
            int[] server = pq.poll();

            int weight = server[0];
            int index = server[1];

            res[j] = index;

            // Assign the task and calculate finishing time.
            long finishTime = time + tasks[j];

            dq.add(new int[]{
                (int) finishTime,
                weight,
                index
            });
        }

        return res;
    }
}