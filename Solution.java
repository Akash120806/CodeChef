lass Solution {
    public long countIntersectingIntervals(int[][] intervals) {
        if (intervals.length == 1) {
            return 0;
        }
        int i = 0;
        long count = 0;
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int j = i;
        while (i < intervals.length -1 ) {
            if (intervals[i][1] >= intervals[i+1][0]) {
                count++;
            }
            i++;
            if(i==intervals.length-2){
                j++;
                i=j;
            }

            
        }
        return count;
    }
}