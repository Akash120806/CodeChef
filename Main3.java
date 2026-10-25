import java.util.*;

public class Main3{

    static boolean isBlocked(long p, long[][] rects, boolean isX) {
        for (long[] r : rects) {
            long start = isX ? r[0] : r[1];
            long end = isX ? r[2] : r[3];

            if (start < p && p < end) {
                return true;
            }
        }
        return false;
    }

    static long minGap(long[][] rects, long low,
                       long high, boolean isX) {

        List<Long> points = new ArrayList<>();

        points.add(low);
        points.add(high);

        for (long[] r : rects) {
            long start = isX ? r[0] : r[1];
            long end = isX ? r[2] : r[3];

            if (start >= low && start <= high) {
                points.add(start);
            }

            if (end >= low && end <= high) {
                points.add(end);
            }
        }

        Collections.sort(points);

        List<Long> unique = new ArrayList<>();

        for (long p : points) {
            if (unique.isEmpty()
                    || unique.get(unique.size() - 1) != p) {
                unique.add(p);
            }
        }

        // Check for valid consecutive integer coordinates
        for (int i = 0; i < unique.size() - 1; i++) {
            long a = unique.get(i);
            long b = unique.get(i + 1);

            if (b - a >= 2 && !isBlocked(a + 1, rects, isX)) {
                return 1;
            }
        }

        // Keep only valid candidate coordinates
        List<Long> valid = new ArrayList<>();

        for (long p : unique) {
            if (!isBlocked(p, rects, isX)) {
                valid.add(p);
            }
        }

        long answer = Long.MAX_VALUE;

        for (int i = 1; i < valid.size(); i++) {
            answer = Math.min(
                answer,
                valid.get(i) - valid.get(i - 1)
            );
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long[][] rects = new long[n][4];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                rects[i][j] = sc.nextLong();
            }
        }

        long paperX1 = sc.nextLong();
        long paperY1 = sc.nextLong();
        long paperX2 = sc.nextLong();
        long paperY2 = sc.nextLong();

        long minWidth = minGap(
            rects, paperX1, paperX2, true
        );

        long minHeight = minGap(
            rects, paperY1, paperY2, false
        );

        System.out.println(minWidth * minHeight);

        sc.close();
    }
}