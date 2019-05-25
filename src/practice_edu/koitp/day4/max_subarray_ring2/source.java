package practice_edu.koitp.day4.max_subarray_ring2;

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
    // https://koitp.org/problem/MAX_SUBARRAY_RING2/read/
    // 최대구간합(환)_2: DP, brute-force
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

            // 1.
            // 2 ~ N 까지 최대구간 합
            // N ~ 1 까지 최대 구간 합
            // 최종 ans = 2 ~ N 까지 최대 값 D1[i] + D2[i+2]
            D1[2] = A[2];
            for (int i = 3; i <= N; i++) {
                D1[i] = max(D1[i-1], 0) + A[i];
            }
            for (int i = 3; i <= N; i++) {
                D1[i] = max(D1[i], D1[i-1]);
            }

            D2[N] = A[N];
            for (int i = N-1; i >= 1; i--) {
                D2[i] = max(D2[i+1], 0) + A[i];
            }
            for (int i = N-1; i >= 1; i--) {
                D2[i] = max(D2[i], D2[i+1]);
            }

            int ans = A[1] + A[3];
            for (int i = 2; i < N-1; i++) {
                ans = max(ans, D1[i] + D2[i+2]);
            }

            // 2.
            // 1 ~ N 까지 최대구간 합
            // N-1 ~ 1 까지 최대 구간 합
            // 최종 ans = 1 ~ N-1 까지 최대 값 D1[i] + D2[i+2]
            D1[1] = A[1];
            for (int i = 2; i <= N; i++) {
                D1[i] = max(D1[i-1], 0) + A[i];
            }
            for (int i = 2; i <= N; i++) {
                D1[i] = max(D1[i], D1[i-1]);
            }

            D2[N-1] = A[N-1];
            for (int i = N-2; i >= 1; i--) {
                D2[i] = max(D2[i+1], 0) + A[i];
            }
            for (int i = N-2; i >= 1; i--) {
                D2[i] = max(D2[i], D2[i+1]);
            }

            for (int i = 1; i < N-2; i++) {
                ans = max(ans, D1[i] + D2[i+2]);
            }

            // 3.
            // 2 ~ N 까지 최소 구간 합
            // N-1 ~ 1 까지 최소 구간 합
            // 최종 ans = 2 ~ N-1 까지 총 합에서 뺀 값들 중 최고 sum - D1[i] - D2[i+2]
            D1[2] = A[2];
            for (int i = 3; i <= N; i++) {
                D1[i] = min(D1[i-1], 0) + A[i];
            }
            for (int i = 3; i <= N; i++) {
                D1[i] = min(D1[i], D1[i-1]);
            }

            D2[N-1] = A[N-1];
            for (int i = N-2; i >= 1; i--) {
                D2[i] = min(D2[i+1], 0) + A[i];
            }
            for (int i = N-2; i >= 1; i--) {
                D2[i] = min(D2[i], D2[i+1]);
            }

            for (int i = 2; i < N-2; i++) {
                ans = max(ans, sum - D1[i] - D2[i+2]);
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
