import java.util.*;

public class Main6{

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Stick {
        int x1, y1, x2, y2;
        ArrayList<Point> points = new ArrayList<>();

        Stick(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;

            points.add(new Point(x1 * 100, y1 * 100));
            points.add(new Point(x2 * 100, y2 * 100));
        }

        double length() {
            return Math.hypot(x2 - x1, y2 - y1);
        }
    }

    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    static ArrayList<Stick> sticks = new ArrayList<>();
    static ArrayList<Point> nodes = new ArrayList<>();
    static ArrayList<Edge> edges = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    static HashMap<Long, Integer> ids = new HashMap<>();

    static int[] parent, state;
    static int cycleU = -1, cycleV = -1;

    static long key(Point p) {
        return (((long) p.x) << 32) | (p.y & 0xffffffffL);
    }

    static double cross(double ax, double ay,
                        double bx, double by) {
        return ax * by - ay * bx;
    }

    static void findIntersections(Stick a, Stick b) {
        double rx = a.x2 - a.x1;
        double ry = a.y2 - a.y1;
        double sx = b.x2 - b.x1;
        double sy = b.y2 - b.y1;

        double denominator = cross(rx, ry, sx, sy);

        // Parallel sticks do not have a unique intersection.
        if (Math.abs(denominator) < 1e-9) {
            return;
        }

        double qx = b.x1 - a.x1;
        double qy = b.y1 - a.y1;

        double t = cross(qx, qy, sx, sy) / denominator;
        double u = cross(qx, qy, rx, ry) / denominator;

        if (t < -1e-9 || t > 1 + 1e-9 ||
            u < -1e-9 || u > 1 + 1e-9) {
            return;
        }

        int x = (int) Math.round((a.x1 + t * rx) * 100);
        int y = (int) Math.round((a.y1 + t * ry) * 100);

        Point p = new Point(x, y);
        a.points.add(p);
        b.points.add(p);
    }

    static int getId(Point p) {
        long k = key(p);

        if (ids.containsKey(k)) {
            return ids.get(k);
        }

        int id = nodes.size();
        ids.put(k, id);
        nodes.add(p);
        graph.add(new ArrayList<Integer>());

        return id;
    }

    static double position(Point p, Stick s) {
        double dx = (s.x2 - s.x1) * 100.0;
        double dy = (s.y2 - s.y1) * 100.0;
        double px = p.x - s.x1 * 100.0;
        double py = p.y - s.y1 * 100.0;

        return (px * dx + py * dy) /
               (dx * dx + dy * dy);
    }

    static void buildGraph() {
        for (Stick s : sticks) {
            Collections.sort(s.points, new Comparator<Point>() {
                public int compare(Point a, Point b) {
                    return Double.compare(position(a, s),
                                          position(b, s));
                }
            });

            ArrayList<Point> unique = new ArrayList<>();
            HashSet<Long> seen = new HashSet<>();

            for (Point p : s.points) {
                if (seen.add(key(p))) {
                    unique.add(p);
                }
            }

            for (int i = 0; i < unique.size() - 1; i++) {
                int u = getId(unique.get(i));
                int v = getId(unique.get(i + 1));

                if (u == v) {
                    continue;
                }

                int edgeId = edges.size();
                edges.add(new Edge(u, v));
                graph.get(u).add(edgeId);
                graph.get(v).add(edgeId);
            }
        }
    }

    static boolean dfs(int u, int parentEdge) {
        state[u] = 1;

        for (int edgeId : graph.get(u)) {
            if (edgeId == parentEdge) {
                continue;
            }

            Edge e = edges.get(edgeId);
            int v = (e.u == u) ? e.v : e.u;

            if (state[v] == 0) {
                parent[v] = u;

                if (dfs(v, edgeId)) {
                    return true;
                }
            } else if (state[v] == 1) {
                cycleU = u;
                cycleV = v;
                return true;
            }
        }

        state[u] = 2;
        return false;
    }

    static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y) / 100.0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        double totalLength = 0;

        for (int i = 0; i < n; i++) {
            Stick s = new Stick(
                sc.nextInt(), sc.nextInt(),
                sc.nextInt(), sc.nextInt()
            );

            sticks.add(s);
            totalLength += s.length();
        }

        sc.close();

        // Find all pairwise intersections.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                findIntersections(sticks.get(i), sticks.get(j));
            }
        }

        buildGraph();

        int size = nodes.size();
        parent = new int[size];
        state = new int[size];

        Arrays.fill(parent, -1);

        boolean found = false;

        for (int i = 0; i < size; i++) {
            if (state[i] == 0 && dfs(i, -1)) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Abandoned");
            return;
        }

        // Recover the cycle vertices.
        ArrayList<Point> polygon = new ArrayList<>();
        int current = cycleU;

        while (true) {
            polygon.add(nodes.get(current));

            if (current == cycleV) {
                break;
            }

            current = parent[current];

            if (current == -1) {
                System.out.println("Abandoned");
                return;
            }
        }

        double perimeter = 0;
        double twiceArea = 0;
        int m = polygon.size();

        for (int i = 0; i < m; i++) {
            Point a = polygon.get(i);
            Point b = polygon.get((i + 1) % m);

            perimeter += distance(a, b);

            double ax = a.x / 100.0;
            double ay = a.y / 100.0;
            double bx = b.x / 100.0;
            double by = b.y / 100.0;

            twiceArea += ax * by - ay * bx;
        }

        double kalyanArea = Math.abs(twiceArea) / 2.0;

        // Remove the sticks used by Kalyan's shape.
        double remaining = Math.max(0, totalLength - perimeter);

        // Maximum possible area for a given perimeter is a circle.
        double computerArea =
                remaining * remaining / (4.0 * Math.PI);

        if (kalyanArea > computerArea + 1e-7) {
            System.out.println("Kalyan");
        } else {
            System.out.println("Computer");
        }
    }
}