package practice_edu.koitp.etc.treasure_island;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, M, T;
    private static final int max = 2000001;
    private static int[] F, B;
    private static Node[] nodes;

    // koitp.org
    // https://koitp.org/problem/SDS_PRO_4_3/read/
    // 보물섬(그래프): 다익스트라 알고리즘
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        nodes = new Node[N+1];
        F = new int[N+1];
        B = new int[N+1];

        for (int i = 1; i <= N; i++) {
            F[i] = max;
            B[i] = max;
            nodes[i] = new Node(i);
        }

        T = Integer.parseInt(br.readLine());

        int x, y, z;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            z = Integer.parseInt(st.nextToken());

            nodes[x].addLink(new Link(nodes[y], z));
        }

        dijkstraToT();
        if (F[T] == max) {
            bw.write("NO\n");
            bw.close();
        } else {
            dijkstraTo1();
            if (B[1] == max) {
                bw.write("NO\n");
                bw.close();
            } else {
                bw.write("YES\n");
                bw.write((F[T] + B[1]) + "\n");
                bw.close();
            }
        }
    }

    private static void dijkstraTo1() {
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        B[T] = 0;
        pq.add(new int[]{T, B[T]});

        int[] n;
        while (!pq.isEmpty()) {
            n = pq.poll();
            if (B[n[0]] < n[1]) {
                continue;
            }

            for (Link link : nodes[n[0]].links) {
                if (B[link.target.no] > B[n[0]] + link.weight) {
                    B[link.target.no] = B[n[0]] + link.weight;
                    pq.add(new int[]{link.target.no, B[link.target.no]});
                }
            }
        }
    }

    private static void dijkstraToT() {
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        F[1] = 0;
        pq.add(new int[]{1, F[1]});

        int[] n;
        while (!pq.isEmpty()) {
            n = pq.poll();
            if (F[n[0]] < n[1]) {
                continue;
            }

            for (Link link : nodes[n[0]].links) {
                if (F[link.target.no] > F[n[0]] + link.weight) {
                    F[link.target.no] = F[n[0]] + link.weight;
                    pq.add(new int[]{link.target.no, F[link.target.no]});
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
