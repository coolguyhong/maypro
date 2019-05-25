package basic_edu.baekjoon.hw.lis;

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

    private static int N, idx, maxIdx;
    private static int[] D, prev, ans, lis_trees, idx_trees;
    private static int[][] A;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/14003
    // 가장 긴 증가하는 부분 수열 5(14003): index tree
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        A = new int[N+1][2];
        D = new int[N+1];
        prev = new int[N+1];
        ans = new int[N+1];
        lis_trees = new int[4*N];
        idx_trees = new int[4*N];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            D[i] = Integer.parseInt(st.nextToken());
            A[i][0] = D[i];
            A[i][1] = i;
        }

        Arrays.sort(A, 1, N+1, (a, b) -> {
            int compare = Integer.compare(a[0], b[0]);
            if (compare == 0) {
                return Integer.compare(b[1], a[1]);
            }
            return compare;
        });

        idx = 1;
        while (idx < N) {
            idx *= 2;
        }
        idx--;

        for (int i = 1; i <= N; i++) {
            int maxLis = query(idx + 1, A[i][1] + idx - 1);

            prev[A[i][1]] = maxIdx - idx;

            update(A[i][1] + idx, maxLis + 1);
        }

        bw.write(lis_trees[1] + "\n");
        
        int lastIdx = idx_trees[1] - idx;
        for (int i = lis_trees[1]; i >= 1; i--) {
            ans[i] = D[lastIdx];
            lastIdx = prev[lastIdx];
        }

        for (int i = 1; i <= lis_trees[1]; i++) {
            bw.write(ans[i] + " ");
        }
        bw.write("\n");
        bw.close();
    }

    private static void update(int index, int lis) {
        int temp = index;
        while (temp > 0) {
            if (lis > lis_trees[temp]) {
                lis_trees[temp] = lis;
                idx_trees[temp] = index;
                temp /= 2;
            } else {
                break;
            }
        }
    }

    private static int query(int s, int e) {
        int maxLis = 0;
        maxIdx = -1;

        while (s <= e) {
            if (s % 2 == 1) {
                if (maxLis < lis_trees[s]) {
                    maxLis = lis_trees[s];
                    maxIdx = idx_trees[s];
                }
            }

            if (e % 2 == 0) {
                if (maxLis < lis_trees[e]) {
                    maxLis = lis_trees[e];
                    maxIdx = idx_trees[e];
                }
            }

            s = (s + 1) / 2;
            e = (e - 1) / 2;
        }

        return maxLis;
    }
}