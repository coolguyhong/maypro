package basic_edu.baekjoon.hw.road_network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, K, D, max, min;
    private static Node[] nodes;
    private static int[][] parents, MAX, MIN;
    private static int[] depth;


    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        int n = N;
        while (n > 0) {
            n /= 2;
            D++;
        }

        nodes = new Node[N+1];
        parents = new int[D][N+1];
        MAX = new int[D][N+1];
        MIN = new int[D][N+1];
        depth = new int[N+1];

        for (int i = 1; i <= N; i++) {
            nodes[i] = new Node(i);
        }

        int a, b, c;
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            nodes[a].addLink(new Link(nodes[b], c));
            nodes[b].addLink(new Link(nodes[a], c));
        }

        bfs();
        fillParents();

        K = Integer.parseInt(br.readLine());
        int d, e;
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            d = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());

            bw.write(min + " " + max +  "\n");
        }
    }

    private static void lca(int a, int b) {
        if (depth[b] > depth[a]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];
        int d = 0;
        max = -1;
        min = 1000001;
        while (diff > 0) {
            if (diff % 2 == 1) {
                a = parents[d][a];
                max = max(max, MAX[d][a]);
                min = min(min, MIN[d][a]);
            }

            diff /= 2;
            d++;
        }

        if (a == b) {
            return;
        }

        for (d = D-1; d >= 0; d--) {
            if (parents[d][a] == parents[d][b]) {
                continue;
            }

            max = max(max, MAX[d][a]);
            max = max(max, MAX[d][b]);
            min = min(min, MIN[d][a]);
            min = min(min, MIN[d][b]);

            a = parents[d][a];
            b = parents[d][b];
        }

        max = max(max, MAX[0][a]);
        max = max(max, MAX[0][b]);
        min = min(min, MIN[0][a]);
        min = min(min, MIN[0][b]);
    }

    private static void fillParents() {
        for (int d = 1; d < D; d++) {
            for (int i = 1; i <= N; i++) {
                parents[d][i] = parents[d-1][parents[d-1][i]];
                MAX[d][i] = max(MAX[d-1][i], MAX[d-1][parents[d-1][i]]);
                MIN[d][i] = min(MIN[d-1][i], MIN[d-1][parents[d-1][i]]);
            }
        }
    }

    private static void bfs() {
        Queue<Node> q = new LinkedList<>();
        q.add(nodes[1]);
        depth[1] = 0;
        parents[0][1] = 0;

        Node n;
        while (!q.isEmpty()) {
            n = q.poll();
            for (Link link : n.links) {
                if (depth[link.target.no] != 0) {
                    continue;
                }

                depth[link.target.no] = depth[n.no] + 1;
                parents[0][link.target.no] = n.no;
                MAX[0][link.target.no] = link.weight;
                MIN[0][link.target.no] = link.weight;
                q.add(link.target);
            }
        }
    }

    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private static int min(int a, int b) {
        return (a < b) ? a : b;
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
