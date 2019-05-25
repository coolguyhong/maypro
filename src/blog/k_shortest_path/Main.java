package blog.k_shortest_path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int n, m, k;
    private static Node[] nodes;
    private static Queue<Integer>[] D;

    // 블로그, 백준
    // https://code0xff.tistory.com/13?category=723759
    // https://www.acmicpc.net/problem/1854
    // k번째 최단거리
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        nodes = new Node[n+1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        int a, b, c;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            nodes[a].addLink(new Link(nodes[b], c));
        }

        dijkstra();

        for (int i = 1; i <= n; i++) {
            if (D[i].size() < k) {
                bw.write("-1\n");
            } else {
                bw.write(D[i].poll() + "\n");
            }
        }
        bw.close();
    }

    private static void dijkstra() {
        D = new PriorityQueue[n+1];
        for (int i = 1; i <= n; i++) {
            D[i] = new PriorityQueue<>(k, (a, b) -> Integer.compare(b, a));
        }

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        D[1].add(0);
        pq.add(new int[]{1, 0});

        int[] n;
        while (!pq.isEmpty()) {
            n = pq.poll();

            for (Link link : nodes[n[0]].links) {
                int t = link.target.no;
                int v = link.weight + n[1];

                if (D[t].size() < k) {
                    D[t].add(v);
                    pq.add(new int[]{t, v});
                } else if (v < D[t].peek()) {
                    D[t].poll();
                    D[t].add(v);
                    pq.add(new int[]{t, v});
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