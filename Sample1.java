import java.util.Scanner;

class Sample1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int grade = sc.nextInt();
            if (grade > 90)
                System.out.println("A");
            else if (grade > 70)
                System.out.println("B");
            else if (grade >= 40)
                System.out.println("C");
            else {
                System.out.println("F");
            }
        }
    }
}
