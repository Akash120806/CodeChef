class Solution {
    static  long countSubarrays(int[] nums, int minK, int maxK) {
        long count=0;
        int  j=0;
        for(int i =0; i<nums.length;i++){
          if(valid(nums[i] , minK,  maxK)){
              count += countarr(i,j);
          }
          else{
              j=i;
          }
        }
        return count;
    }
    static boolean valid(int i ,int minK, int maxK){
        if(i<=maxK && i>=minK )return true;
        return false;
    }
    static long countarr(int i,int j ){
        int count=0;
        for( int k =0;k<=i-j;k++){
           
            count +=k ;
            
        }
        return count;
        
    }
         public static void main(String[] args) {
        int[] nums={}
        countSubarrays( nums,  minK,  maxK)
    }
   
}