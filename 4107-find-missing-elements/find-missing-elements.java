class Solution {
    public static  boolean find(int target,int[]nums)
    {
        boolean res = false;
        for(int i = 0;i<nums.length;i++)
        {
            if(target == nums[i])
            {
                res = true;
            }
        }
   return res;
    }
    public List<Integer> findMissingElements(int[] nums) {
     int smallest = 0;
     int highest  = 0;

     Arrays.sort(nums);
     smallest = nums[0];
     highest  = nums[nums.length-1]; 
     ArrayList<Integer> list = new ArrayList<>();
     for(int j = smallest;j<= highest;j++)
    {
        if(find(j,nums)== false)
        {
            list.add(j);
        }
    }
  return list;
    }
    
}