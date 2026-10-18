import java.util.*;
import java.lang.*;
import java.io.*;

class BlueAndBlackPens
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int mini= Math.min(A*3, A+B);
        System.out.println(mini);
        
	}
}
