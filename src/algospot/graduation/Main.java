package algospot.graduation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static final int INF = 987654321;
    private static int N, K, M, L;
    private static int[] prerequisite, classes;
    private static int[][] D;

    // 알고스팟
    // https://algospot.com/judge/problem/read/GRADUATION
    // 졸업학기(GRADUATION): 비트마스크
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int C = Integer.parseInt(br.readLine());
        while (C-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            prerequisite = new int[N];
            classes = new int[M];
            D = new int[M][1 << N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                if (r == 0) {
                    prerequisite[i] = 0;
                    continue;
                }

                for (int j = 0; j < r; j++) {
                    prerequisite[i] |= 1 << Integer.parseInt(st.nextToken());
                }
            }

            for (int i = 0; i < M; i++) {
                Arrays.fill(D[i], -1);
                st = new StringTokenizer(br.readLine());
                int c = Integer.parseInt(st.nextToken());
                for (int j = 0; j < c; j++) {
                    classes[i] |= 1 << Integer.parseInt(st.nextToken());
                }
            }

            int ans = graduate(0, 0);
            bw.write((ans == INF ? "IMPOSSIBLE" : ans) + "\n");
        }
        bw.close();
    }

    private static int graduate(int semester, int taken) {
        if (Integer.bitCount(taken) >= K) {
            return 0;
        }

        if (semester == M) {
            return INF;
        }

        int ret = D[semester][taken];
        if (ret != -1) {
            return ret;
        }

        int canTake = classes[semester] & (~taken);
        
        // 사전 수업 안들은 것은 패스
        for (int i = 0; i < N; i++) {
            // canTake 에 해당 과목이 있어야 하고, 사전 교육 들어야 함
            if ((canTake & (1 << i)) > 0 && (taken & prerequisite[i]) != prerequisite[i]) {
                canTake &= ~(1 << i);
            }
        }

        ret = INF;
        for (int take = canTake; take > 0 ; take = (take - 1) & canTake) {
            if (Integer.bitCount(take) > L) {
                continue;
            }

            ret = Math.min(ret, graduate(semester+1, (taken | take)) + 1);
            D[semester][taken] = ret;
        }

        // 이번학기 아무것도 듣지 않을 경우
        ret = Math.min(ret, graduate(semester+1, taken));
        D[semester][taken] = ret;
        return ret;
    }
}
