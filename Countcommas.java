import java.util.*;
class Countcommas {
    static int countCommas(int n) {
        if(n<1000)return 0;
        int count =0;
        while(n>=1000){
            count = n%1000;
            n=n/1000;
          
        }
        return count+1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(countCommas(n));
    }
}