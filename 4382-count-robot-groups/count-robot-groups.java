class Solution {
    public int countGroups(int[] position, int[] speed, int distance) {

        int n = position.length;

        int groups = 1; // At least 1 group will be formed

        int groupSpeed = speed[n - 1];

        for (int i = n - 2; i >= 0; i--) {

            if (position[i + 1] - position[i] <= distance) {
                continue;
            } 
            else if (speed[i] <= groupSpeed) {
                groups++;
                groupSpeed = speed[i];
            }
        }

        return groups;
    }
}