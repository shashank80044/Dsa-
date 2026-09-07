class Solution {
    public int countGoodRotations(int[] nums) {
       int len = nums.length;
        int res = 0;
        long sum1 =0;
        long sum2=0;
        
    for(int i =0;i<len/2;i++)
    {
        sum1=sum1+nums[i];
    }
        for(int i =len/2;i<len;i++)
        {
            sum2=sum2+nums[i];
        }
        for(int i =0;i<len;i++)
        {
            if(sum1 > sum2)
            {
                res++;
            }
            int left = nums[i];
            int right = nums[(i+len/2)%len];

            sum1 = sum1 - left + right;
            sum2 = sum2 -right + left;
        }
        return res;
    }
}