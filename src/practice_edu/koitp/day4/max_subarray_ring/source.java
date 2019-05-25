package practice_edu.koitp.day4.max_subarray_ring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N;
    private static int[] A, D1, D2;


    // koitp.org
    // https://koitp.org/problem/MAX_SUBARRAY_RING/read/
    // 최대구간합(환): dp, brute-force
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            A = new int[N+1];
            D1 = new int[N+1];
            D2 = new int[N+1];

            int sum = 0;
            for (int i = 1; i <= N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
                sum += A[i];
            }

            D2[1] = A[1];
            D1[1] = A[1];
            for (int i = 2; i <= N; i++) {
                D1[i] = max(D1[i-1], 0) + A[i];
                D2[i] = min(D2[i-1], 0) + A[i];
            }

            int ans = 0;
            for (int i = 1; i <= N; i++) {
                ans = max(ans, max(D1[i], sum - D2[i]));
            }

            bw.write(ans + "\n");
        }
        bw.close();
    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }

    private static int min(int a, int b) {
        return a < b ? a : b;
    }
}
