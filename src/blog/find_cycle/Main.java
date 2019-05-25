package blog.find_cycle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int n;
    private static int[] links, visited, starts;

    // 블로그 & 백준 알고리즘
    // https://code0xff.tistory.com/39?category=723759
    // https://www.acmicpc.net/problem/9466
    // 텀 프로젝트(9466): 싸이클 찾기, dfs 활용
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            n = Integer.parseInt(br.readLine());
            links = new int[n+1];
            visited = new int[n+1];
            starts = new int[n+1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                links[i] = Integer.parseInt(st.nextToken());
            }

            int ans = 0;
            for (int i = 1; i <= n; i++) {
                if (visited[i] != 0) {
                    continue;
                }

                ans += dfs(i, i);
            }

            bw.write((n - ans) + "\n");
        }
        bw.close();
    }

    // dfs 시적점 부터 dfs 돌면서 싸이클이 되는 노드 수 반환
    private static int dfs(int n, int start) {
        int cnt = 1;

        while (true) {
            if (visited[n] != 0) {
                // 시작점이 처음 dfs 들어 왔을 때의 start 와 다르면 싸이클이 아님
                if (starts[n] != start) {
                    return 0;
                }
                // 사이클이 맞음, 싸이클 되면서 이전까지 방문했던 노드 수 반환
                return cnt - visited[n];
            }

            visited[n] = cnt;
            starts[n] = start;
            n = links[n];
            cnt++;
        }
    }
}
