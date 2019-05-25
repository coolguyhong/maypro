package basic_edu.baekjoon.geometry.convex_hull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N;
    private static Point p1;
    private static Point[] p;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/1708
    // 볼록 껍질(1708): 기하 알고리즘, ccw, 그라함 스캔(Graham's Scan)
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        p = new Point[N];
        p1 = new Point(40001, 40001);

        long x, y;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            x = Long.parseLong(st.nextToken());
            y = Long.parseLong(st.nextToken());

            p[i] = new Point(x, y);
            if (y < p1.y || (y == p1.y && x < p1.x)) {
                p1.x = x;
                p1.y = y;
            }
        }

        // ccw 내림차순 정리, 일직선일 경우 거리가 가까운 순
        Arrays.sort(p, (a, b) -> {
            long ccw = ccw(p1, a, b);
            if (ccw == 0) {
                return Long.compare(dist(p1, a), dist(p1, b));
            }
            return Long.compare(0, ccw);
        });

        Deque<Point> ad = new ArrayDeque<>();
        ad.add(p[0]);
        ad.add(p[1]);

        for (int i = 0; i < N; i++) {
            while (ad.size() >= 2) {
                Point p1 = ad.pollLast();
                Point p0 = ad.peekLast();
                Point p2 = p[i];

                if (ccw(p0, p1, p2) > 0) {
                    ad.add(p1);
                    break;
                }
            }
            ad.add(p[i]);
        }

        bw.write(ad.size() + "\n");
        bw.close();
    }

    private static long dist(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    private static long ccw(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
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
