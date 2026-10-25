import java.io.*;

public class Main1 {

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;

            do {
                c = read();
            } while (c <= ' ' && c != -1);

            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }

            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int rows = fs.nextInt();
        int cols = fs.nextInt();

        // Store the number of stones in each column.
        int[] heights = new int[cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (fs.next().charAt(0) == '*') {
                    heights[j]++;
                }
            }
        }

        int k = fs.nextInt();

        for (int step = 0; step < k; step++) {
            String direction = fs.next();

            int oldRows = rows;
            int oldCols = heights.length;

            // Count columns according to their stone heights.
            int[] freq = new int[oldRows + 1];

            for (int h : heights) {
                freq[h]++;
            }

            // Count stones in each row after gravity.
            int[] rowCounts = new int[oldRows];
            int count = 0;

            for (int h = oldRows; h >= 1; h--) {
                count += freq[h];
                rowCounts[oldRows - h] = count;
            }

            int[] newHeights = new int[oldRows];

            if (direction.equalsIgnoreCase("right")) {
                for (int j = 0; j < oldRows; j++) {
                    newHeights[j] = rowCounts[oldRows - 1 - j];
                }
            } else {
                for (int j = 0; j < oldRows; j++) {
                    newHeights[j] = rowCounts[j];
                }
            }

            // Dimensions change after a 90-degree rotation.
            heights = newHeights;
            rows = oldCols;
        }

        // Reconstruct the final box.
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < heights.length; j++) {
                if (j > 0) {
                    output.append(' ');
                }

                if (heights[j] >= rows - i) {
                    output.append('*');
                } else {
                    output.append('.');
                }
            }
            output.append('\n');
        }

        System.out.print(output);
    }
}