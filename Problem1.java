import java.util.*;

public class Problem1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] a = new int[51][4];

        for (int i = 0; i < 51; i++) {
            Arrays.fill(a[i], -1);
        }

        int[][] cmd = new int[n][2];
        String[] dir = new String[n];

        for (int i = 0; i < n; i++) {
            cmd[i][0] = sc.nextInt();
            cmd[i][1] = sc.nextInt();
            dir[i] = sc.next();
        }

        int target = sc.nextInt();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                if (cmd[i][0] > cmd[j][0] ||
                        (cmd[i][0] == cmd[j][0] && cmd[i][1] > cmd[j][1])) {

                    int t = cmd[i][0];
                    cmd[i][0] = cmd[j][0];
                    cmd[j][0] = t;

                    t = cmd[i][1];
                    cmd[i][1] = cmd[j][1];
                    cmd[j][1] = t;

                    String s = dir[i];
                    dir[i] = dir[j];
                    dir[j] = s;
                }
            }
        }

        for (int i = 0; i < n; i++) {

            int old = cmd[i][0];
            int ne = cmd[i][1];

            if (dir[i].equals("top")) {
                a[old][0] = ne;
                a[ne][1] = old;
            } else if (dir[i].equals("down")) {
                a[old][1] = ne;
                a[ne][0] = old;
            } else if (dir[i].equals("left")) {
                a[old][2] = ne;
                a[ne][3] = old;
            } else if (dir[i].equals("right")) {
                a[old][3] = ne;
                a[ne][2] = old;
            }
        }

        System.out.println(
                a[target][0] + " " +
                        a[target][1] + " " +
                        a[target][2] + " " +
                        a[target][3]);
    }
}