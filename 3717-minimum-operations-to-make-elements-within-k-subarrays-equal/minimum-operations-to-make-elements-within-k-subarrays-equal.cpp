class Solution {
public:
    int n, x, k;

    multiset<int> left, right;
    long long leftSum = 0, rightSum = 0;

    // Maintain:
    // left.size() == right.size() OR left.size() == right.size() + 1
    // Every element in left <= every element in right

    void balance() {
        while (left.size() > right.size() + 1) {
            int val = *left.rbegin();

            left.erase(left.find(val));
            leftSum -= val;

            right.insert(val);
            rightSum += val;
        }

        while (left.size() < right.size()) {
            int val = *right.begin();

            right.erase(right.find(val));
            rightSum -= val;

            left.insert(val);
            leftSum += val;
        }
    }

    void add(int val) {
        if (left.empty() || val <= *left.rbegin()) {
            left.insert(val);
            leftSum += val;
        } else {
            right.insert(val);
            rightSum += val;
        }

        balance();
    }

    void remove(int val) {
        auto it = left.find(val);

        if (it != left.end()) {
            left.erase(it);
            leftSum -= val;
        } else {
            it = right.find(val);
            right.erase(it);
            rightSum -= val;
        }

        balance();
    }

    long long getCost() {
        long long median = *left.rbegin();

        long long leftCost =
            (long long)left.size() * median - leftSum;

        long long rightCost =
            rightSum - (long long)right.size() * median;

        return leftCost + rightCost;
    }

    long long minOperations(vector<int>& nums, int x, int k) {
        n = nums.size();
        this->x = x;
        this->k = k;

        // Cost of making each window of length x equal
        vector<long long> cost(n - x + 1, 0);

        left.clear();
        right.clear();
        leftSum = 0;
        rightSum = 0;

        // Sliding window
        int i = 0;

        for (int j = 0; j < n; j++) {
            add(nums[j]);

            if (j - i + 1 == x) {
                cost[i] = getCost();

                remove(nums[i]);
                i++;
            }
        }

        // DP:
        // dp[count][idx] = minimum cost to select
        // count subarrays starting from index idx onwards

        const long long INF = LLONG_MAX / 4;

        vector<vector<long long>> dp(
            k + 1, vector<long long>(n + 1, INF)
        );

        // No more subarrays needed
        for (int i = 0; i <= n; i++) {
            dp[k][i] = 0;
        }

        // Bottom-up DP
        for (int count = k - 1; count >= 0; count--) {
            for (int idx = n - 1; idx >= 0; idx--) {

                // Option 1: Skip current index
                dp[count][idx] = dp[count][idx + 1];

                // Option 2: Select subarray of length x
                if (idx + x <= n &&
                    dp[count + 1][idx + x] != INF) {

                    dp[count][idx] = min(
                        dp[count][idx],
                        cost[idx] + dp[count + 1][idx + x]
                    );
                }
            }
        }

        return dp[0][0];
    }
};