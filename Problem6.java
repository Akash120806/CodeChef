import java.util.*;

public class Problem6 {

    static int[][] corners = {
        {1, 2, 3},
        {1, 3, 5},
        {0, 1, 4},
        {0, 4, 5},
        {2, 3, 6},
        {2, 5, 7},
        {0, 4, 6},
        {0, 5, 7}
    };

    static int[][] faceCorners = {
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3}
    };

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        char[] cube = new char[24];

        for (int i = 0; i < 24; i++) {
            cube[i] = sc.next().charAt(0);
        }

       

        ArrayList<String> cornerColours = new ArrayList<>();

        for (int i = 0; i < 8; i++) {

            String s = "";

            for (int j = 0; j < 3; j++) {
                s += cube[corners[i][j]];
            }

            char[] temp = s.toCharArray();
            Arrays.sort(temp);

            cornerColours.add(new String(temp));
        }

      

        HashMap<String, Integer> count = new HashMap<>();

        for (String s : cornerColours) {

            if (count.containsKey(s)) {
                count.put(s, count.get(s) + 1);
            } else {
                count.put(s, 1);
            }
        }

    

        for (int i = 0; i < 8; i++) {

            String original = "";

            for (int j = 0; j < 3; j++) {
                original += cube[corners[i][j]];
            }

            char[] sorted = original.toCharArray();
            Arrays.sort(sorted);

            String key = new String(sorted);

          

            if (count.get(key) == 1) {

                char a = original.charAt(0);
                char b = original.charAt(1);
                char c = original.charAt(2);

                if (!isNormal(a, b, c)) {

                    char[] answer = {a, b, c};

                    Arrays.sort(answer);

                    System.out.println(
                        answer[0] + "" +
                        answer[1] + "" +
                        answer[2]
                    );

                    return;
                }
            }
        }

        sc.close();
    }

    static boolean isNormal(char a, char b, char c) {

        

        String s = "" + a + b + c;

        char[] arr = s.toCharArray();

        Arrays.sort(arr);

        String sorted = new String(arr);

        return s.equals(sorted);
    }
}