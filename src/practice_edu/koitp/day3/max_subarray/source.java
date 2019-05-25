package practice_edu.koitp.day3.max_subarray;

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
    private static long[] A;

    // koitp.org
    // https://koitp.org/problem/MAX_SUBARRAY/read/
    // 최대구간합: dp, brute-force
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        A = new long[N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        long psum = 0;
        long ans = Long.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            psum = max(psum, 0) + A[i];
            ans = max(ans, psum);
        }

        bw.write(ans + "\n");
        bw.close();
    }

    private static long max(long a, long b) {
        return a > b ? a : b;
    }
}
