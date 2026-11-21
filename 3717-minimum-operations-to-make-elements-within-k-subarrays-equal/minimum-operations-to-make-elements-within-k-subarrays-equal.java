import java.util.*;

class Solution {
    int n, x, k;

    long[] minOpsSubarray;
    long[][] memo;

    int leftSize, rightSize;
    long leftSum, rightSum;

    TreeMap<Integer, Integer> left;
    TreeMap<Integer, Integer> right;

    // Add element to left
    void addLeft(int val) {
        left.put(val, left.getOrDefault(val, 0) + 1);
        leftSize++;
        leftSum += val;
    }

    // Add element to right
    void addRight(int val) {
        right.put(val, right.getOrDefault(val, 0) + 1);
        rightSize++;
        rightSum += val;
    }

    // Remove element from left
    void removeLeft(int val) {
        int freq = left.get(val);

        if (freq == 1) {
            left.remove(val);
        } else {
            left.put(val, freq - 1);
        }

        leftSize--;
        leftSum -= val;
    }

    // Remove element from right
    void removeRight(int val) {
        int freq = right.get(val);

        if (freq == 1) {
            right.remove(val);
        } else {
            right.put(val, freq - 1);
        }

        rightSize--;
        rightSum -= val;
    }

    // Balance both sets
    void balance() {
        while (leftSize > rightSize + 1) {
            int val = left.lastKey();

            removeLeft(val);
            addRight(val);
        }

        while (leftSize < rightSize) {
            int val = right.firstKey();

            removeRight(val);
            addLeft(val);
        }
    }

    // Insert element
    void add(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            addLeft(val);
        } else {
            addRight(val);
        }

        balance();
    }

    // Remove element
    void remove(int val) {
        if (left.containsKey(val)) {
            removeLeft(val);
        } else {
            removeRight(val);
        }

        balance();
    }

    // Calculate minimum operations for current window
    long getCost() {
        long median = left.lastKey();

        return (median * leftSize - leftSum)
             + (rightSum - median * rightSize);
    }

    // Recursive DP
    long solve(int count, int i) {
        if (count == k) {
            return 0;
        }

        int subArraysNeeded = k - count;
        int elementsNeeded = subArraysNeeded * x;
        int lastIndex = i + elementsNeeded - 1;

        if (lastIndex >= n) {
            return Long.MAX_VALUE / 4;
        }

        if (memo[count][i] != -1) {
            return memo[count][i];
        }

        // Take subarray starting at i
        long result = minOpsSubarray[i] + solve(count + 1, i + x);

        // Skip index i
        result = Math.min(result, solve(count, i + 1));

        return memo[count][i] = result;
    }

    public long minOperations(int[] nums, int x, int k) {
        this.n = nums.length;
        this.x = x;
        this.k = k;

        minOpsSubarray = new long[n];

        memo = new long[k + 1][n + 1];

        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }

        left = new TreeMap<>();
        right = new TreeMap<>();

        leftSize = 0;
        rightSize = 0;
        leftSum = 0;
        rightSum = 0;

        // Sliding window to calculate cost of every subarray of size x
        int i = 0;
        int j = 0;

        while (j < n) {
            add(nums[j]);

            if (j - i + 1 == x) {
                minOpsSubarray[i] = getCost();

                remove(nums[i]);
                i++;
            }

            j++;
        }

        return solve(0, 0);
    }
}