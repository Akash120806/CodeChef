import java.util.*;

public class Problem4 {

    static HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();

    static HashMap<String, HashSet<String>> restrictions = new HashMap<String, HashSet<String>>();

    static boolean canTravel(String source, String destination) {

        if (source.equals(destination)) {
            return true;
        }

        HashSet<String> blocked = restrictions.get(source);

        HashSet<String> visited = new HashSet<String>();
        Queue<String> queue = new LinkedList<String>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {

            String current = queue.poll();

            ArrayList<String> neighbours = graph.get(current);

            if (neighbours == null) {
                continue;
            }

            for (String next : neighbours) {

                if (visited.contains(next)) {
                    continue;
                }

                if (blocked != null && blocked.contains(next)) {
                    continue;
                }

                if (next.equals(destination)) {
                    return true;
                }

                visited.add(next);
                queue.add(next);
            }
        }

        return false;
    }

    static void addStation(String station) {

        if (!graph.containsKey(station)) {
            graph.put(station, new ArrayList<String>());
        }
    }

    static void connect(String a, String b) {

        addStation(a);
        addStation(b);

        if (!graph.get(a).contains(b)) {
            graph.get(a).add(b);
        }

        if (!graph.get(b).contains(a)) {
            graph.get(b).add(a);
        }
    }

    static void disconnect(String a, String b) {

        if (graph.containsKey(a)) {
            graph.get(a).remove(b);
        }

        if (graph.containsKey(b)) {
            graph.get(b).remove(a);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            String line = sc.nextLine().trim();

            String[] parts = line.split("\\s+");

            String source = parts[0];

            addStation(source);

            for (int j = 1; j < parts.length; j++) {
                connect(source, parts[j]);
            }
        }

        int q = sc.nextInt();
        sc.nextLine();

        String[] queries = new String[q];

        for (int i = 0; i < q; i++) {
            queries[i] = sc.nextLine().trim();
        }

        int r = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < r; i++) {

            String line = sc.nextLine().trim();

            String[] parts = line.split("\\s+");

            String source = parts[0];

            HashSet<String> set = new HashSet<String>();

            for (int j = 1; j < parts.length; j++) {
                set.add(parts[j]);
            }

            restrictions.put(source, set);
        }

        for (String query : queries) {

            String[] parts = query.split("\\s+");

            String source = parts[0];

            if (parts[1].equals("to")) {

                String destination = parts[2];

                if (canTravel(source, destination)) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }

            } else if (parts[1].equals("connects")) {

                String station = parts[2];

                connect(source, station);

            } else if (parts[1].equals("disconnects")) {

                String station = parts[2];

                disconnect(source, station);
            }
        }

        sc.close();
    }
}