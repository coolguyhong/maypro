package algospot.tpath;

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

    private static int N, M;
    private static int[] D;
    private static int[][] path;

    // 알고스팟
    // https://algospot.com/judge/problem/read/TPATH
    // 여행 경로 정하기(TPATH): 그래프 크루스칼 알고리즘
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int C = Integer.parseInt(br.readLine());
        while (C-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            D = new int[N];
            path = new int[M][3];

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                path[i][0] = Integer.parseInt(st.nextToken());
                path[i][1] = Integer.parseInt(st.nextToken());
                path[i][2] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(path, (a, b) -> Integer.compare(a[2], b[2]));

            int ans = Integer.MAX_VALUE;
            for (int start = 0; start < M; start++) {
                if (start > 0 && path[start-1][2] == path[start][2]) {
                    continue;
                }

                for (int i = 0; i < N; i++) {
                    D[i] = i;
                }

                for (int i = start; i < M; i++) {
                    int pa = find(path[i][0], D);
                    int pb = find(path[i][1], D);

                    if (pa == pb) {
                        continue;
                    }

                    D[pb] = pa;
                    if (find(0, D) == find(N-1, D)) {
                        ans = Math.min(ans, path[i][2] - path[start][2]);
                        break;
                    }
                }

                if (find(0, D) != find(N-1, D)) {
                    break;
                }
            }

            bw.write(ans + "\n");
        }
        bw.close();
    }

    private static int find(int a, int[] d) {
        if (d[a] == a) {
            return a;
        } else {
            return d[a] = find(d[a], d);
        }
    }
}
