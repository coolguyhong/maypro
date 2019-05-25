package practice_edu.koitp.day2.become_one;

import java.io.*;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;

    private static int N;
    private static int[] D;

    // koitp.org
    // https://koitp.org/problem/BECOME_ONE/read/
    // DP
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        D = new int[N+1];
        D[1] = 0;
        for (int i = 2; i <= N; i++) {
            D[i] = D[i-1] + 1;
            if (i % 2 == 0) {
                D[i] = min(D[i], D[i/2] + 1);
            }
            if (i % 3 == 0) {
                D[i] = min(D[i], D[i/3] + 1);
            }
        }

        bw.write(D[N] + "\n");
        bw.close();
    }

    private static int min(int a, int b) {
        return a < b ? a : b;
    }
}
