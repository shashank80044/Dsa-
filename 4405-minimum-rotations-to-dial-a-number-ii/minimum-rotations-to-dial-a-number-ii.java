class Solution {
    public int minRotations(int n, String s) {
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
        }

        // 1. Compute total cost without reversing
        int totalOriginalCost = cost(0, digits[0]);
        for (int i = 1; i < n; i++) {
            totalOriginalCost += cost(digits[i - 1], digits[i]);
        }

        int minCost = totalOriginalCost;

        // 2. Precompute forward costs (prefix sum)
        // pref[i] = total cost to dial digits[0] up to digits[i-1]
        int[] pref = new int[n + 1];
        pref[1] = cost(0, digits[0]);
        for (int i = 1; i < n; i++) {
            pref[i + 1] = pref[i] + cost(digits[i - 1], digits[i]);
        }

        // 3. Precompute backward costs (suffix sum)
        // suff[i] = total cost to dial backwards from digits[n-1] down to digits[i]
        int[] suff = new int[n + 1];
        for (int i = n - 2; i >= 0; i--) {
            suff[i] = suff[i + 1] + cost(digits[i + 1], digits[i]);
        }

        // 4. Try every suffix split point k
        for (int k = 0; k < n; k++) {
            // Cost of normal prefix s[0..k-1]
            int prefixCost = pref[k];

            // Jump cost from s[k-1] (or dial starting position 0 if k=0) to s[n-1]
            int prevDigit = (k == 0) ? 0 : digits[k - 1];
            int jumpCost = cost(prevDigit, digits[n - 1]);

            // Backward cost for the reversed suffix
            int suffixCost = suff[k];

            int total = prefixCost + jumpCost + suffixCost;
            minCost = Math.min(minCost, total);
        }

        return minCost;
    }

    // Simple helper function to find minimum dial rotation between two numbers
    private int cost(int a, int b) {
        int diff = Math.abs(a - b);
        return Math.min(diff, 10 - diff);
    }
}