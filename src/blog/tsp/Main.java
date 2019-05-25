package blog.tsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static final int max = (int) 1e9 + 1;
    private static int N, P, size;
    private static int[][] A, D;
    private static String onOff;

    // blog, 백준 알고리즘
    // https://code0xff.tistory.com/48?category=723759
    // https://www.acmicpc.net/problem/1102
    // 발전소: tsp(외판원, NP, 비트마스크)
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        A = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        size = 1 << N;
        D = new int[size][2];
        for (int i = 0; i < size; i++) {
            D[i][0] = max;
        }

        int index = 0;
        int cnt = 0;
        onOff = br.readLine();
        for (int i = 1; i <= N; i++) {
            index <<= 1;
            if (onOff.charAt(N-i) == 'Y') {
                index++;
                cnt++;
            }
        }

        P = Integer.parseInt(br.readLine());
        if (P == 0 || P <= cnt) {
            bw.write("0\n");
            bw.close();
            return;
        }

        if (cnt == 0) {
            bw.write("-1\n");
            bw.close();
            return;
        }

        D[index][0] = 0;
        D[index][1] = cnt;
        for (int i = 0; i < size; i++) {
            if (D[i][0] == max) {
                continue;
            }

            for (int j = 1; j <= N; j++) {
                if ((i & (1 << (j-1))) == 0) {
                    continue;
                }

                for (int k = 1; k <= N; k++) {
                    if ((i & (1 << (k-1))) != 0) {
                        continue;
                    }

                    if (D[i + (1 << (k-1))][0] > D[i][0] + A[j][k]) {
                        D[i + (1 << (k-1))][0] = D[i][0] + A[j][k];
                        D[i + (1 << (k-1))][1] = D[i][1] + 1;
                    }
                }
            }
        }

        int ans = max;
        for (int i = 0; i < size; i++) {
            if (D[i][1] >= P && D[i][0] < ans) {
                ans = D[i][0];
            }
        }

        bw.write(ans + "\n");
        bw.close();
    }
}
