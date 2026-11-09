
class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Stores available server IDs in sorted order
        TreeSet<Integer> available = new TreeSet<>();

        // Stores {finishTime, serverID}
        // Sorts according to finishTime
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // Number of requests handled by each server
        int[] count = new int[k];

        // Initially all servers are available
        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            // Release all servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {

                int[] curr = busy.poll();

                available.add(curr[1]);
            }

            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }

            // Preferred server
            int server = i % k;

            // Find the first available server >= preferred server
            Integer assigned = available.ceiling(server);

            // If not found, wrap around to the smallest server
            if (assigned == null) {
                assigned = available.first();
            }

            // Assign request to this server
            available.remove(assigned);

            // Increase its request count
            count[assigned]++;

            // Calculate completion time
            int finishTime = arrival[i] + load[i];

            // Mark server as busy
            busy.offer(new int[]{finishTime, assigned});
        }

        // Find maximum number of requests handled
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        // Collect all busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
