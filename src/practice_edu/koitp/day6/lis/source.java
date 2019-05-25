package practice_edu.koitp.day6.lis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class source {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, idx;
    private static int[][] A;
    private static int[] trees;

    // koitp.org
    // https://koitp.org/problem/LONGEST_INCREASING_SUBSEQUENCE/read/
    // 최장 증가 부분 수열(LIS): indexed tree
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        A = new int[N+1][2];
        trees = new int[4*N];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i][0] = Integer.parseInt(st.nextToken());
            A[i][1] = i;
        }

        idx = 1;
        while (idx < N) {
            idx *= 2;
        }
        idx--;

        // 값에 대해선 오름차순, 인덱스에 대해선 내림차순
        Arrays.sort(A, 1, N+1, (a, b) -> {
            int compare = Integer.compare(a[0], b[0]);
            if (compare == 0) {
                return Integer.compare(b[1], a[1]);
            }
            return compare;
        });

        for (int i = 1; i <= N; i++) {
            int maxLis = query(idx+1, A[i][1] + idx - 1);
            update(A[i][1] + idx, maxLis + 1);
        }

        bw.write(trees[1] + "\n");
        bw.close();
    }

    private static void update(int index, int num) {
        int temp = index;
        while (temp > 0) {
            if (num > trees[temp]) {
                trees[temp] = num;
                temp /= 2;
            } else {
                break;
            }
        }
    }

    private static int query(int s, int e) {
        int maxLis = 0;

        while (s <= e) {
            if (s % 2 == 1) {
                if (maxLis < trees[s]) {
                    maxLis = trees[s];
                }
            }

            if (e % 2 == 0) {
                if (maxLis < trees[e]) {
                    maxLis = trees[e];
                }
            }

            s = (s + 1) / 2;
            e = (e - 1) / 2;
        }
        return maxLis;
    }
}
