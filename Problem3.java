import java.util.*;

public class Problem3 {

    static int n, m, destination;

    static ArrayList<Integer>[] graph;

    static ArrayList<HashSet<Integer>> paths1 = new ArrayList<>();
    static ArrayList<HashSet<Integer>> paths2 = new ArrayList<>();

    static void findPaths(int current, boolean[] visited,
                          HashSet<Integer> currentPath,
                          ArrayList<HashSet<Integer>> allPaths) {

        if (current == destination) {

            allPaths.add(new HashSet<Integer>(currentPath));

            return;
        }

        for (int next : graph[current]) {


            if (visited[next]) {
                continue;
            }

            visited[next] = true;
            currentPath.add(next);

            findPaths(next, visited, currentPath, allPaths);

            visited[next] = false;
            currentPath.remove(next);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();

        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < m; i++) {

            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            graph[a].add(b);
            graph[b].add(a);
        }

        int start1 = sc.nextInt() - 1;
        int start2 = sc.nextInt() - 1;

        destination = sc.nextInt() - 1;

        boolean[] visited1 = new boolean[n];

        HashSet<Integer> path1 = new HashSet<>();

        visited1[start1] = true;
        path1.add(start1);

        findPaths(start1, visited1, path1, paths1);

        boolean[] visited2 = new boolean[n];

        HashSet<Integer> path2 = new HashSet<>();

        visited2[start2] = true;
        path2.add(start2);

        findPaths(start2, visited2, path2, paths2);

        int answer = Integer.MAX_VALUE;

        
        for (HashSet<Integer> p1 : paths1) {

            for (HashSet<Integer> p2 : paths2) {

                boolean valid = true;

                for (int town : p1) {

                    if (p2.contains(town) && town != destination) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {

                    int total = p1.size() + p2.size() - 1;

                    if (total < answer) {
                        answer = total;
                    }
                }
            }
        }

        if (answer == Integer.MAX_VALUE) {
            System.out.println("Impossible");
        } else {
            System.out.println(answer);
        }

        sc.close();
    }
}