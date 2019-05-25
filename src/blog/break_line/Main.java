package blog.break_line;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int V, E, order;
    private static List<Integer>[] links;
    private static int[] visitedOrder;
    private static List<int[]> ans;

    // 블로그 & 백준 알고리즘
    // https://code0xff.tistory.com/8?category=723759
    // https://www.acmicpc.net/problem/11400
    // 단절선(11400): dfs 활용하여 단절선 구하기
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        visitedOrder = new int[V+1];
        links = new ArrayList[V+1];
        for (int i = 1; i <= V; i++) {
            links[i] = new ArrayList<>();
        }

        int a, b;
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            links[a].add(b);
            links[b].add(a);
        }

        order = 0;
        ans = new ArrayList<>();
        dfs(1, -1);

        Collections.sort(ans, (c, d) -> {
            int compare = Integer.compare(c[0], d[0]);
            if (compare == 0) {
                return Integer.compare(c[1], d[1]);
            }
            return compare;
        });

        bw.write(ans.size() + "\n");
        for (int[] answer : ans) {
            bw.write(answer[0] + " " + answer[1] + "\n");
        }
        bw.close();
    }

    private static int dfs(int n, int p) {
        if (visitedOrder[n] != 0) {
            return visitedOrder[n];
        }

        order++;
        visitedOrder[n] = order;
        int minLow = order;

        for (int c : links[n]) {
            if (c == p) {
                continue;
            }

            if (visitedOrder[c] != 0) {
                minLow = min(minLow, visitedOrder[c]);
            } else {
                int low = dfs(c, n);

                if (visitedOrder[n] < low) {
                    if (c < n) {
                        ans.add(new int[]{c, n});
                    } else {
                        ans.add(new int[]{n, c});
                    }
                }

                minLow = min(low, minLow);
            }
        }

        return minLow;
    }

    private static int min(int a, int b) {
        return a < b ? a : b;
    }
}
