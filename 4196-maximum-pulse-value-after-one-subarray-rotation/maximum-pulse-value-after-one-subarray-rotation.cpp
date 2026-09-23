class Solution {
    using ll = long long;

public:
    long long maxValue(vector<int>& nums) {
        int n = nums.size();

        if (n == 1) return nums[0];

        if (n == 2)
            return max(nums[0] - nums[1], nums[1] - nums[0]);

        ll total = 0;

        for (int i = 0; i < n; i++) {
            if (i & 1)
                total -= nums[i];
            else
                total += nums[i];
        }

        ll max_sum = max(total, total - 2LL * (nums[0] - nums[1]));

        ll max_even_prefix_sum = nums[0];
        ll max_odd_prefix_sum = max(0LL, (ll)nums[0] - nums[1]);

        ll sum = nums[0] - nums[1];

        for (int i = 2; i < n; i++) {

            if (i & 1) {
                sum -= nums[i];

                max_sum = max(
                    max_sum,
                    total - 2LL * (sum - max_odd_prefix_sum)
                );

                max_odd_prefix_sum = max(max_odd_prefix_sum, sum);
            }
            else {
                sum += nums[i];

                max_sum = max(
                    max_sum,
                    total - 2LL * (sum - max_even_prefix_sum)
                );

                max_even_prefix_sum = max(max_even_prefix_sum, sum);
            }
        }

        return max_sum;
    }
};