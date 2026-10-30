
import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Build adjacency list
        List<int[]>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
        }

        // dist[node][count]
        int[][] dist = new int[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Priority queue: {distance, node, consecutiveCount}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // Starting node has one occurrence of its label
        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int d = curr[0];
            int node = curr[1];
            int count = curr[2];

            if (d != dist[node][count]) {
                continue;
            }

            // Destination reached
            if (node == n - 1) {
                return d;
            }

            for (int[] edge : adj[node]) {

                int next = edge[0];
                int weight = edge[1];

                int newCount;

                if (labels.charAt(node) == labels.charAt(next)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                // More than k consecutive identical characters
                if (newCount > k) {
                    continue;
                }

                int newDist = d + weight;

                if (newDist < dist[next][newCount]) {

                    dist[next][newCount] = newDist;

                    pq.offer(new int[]{
                        newDist,
                        next,
                        newCount
                    });
                }
            }
        }

        return -1;
    }
}
