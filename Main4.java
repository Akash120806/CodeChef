import java.util.*;

public class Main4 {

    static int n;
    static int[] x1 = new int[50];
    static int[] y1 = new int[50];
    static int[] x2 = new int[50];
    static int[] y2 = new int[50];

    static boolean onSlide(int i, int x, int y) {
        int minX = Math.min(x1[i], x2[i]);
        int maxX = Math.max(x1[i], x2[i]);

        if (x < minX || x > maxX)
            return false;

        int slope = (y2[i] - y1[i]) / (x2[i] - x1[i]);
        int expectedY = y1[i] + slope * (x - x1[i]);

        return expectedY == y;
    }

    static int countSlides(int x, int y) {
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (onSlide(i, x, y))
                count++;
        }

        return count;
    }

    static int nextX(int x, int y) {
        for (int i = 0; i < n; i++) {
            if (onSlide(i, x, y)) {

                int slope = (y2[i] - y1[i])
                          / (x2[i] - x1[i]);

                int minX = Math.min(x1[i], x2[i]);
                int maxX = Math.max(x1[i], x2[i]);

                if (slope == 1 && x > minX)
                    return x - 1;

                if (slope == -1 && x < maxX)
                    return x + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            x1[i] = sc.nextInt();
            y1[i] = sc.nextInt();
            x2[i] = sc.nextInt();
            y2[i] = sc.nextInt();
        }

        int x = sc.nextInt();
        int y = sc.nextInt();
        long energy = sc.nextLong();

        while (y > 0) {

            int count = countSlides(x, y);
            int next = nextX(x, y);

            if (count > 1) {
                long cost = (long) x * y;

                if (energy < cost)
                    break;

                energy -= cost;
            }

            if (next == -1) {
                y--;
                continue;
            }

            if (energy < 1)
                break;

            x = next;
            y--;
            energy--;
        }

        System.out.println(x + " " + y);

        sc.close();
    }
}