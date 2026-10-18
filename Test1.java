import java.util.*;
class Test1 {
    public static void main(String[] args) {
        ArrayList <Integer > arr = new ArrayList<>();
        double x = Math.pow(10, 9);
        for(int i = 0 ; i<=x ;i++){
            if(i==0)arr.add(i);
            else if (i==1)arr.add(i);
            else{
                int element = i + (i-1) + (i-2);
                arr.add(element);
            }
        } 
       
        }
    }