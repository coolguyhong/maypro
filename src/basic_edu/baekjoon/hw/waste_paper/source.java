package basic_edu.baekjoon.hw.waste_paper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int M, N;
    private static char[][] A;
    private static int[][][] D;

    // koitp.org
    // https://koitp.org/problem/TWICE_PICK/read/
    // TWICE_PICK(폐지 두 번 줍기): DP
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            A = new char[M+1][N+1];

            for (int i = 1; i <= M; i++) {
                String tmp = br.readLine();
                for (int j = 1; j <= N; j++) {
                    A[i][j] = tmp.charAt(j-1);
                }
            }

            D = new int[M+N+1][N+1][N+1];
            for (int k = 0; k < M+N; k++) {
                for (int r = 0; r <= N; r++) {
                    for (int c = 0; c <= N; c++) {
                        D[k][r][c] = 0;
                    }
                }
            }

            // 초기값 세팅
            D[0][1][1] = A[1][1] == '*' ? 1 : 0;
            for (int m = 1; m <= N+M-2; m++) {
                for (int c1 = 1; c1 <= N; c1++) {
                    int r1 = m - (c1 - 1) + 1;
                    if (r1 < 1 || r1 > M) {
                        continue;
                    }
                    if (A[r1][c1] == '#') {
                        continue;
                    }

                    for (int c2 = 1; c2 <= c1; c2++) {
                        int r2 = m - (c2 - 1) + 1;
                        if (r2 < 1 || r2 > M) {
                            continue;
                        }
                        if (A[r2][c2] == '#') {
                            continue;
                        }

                        int cnt = A[r1][c1] == '*' ? 1 : 0;
                        if(c1 != c2) {
                            cnt += A[r2][c2] == '*' ? 1 : 0;
                        }

                        D[m][c1][c2] = max(max(D[m-1][c1][c2],D[m-1][c1-1][c2-1]),
                                max(D[m-1][c1-1][c2],D[m-1][c1][c2-1])) + cnt;
                    }
                }
            }
            bw.write(D[N+M-2][N][N] + "\n");
        }
        bw.close();
    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }
}
