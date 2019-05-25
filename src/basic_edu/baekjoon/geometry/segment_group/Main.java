package basic_edu.baekjoon.geometry.segment_group;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N;
    private static int[] D, cnt;
    private static Line[] lines;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/2162
    // 선분 그룹(2162): ccw, union-find
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        D = new int[N+1];
        cnt = new int[N+1];
        lines = new Line[N+1];

        long x1, y1, x2, y2;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            x1 = Long.parseLong(st.nextToken());
            y1 = Long.parseLong(st.nextToken());
            x2 = Long.parseLong(st.nextToken());
            y2 = Long.parseLong(st.nextToken());

            lines[i] = new Line(x1, y1, x2, y2);
            D[i] = i;
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    continue;
                }

                if (isUnion(i, j)) {
                    continue;
                }

                if (isCrossed(lines[i], lines[j])) {
                    union(i, j);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            cnt[D[i]]++;
        }

        int max = 0;
        int group = 0;
        for (int i = 1; i <= N; i++) {
            if (cnt[i] > max) {
                max = cnt[i];
            }

            if (cnt[i] != 0) {
                group++;
            }
        }

        bw.write(group + "\n");
        bw.write(max + "\n");
        bw.close();
    }

    private static boolean isCrossed(Line a, Line b) {
        long chk1 = (a.x1 * a.y2 + a.x2 * b.y1 + b.x1 * a.y1 - a.y1 * a.x2 - a.y2 * b.x1 - b.y1 * a.x1) *
                (a.x1 * a.y2 + a.x2 * b.y2 + b.x2 * a.y1 - a.y1 * a.x2 - a.y2 * b.x2 - b.y2 * a.x1);
        long chk2 = (b.x1 * b.y2 + b.x2 * a.y1 + a.x1 * b.y1 - b.y1 * b.x2 - b.y2 * a.x1 - a.y1 * b.x1) *
                (b.x1 * b.y2 + b.x2 * a.y2 + a.x2 * b.y1 - b.y1 * b.x2 - b.y2 * a.x2 - a.y2 * b.x1);

        if (chk1 == 0 && chk2 == 0) {
            return isOverlapped(a, b);
        }

        return chk1 <= 0 && chk2 <= 0;
    }

    private static boolean isOverlapped(Line a, Line b) {
        if (max(a.x1, a.x2) < min(b.x1, b.x2)) {
            return false;
        }
        if (min(a.x1, a.x2) > max(b.x1, b.x2)) {
            return false;
        }

        if (max(a.y1, a.y2) < min(b.y1, b.y2)) {
            return false;
        }
        if (min(a.y1, a.y2) > max(b.y1, b.y2)) {
            return false;
        }

        return true;
    }

    private static boolean isUnion(int i, int j) {
        return find(i) == find(j);
    }

    private static int find(int a) {
        if (D[a] == a) {
            return a;
        } else {
            return D[a] = find(D[a]);
        }
    }

    private static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa != pb) {
            D[pb] = pa;
        }
    }

    private static long max(long a, long b) {
        return a > b ? a : b;
    }

    private static long min(long a, long b) {
        return a < b ? a : b;
    }
}

class Line {
    long x1;
    long y1;
    long x2;
    long y2;

    Line(long x1, long y1, long x2, long y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}
