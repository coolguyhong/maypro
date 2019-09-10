package baekjoon.tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, M, K, idx;
    private static long[] tree, lazy;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/10999
    // 10999(구간 합 구하기 2): 세그먼트 트리 with Lazy Propagation
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        tree = new long[4*N];
        lazy = new long[4*N];

        int n = N;
        idx = 1;
        while (n > 0) {
            idx *= 2;
            n /= 2;
        }
        int leafSize = idx;
        idx--;
        for (int i = 1; i <= N; i++) {
            tree[idx + i] = Integer.parseInt(br.readLine());
        }

        for (int i = idx; i > 0; i--) {
            tree[i] = tree[i*2] + tree[i*2 + 1];
        }

        int a, b, c;
        long d;
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            if (a == 1) {
                d = Long.parseLong(st.nextToken());
                update(1, 1, leafSize, b, c, d);
            } else {
                bw.write(query(1, 1, leafSize, b, c) + "\n");
            }
        }
        bw.close();
    }

    private static long query(int node, int start, int end, int left, int right) {
        if (lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];
            if (start < end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (end < left || start > right) {
            return 0;
        }

        if (left <= start && right >= end) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        return query(node * 2, start, mid, left, right) +
                query(node * 2 + 1, mid + 1, end, left, right);
    }

    private static void update(int node, int start,  int end, int left, int right, long val) {
        if (lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];
            if (start < end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (end < left || start > right) {
            return;
        }

        if (left <= start && right >= end) {
            tree[node] += (end - start + 1) * val;
            if (start < end) {
                lazy[node * 2] += val;
                lazy[node * 2 + 1] += val;
            }
            return;
        }

        int mid = (start + end) / 2;
        update(node * 2, start, mid, left, right, val);
        update(node * 2 + 1, mid + 1, end, left, right, val);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }
}
