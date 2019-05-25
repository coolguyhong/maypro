package basic_edu.baekjoon.geometry.polygon_area;

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
    private static Point[] p;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/2166
    // 다각형의 면적(2166): ccw
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        p = new Point[N+1];

        long x, y;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            x = Long.parseLong(st.nextToken());
            y = Long.parseLong(st.nextToken());

            p[i] = new Point(x, y);
        }

        long ans = 0;
        for (int i = 2; i < N; i++) {
            ans += ccw(p[1], p[i], p[i+1]);
        }

        ans = Math.abs(ans);
        if (ans % 2 == 1) {
            bw.write((ans / 2) + ".5\n");
        } else {
            bw.write((ans / 2) + ".0\n");
        }
        bw.close();
    }

    private static long ccw(Point p1, Point p2, Point p3) {
        return p1.x * p2.y + p2.x * p3.y + p3.x * p1.y
                - p1.y * p2.x - p2.y * p3.x - p3.y * p1.x;
    }
}

class Point {
    long x;
    long y;

    Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}
