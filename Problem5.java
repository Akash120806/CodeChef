import java.util.*;

public class Problem5 {

    static int n, m, t;
    static char[][] grid;
    static boolean[][][] allowed;
    static boolean[][] visited;

    static String answer = "";
    static boolean found = false;
    static boolean multiple = false;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static void dfs(int r, int c, int time, String word) {

        if (multiple)
            return;

        if (time == t) {

            if (!found) {
                answer = word;
                found = true;
            } else if (!answer.equals(word)) {
                multiple = true;
            }

            return;
        }

        for (int i = 0; i < 4; i++) {

            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr < 0 || nr >= n || nc < 0 || nc >= m)
                continue;

            if (visited[nr][nc])
                continue;

            if (!allowed[time + 1][nr][nc])
                continue;

            visited[nr][nc] = true;

            dfs(nr, nc, time + 1,
                word + grid[nr][nc]);

            visited[nr][nc] = false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();

        grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.next().charAt(0);
            }
        }

        t = sc.nextInt();

        int clues = sc.nextInt();

        allowed = new boolean[t + 1][n][m];

        for (int time = 1; time <= t; time++) {
            for (int i = 0; i < n; i++) {
                Arrays.fill(allowed[time][i], true);
            }
        }

        for (int i = 0; i < clues; i++) {

            int time = sc.nextInt();

            int x1 = sc.nextInt() - 1;
            int y1 = sc.nextInt() - 1;
            int x2 = sc.nextInt() - 1;
            int y2 = sc.nextInt() - 1;

            for (int r = x1; r <= x2; r++) {
                for (int c = y1; c <= y2; c++) {
                    allowed[time][r][c] = false;
                }
            }
        }

        for (int time = 1; time <= t; time++) {

            boolean possible = false;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {

                    if (allowed[time][i][j]) {
                        possible = true;
                    }
                }
            }

            if (!possible) {
                System.out.println("Not enough clues");
                return;
            }
        }

        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (allowed[1][i][j]) {

                    visited[i][j] = true;

                    dfs(i, j, 1, "" + grid[i][j]);

                    visited[i][j] = false;

                    if (multiple) {
                        System.out.println("Not enough clues");
                        return;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("Not enough clues");
        } else {
            System.out.println(answer);
        }

        sc.close();
    }
}