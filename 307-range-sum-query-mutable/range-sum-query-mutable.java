class NumArray {
   private int n;
   private int[]tree;
    public NumArray(int[] nums) {
     n = nums.length;
    if(n==0) return;
     
     tree = new int[4*n];
    build(0,0,n-1,nums);
    }
    private void build(int index,int left,int right,int []nums)
    {
         if(left == right)
         {
            tree[index]=nums[left];
            return;
         }
         int mid = left+(right-left)/2;
         build(2*index+1,left,mid,nums);
         build(2*index+2,mid+1,right,nums);
         tree[index]=tree[2*index+1]+tree[2*index+2];
    }
    
    public void update(int index, int val){
    updatequery(index,val,0,0,n-1);
    }
    private void updatequery(int index,int val,int i,int left,int right)
    { 

int mid = left + (right-left)/2;

  if(left == right)
  {
    tree[i]= val;
    return;
  }
  if(index<=mid)
  {
    updatequery(index,val,2*i+1,left,mid);
  }  
  else{
    updatequery(index,val,2*i+2,mid+1,right);
    }
   tree[i]=tree[2*i+1]+tree[2*i+2];
    }
    
    public int sumRange(int left, int right) {
         return  query(0,n-1,left,right,0);           
    }
   private int query(int left,int right,int start,int end,int index)
{
    if(left > end || right < start)
        return 0;

    if(start <= left && right <= end)
        return tree[index];

    int mid = left + (right-left)/2;

    return query(left,mid,start,end,2*index+1)
         + query(mid+1,right,start,end,2*index+2);
}
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */