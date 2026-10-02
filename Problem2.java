import java.util.*;

public class Problem2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        char[][] grid = new char[n][n];

        int sr = 0, scol = 0;
        int dr = 0, dcol = 0;

        for (int i = 0; i < n; i++) {

            String s = sc.nextLine();
            int col = 0;

            for (int j = 0; j < s.length();) {

                int num = 0;

                while (j < s.length() && Character.isDigit(s.charAt(j))) {
                    num = num * 10 + (s.charAt(j) - '0');
                    j++;
                }

                char ch = s.charAt(j++);

                for (int k = 0; k < num; k++) {

                    grid[i][col] = ch;

                    if (ch == 'S') {
                        sr = i;
                        scol = col;
                    }

                    if (ch == 'D') {
                        dr = i;
                        dcol = col;
                    }

                    col++;
                }
            }
        }

        int[][] dist = new int[n][n];

        for (int[] row : dist)
            Arrays.fill(row, 9999);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        dist[sr][scol] = 0;

        pq.add(new int[] { sr, scol, 0 });

        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        while (!pq.isEmpty()) {

            int[] cur = pq.poll();

            int x = cur[0];
            int y = cur[1];
            int cost = cur[2];

            if (x == dr && y == dcol) {
                System.out.println(cost);
                return;
            }

            for (int i = 0; i < 4; i++) {

                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= n || ny < 0 || ny >= n)
                    continue;

                if (grid[nx][ny] == 'R')
                    continue;

                int newCost = cost;

                if (grid[nx][ny] == 'G')
                    newCost++;

                if (newCost < dist[nx][ny]) {

                    dist[nx][ny] = newCost;

                    pq.add(new int[] { nx, ny, newCost });
                }
            }
        }
    }
}