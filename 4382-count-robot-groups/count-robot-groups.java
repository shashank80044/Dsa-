import java.util.*;

class Solution {
    public int countGroups(int[] position, int[] speed, int distance) {

        int n = position.length;

        ArrayList<Integer> s = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            if (i > 0 && position[i] - position[i - 1] <= distance) {
                s.remove(s.size() - 1);
            }

            s.add(speed[i]);
        }

        int ans = 0;
        int minSpeed = Integer.MAX_VALUE;

        for (int i = s.size() - 1; i >= 0; i--) {

            if (s.get(i) <= minSpeed) {
                ans++;
                minSpeed = s.get(i);
            }
        }

        return ans;
    }
}