package practice_edu.koitp.day7.warp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, M;
    private static long max = 50000000001L;
    private static long[] D;
    private static Node[] nodes;

    // koitp.org
    // https://koitp.org/problem/WARP/read/
    // 워프: 그래프, 다익스트라(dijkstra) 최단거리 구하기
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        nodes = new Node[N+1];
        D = new long[N+1];

        for (int i = 1; i <= N; i++) {
            D[i] = max;
            nodes[i] = new Node(i);
        }

        M = Integer.parseInt(st.nextToken());
        int a, b, c;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            nodes[a].addLink(new Link(nodes[b], c));
        }

        dijkstra();

        bw.write((D[N] == max ? -1 : D[N]) + "\n");
        bw.close();
    }

    private static void dijkstra() {
        Queue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        D[1] = 0;
        pq.add(new long[]{1, D[1]});

        long[] n;
        while (!pq.isEmpty()) {
            n = pq.poll();
            if (D[(int) n[0]] < n[1]) {
                continue;
            }

            for (Link link : nodes[(int) n[0]].links) {
                if (D[link.target.no] > D[(int) n[0]] + link.weight) {
                    D[link.target.no] = D[(int) n[0]] + link.weight;
                    pq.add(new long[]{link.target.no, D[link.target.no]});
                }
            }
        }
    }
}

class Node {
    int no;
    List<Link> links = new ArrayList<>();

    Node(int no) {
        this.no = no;
    }

    void addLink(Link link) {
        links.add(link);
    }
}

class Link {
    Node target;
    int weight;

    Link(Node node, int weight) {
        this.target = node;
        this.weight = weight;
    }
}
