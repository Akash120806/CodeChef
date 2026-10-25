import java.io.*;
import java.util.*;

public class Main {

    static int lowerBound(ArrayList<Integer> a, int value) {
        int low = 0, high = a.size();

        while (low < high) {
            int mid = (low + high) / 2;

            if (a.get(mid) < value)
                low = mid + 1;
            else
                high = mid;
        }

        return low;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[][] lines = new int[n][4];

        TreeSet<Integer> setX = new TreeSet<Integer>();
        TreeSet<Integer> setY = new TreeSet<Integer>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st =
                    new StringTokenizer(br.readLine());

            for (int j = 0; j < 4; j++)
                lines[i][j] = Integer.parseInt(st.nextToken());

            int x1 = lines[i][0];
            int y1 = lines[i][1];
            int x2 = lines[i][2];
            int y2 = lines[i][3];

            if (x1 == x2)
                setX.add(x1);

            if (y1 == y2)
                setY.add(y1);
        }

        ArrayList<Integer> X = new ArrayList<Integer>(setX);
        ArrayList<Integer> Y = new ArrayList<Integer>(setY);

        int V = X.size();
        int H = Y.size();

        HashMap<Integer, Integer> xi =
                new HashMap<Integer, Integer>();

        HashMap<Integer, Integer> yi =
                new HashMap<Integer, Integer>();

        for (int i = 0; i < V; i++)
            xi.put(X.get(i), i);

        for (int i = 0; i < H; i++)
            yi.put(Y.get(i), i);

        // h[y][j]: horizontal interval X[j] to X[j+1]
        // v[x][j]: vertical interval Y[j] to Y[j+1]
        boolean[][] h = new boolean[H][V];
        boolean[][] v = new boolean[V][H];

        for (int i = 0; i < n; i++) {
            int x1 = lines[i][0];
            int y1 = lines[i][1];
            int x2 = lines[i][2];
            int y2 = lines[i][3];

            if (y1 == y2) {
                int row = yi.get(y1);
                int left = Math.min(x1, x2);
                int right = Math.max(x1, x2);

                for (int j = 0; j < V - 1; j++) {
                    if (left <= X.get(j) &&
                        X.get(j + 1) <= right) {
                        h[row][j] = true;
                    }
                }

            } else if (x1 == x2) {
                int col = xi.get(x1);
                int bottom = Math.min(y1, y2);
                int top = Math.max(y1, y2);

                for (int j = 0; j < H - 1; j++) {
                    if (bottom <= Y.get(j) &&
                        Y.get(j + 1) <= top) {
                        v[col][j] = true;
                    }
                }
            }
        }

        // Prefix sums count uncovered intervals.
        int[][] hp = new int[H][V];
        int[][] vp = new int[V][H];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < V - 1; j++) {
                hp[i][j + 1] = hp[i][j]
                        + (h[i][j] ? 0 : 1);
            }
        }

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < H - 1; j++) {
                vp[i][j + 1] = vp[i][j]
                        + (v[i][j] ? 0 : 1);
            }
        }

        long answer = 0;

        for (int left = 0; left < V; left++) {
            for (int right = left + 1; right < V; right++) {

                for (int bottom = 0; bottom < H; bottom++) {
                    for (int top = bottom + 1; top < H; top++) {

                        if (hp[bottom][right] - hp[bottom][left] == 0
                                && hp[top][right] - hp[top][left] == 0
                                && vp[left][top] - vp[left][bottom] == 0
                                && vp[right][top] - vp[right][bottom] == 0) {

                            answer++;
                        }
                    }
                }
            }
        }

        System.out.println(answer);
    }
}