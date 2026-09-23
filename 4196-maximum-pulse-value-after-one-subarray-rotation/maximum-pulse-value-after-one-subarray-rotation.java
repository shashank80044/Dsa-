class Solution {

    public long maxValue(int[] nums) {

        int n = nums.length;

        if (n == 1)
            return nums[0];

        if (n == 2)
            return Math.max(
                nums[0] - nums[1],
                nums[1] - nums[0]
            );

        long total = 0;

        // Calculate the original alternating sum
        for (int i = 0; i < n; i++) {

            if ((i & 1) == 1)
                total -= nums[i];
            else
                total += nums[i];
        }

        long max_sum = Math.max(
            total,
            total - 2L * (nums[0] - nums[1])
        );

        long max_even_prefix_sum = nums[0];

        long max_odd_prefix_sum = Math.max(
            0L,
            (long) nums[0] - nums[1]
        );

        long sum = nums[0] - nums[1];

        for (int i = 2; i < n; i++) {

            if ((i & 1) == 1) {

                sum -= nums[i];

                max_sum = Math.max(
                    max_sum,
                    total - 2L * (sum - max_odd_prefix_sum)
                );

                max_odd_prefix_sum = Math.max(
                    max_odd_prefix_sum,
                    sum
                );

            } else {

                sum += nums[i];

                max_sum = Math.max(
                    max_sum,
                    total - 2L * (sum - max_even_prefix_sum)
                );

                max_even_prefix_sum = Math.max(
                    max_even_prefix_sum,
                    sum
                );
            }
        }

        return max_sum;
    }
}