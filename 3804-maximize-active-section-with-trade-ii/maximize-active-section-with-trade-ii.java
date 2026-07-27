import java.util.*;

class Solution {
    private void buildSegmentTree(int i, int l, int r, int[] segmentTree, int[] arr) {
        if (l == r) {
            segmentTree[i] = arr[l];
            return;
        }
        int mid = l + (r - l) / 2;
        buildSegmentTree(2 * i + 1, l, mid, segmentTree, arr);
        buildSegmentTree(2 * i + 2, mid + 1, r, segmentTree, arr);
        segmentTree[i] = Math.max(segmentTree[2 * i + 1], segmentTree[2 * i + 2]);
    }

    private int[] constructST(int[] arr, int n) {
        int[] segmentTree = new int[4 * n];
        buildSegmentTree(0, 0, n - 1, segmentTree, arr);
        return segmentTree;
    }

    private int querySegmentTree(int start, int end, int i, int l, int r, int[] segmentTree) {
        if (l > end || r < start) {
            return Integer.MIN_VALUE;
        }
        if (l >= start && r <= end) {
            return segmentTree[i];
        }
        int mid = l + (r - l) / 2;
        return Math.max(
            querySegmentTree(start, end, 2 * i + 1, l, mid, segmentTree),
            querySegmentTree(start, end, 2 * i + 2, mid + 1, r, segmentTree)
        );
    }

    private int RMQ(int[] st, int n, int a, int b) {
        if (a > b) return 0;
        return querySegmentTree(a, b, 0, 0, n - 1, st);
    }

    private int lowerBound(List<Integer> list, int value) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) >= value) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private int upperBound(List<Integer> list, int value) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) > value) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int activeCount = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') activeCount++;
        }

        List<Integer> blockStart = new ArrayList<>();
        List<Integer> blockEnd = new ArrayList<>();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '0') {
                int start = i;
                while (i < n && s.charAt(i) == '0') i++;
                blockStart.add(start);
                blockEnd.add(i - 1);
            } else {
                i++;
            }
        }

        int m = blockStart.size();
        List<Integer> result = new ArrayList<>();
        if (m < 2) {
            for (int q = 0; q < queries.length; q++) {
                result.add(activeCount);
            }
            return result;
        }

        int[] blockSize = new int[m];
        for (int j = 0; j < m; j++) {
            blockSize[j] = blockEnd.get(j) - blockStart.get(j) + 1;
        }

        int N = m - 1;
        int[] pairSum = new int[N];
        for (int j = 0; j < N; j++) {
            pairSum[j] = blockSize[j] + blockSize[j + 1];
        }

        int[] st = constructST(pairSum, N);

        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];

            int low = lowerBound(blockEnd, l);
            int high = upperBound(blockStart, r) - 1;

            int maxPairSum = 0;
            if (low < high) {
                int firstLen = blockEnd.get(low) - Math.max(blockStart.get(low), l) + 1;
                int lastLen = Math.min(blockEnd.get(high), r) - blockStart.get(high) + 1;

                if (high - low == 1) {
                    maxPairSum = firstLen + lastLen;
                } else {
                    int pair1 = firstLen + blockSize[low + 1];
                    int pair2 = blockSize[high - 1] + lastLen;
                    int RMQMaxPairSum = RMQ(st, N, low + 1, high - 2);
                    maxPairSum = Math.max(Math.max(pair1, pair2), RMQMaxPairSum);
                }
            }
            result.add(maxPairSum + activeCount);
        }

        return result;
    }
}