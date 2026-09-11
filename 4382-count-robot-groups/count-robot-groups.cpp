class Solution {
public:
    int countGroups(vector<int>& position, vector<int>& speed, int distance) {

        int n = position.size();

        int groups = 1; // At least 1 group will be made

        int group_speed = speed[n - 1];

        for (int i = n - 2; i >= 0; i--) {

            if (position[i + 1] - position[i] <= distance) {
                continue;
            }
            else if (speed[i] <= group_speed) {
                groups++;
                group_speed = speed[i];
            }
        }

        return groups;
    }
};