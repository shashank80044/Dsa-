import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        int[] newParent = parent.clone();
        int[] nearest = new int[26];
        Arrays.fill(nearest, -1);

        // DFS using stack (enter/exit events)
        int[] stack = new int[2 * n];
        int[] saved = new int[2 * n];
        boolean[] exit = new boolean[2 * n];

        int top = 0;
        stack[top] = 0;
        exit[top++] = false;

        while (top > 0) {
            top--;

            int node = stack[top];

            if (exit[top]) {
                int c = s.charAt(node) - 'a';
                nearest[c] = saved[top];
                continue;
            }

            int c = s.charAt(node) - 'a';
            int prev = nearest[c];

            if (node != 0 && prev != -1) {
                newParent[node] = prev;
            }

            nearest[c] = node;

            // Exit event
            stack[top] = node;
            saved[top] = prev;
            exit[top++] = true;

            // Visit children
            for (int child : adj[node]) {
                stack[top] = child;
                exit[top++] = false;
            }
        }

        // Build modified tree
        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[newParent[i]].add(i);
        }

        // Get traversal order
        int[] order = new int[n];
        int[] dfs = new int[n];
        int size = 0, idx = 0;

        dfs[size++] = 0;

        while (size > 0) {
            int node = dfs[--size];
            order[idx++] = node;

            for (int child : tree[node]) {
                dfs[size++] = child;
            }
        }

        // Calculate subtree sizes
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i > 0; i--) {
            int node = order[i];
            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}