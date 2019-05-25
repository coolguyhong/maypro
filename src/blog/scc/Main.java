package blog.scc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int V, E, order;
    private static int[] visited;
    private static boolean[] checked;
    private static Deque<Integer> dq;
    private static ArrayList<Integer>[] links, reverse_links;
    private static ArrayList<Integer> ans;
    private static ArrayList<ArrayList<Integer>> answer;

    // 블로그, 백준 알고리즘
    // https://code0xff.tistory.com/16?category=723759
    // https://www.acmicpc.net/problem/2150
    // 그래프 scc(2150): 코사라주 알고리즘
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        visited = new int[V+1];
        checked = new boolean[V+1];
        links = new ArrayList[V+1];
        reverse_links = new ArrayList[V+1];
        for (int i = 1; i <= V; i++) {
            links[i] = new ArrayList<>();
            reverse_links[i] = new ArrayList<>();
        }

        int a, b;
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            links[a].add(b);
            reverse_links[b].add(a);
        }

        dq = new ArrayDeque<>();
        for (int i = 1; i <= V; i++) {
            if (visited[i] != 0) {
                continue;
            }

            dfs(i);
        }

        answer = new ArrayList<>();
        while (!dq.isEmpty()) {
            int n = dq.pollLast();
            if (checked[n]) {
                continue;
            }

            ans = new ArrayList<>();
            scc(n);
            if (ans.size() > 0) {
                Collections.sort(ans);
                answer.add(ans);
            }
        }

        Collections.sort(answer, (c, d) -> Integer.compare(c.get(0), d.get(0)));
        bw.write(answer.size() + "\n");
        for (ArrayList<Integer> list : answer) {
            for (int result : list) {
                bw.write(result + " ");
            }
            bw.write("-1\n");
        }
        bw.close();
    }

    private static void scc(int n) {
        checked[n] = true;
        for (int c : reverse_links[n]) {
            if (checked[c]) {
                continue;
            }

            scc(c);
        }
        ans.add(n);
    }

    private static void dfs(int n) {
        visited[n] = ++order;

        for (int c : links[n]) {
            if (visited[c] != 0) {
                continue;
            }

            dfs(c);
        }

        dq.offer(n);
    }
}
