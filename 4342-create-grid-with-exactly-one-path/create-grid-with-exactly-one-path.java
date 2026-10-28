
import java.util.Arrays;

class Solution {
    public String[] createGrid(int m, int n) {
        String[][] arr = new String[m][n];
        String[] res = new String[m];

        // Fill the first row with free cells
        for (int i = 0; i < n; i++) {
            arr[0][i] = ".";
        }

        // Fill the last column with free cells
        for (int j = 0; j < m; j++) {
            arr[j][n - 1] = ".";
        }

        // Fill all remaining cells with obstacles
        for (int k = 0; k < m; k++) {
            for (int l = 0; l < n; l++) {
                if (arr[k][l] == null) {
                    arr[k][l] = "#";
                }
            }
        }

        // Convert each row into a string
        for (int h = 0; h < m; h++) {
            StringBuilder str = new StringBuilder();

            for (int g = 0; g < n; g++) {
                str.append(arr[h][g]);
            }

            res[h] = str.toString();
        }

        return res;
    }
}
