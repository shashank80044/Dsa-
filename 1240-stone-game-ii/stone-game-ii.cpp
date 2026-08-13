#include <vector>
#include <algorithm>
#include <climits>
#include <cstring>

using namespace std;

class Solution {
public:
    int n;
    int dp[2][101][101]; // dp[person][i][M]

    int solveForAlice(vector<int>& piles, int person, int i, int M) {
        if (i >= n) return 0;
        if (dp[person][i][M] != -1) return dp[person][i][M];

        int result = (person == 1) ? -1 : INT_MAX;
        int stones = 0;

        for (int x = 1; x <= min(2 * M, n - i); x++) {
            stones += piles[i + x - 1];

            if (person == 1) {
                // Alice's turn: wants to MAXIMIZE total stones
                result = max(result, stones + solveForAlice(piles, 0, i + x, max(M, x)));
            } else {
                // Bob's turn: wants to MINIMIZE Alice's total stones
                // (Bob gets stones, but Alice gets 0 from this move)
                result = min(result, solveForAlice(piles, 1, i + x, max(M, x)));
            }
        }

        return dp[person][i][M] = result;
    }

    int stoneGameII(vector<int>& piles) {
        n = piles.size();
        memset(dp, -1, sizeof(dp));
        // Start game: Alice's turn (person = 1), starting at index 0, with M = 1
        return solveForAlice(piles, 1, 0, 1);
    }
};