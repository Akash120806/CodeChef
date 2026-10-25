import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt())
            return;
        int n = sc.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = sc.nextInt() * 10;
            y[i] = sc.nextInt() * 10;
        }

        long area = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += (long) x[i] * y[j] - (long) x[j] * y[i];
        }

        if (area < 0) {
            for (int i = 0; i < n / 2; i++) {
                int tx = x[i], ty = y[i];
                x[i] = x[n - 1 - i];
                y[i] = y[n - 1 - i];
                x[n - 1 - i] = tx;
                y[n - 1 - i] = ty;
            }
        }

        double maxVol = 0;

        for (int h = 1; h <= 250; h++) {
            int[] nx = new int[n];
            int[] ny = new int[n];
            int[] edgeX = new int[n];
            int[] edgeY = new int[n];

            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                int dx = x[j] - x[i];
                int dy = y[j] - y[i];
                if (dx > 0) {
                    edgeX[i] = 0;
                    edgeY[i] = 1;
                } else if (dx < 0) {
                    edgeX[i] = 0;
                    edgeY[i] = -1;
                } else if (dy > 0) {
                    edgeX[i] = -1;
                    edgeY[i] = 0;
                } else {
                    edgeX[i] = 1;
                    edgeY[i] = 0;
                }
            }

            for (int i = 0; i < n; i++) {
                int prev = (i - 1 + n) % n;
                int cur = i;
                int px = x[i] + h * edgeX[prev];
                int py = y[i] + h * edgeY[prev];
                int cx = x[i] + h * edgeX[cur];
                int cy = y[i] + h * edgeY[cur];

                if (edgeX[prev] == 0) {
                    nx[i] = cx;
                    ny[i] = py;
                } else {
                    nx[i] = px;
                    ny[i] = cy;
                }
            }

            boolean valid = true;
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                int dist = Math.abs(nx[i] - nx[j]) + Math.abs(ny[i] - ny[j]);
                if (dist < 1) {
                    valid = false;
                    break;
                }
            }

            if (!valid)
                break;

            long curArea = 0;
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                curArea += (long) nx[i] * ny[j] - (long) nx[j] * ny[i];
            }
            curArea = Math.abs(curArea) / 2;

            double v = (curArea / 100.0) * (h / 10.0);
            if (v > maxVol) {
                maxVol = v;
            }
        }

        System.out.printf("%.2f\n", maxVol);
        sc.close();
    }
}
