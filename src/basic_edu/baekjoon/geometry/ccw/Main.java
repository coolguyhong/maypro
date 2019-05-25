package basic_edu.baekjoon.geometry.ccw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static Point[] p = new Point[3];

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/11758
    // CCW(11758): 기하 알고리즘 ccw
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int x, y;
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            p[i] = new Point(x, y);
        }

        bw.write(ccw(p[0], p[1], p[2]) + "\n");
        bw.close();
    }

    private static int ccw(Point p1, Point p2, Point p3) {
        int temp = p1.x * p2.y + p2.x * p3.y + p3.x * p1.y
                - p1.y * p2.x - p2.y * p3.x - p3.y * p1.x;

        return Integer.compare(temp, 0);
    }
}

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

