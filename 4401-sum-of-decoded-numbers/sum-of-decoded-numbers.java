class Solution {
    public int[] converse(long n) {
        long temp = n;
        int count = 0;

        while (temp > 0) {
            count++;
            temp /= 10;
        }

        int[] arr = new int[count];

        for (int i = count - 1; i >= 0; i--) {
            arr[i] = (int) (n % 10);
            n /= 10;
        }

        return arr;
    }

    public long decode(long val) {
        long width = val % 10;
        long d = val / 10;
       int[] arr = converse(d);
        long x = 0;

        for (int i = 0; i < width; i++) {
            x = x * 10 + arr[i];
        }
      long y = 0;

for (int i = (int)width; i < arr.length; i++) {
    y = y * 10 + arr[i];
}
        return power(x, y);
    }

    public long power(long x, long y) {
        long mod = 1000000007;
        long res = 1;
        while (y > 0) {
            if (y % 2 == 1) {
                res = (res * x) % mod;
            }
            x = (x * x) % mod;
            y = y / 2;
        }
        return res;
    }

    public int sumDecoded(long[] nums) {
        long Mod = 1000000007;
        long res = 0;
        for (long x : nums) {
            res = (res + decode(x)) % Mod;
        }
        return (int) res;
    }
}