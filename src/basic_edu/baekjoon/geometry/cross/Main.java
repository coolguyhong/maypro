package basic_edu.baekjoon.geometry.cross;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static Line line;
    private static Line[] sq;

    // 백준 알고리즘
    // https://www.acmicpc.net/problem/6439
    // 교차(6439): ccw, 기하
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            long xs = Long.parseLong(st.nextToken());
            long ys = Long.parseLong(st.nextToken());
            long xe = Long.parseLong(st.nextToken());
            long ye = Long.parseLong(st.nextToken());

            line = new Line(xs, ys, xe, ye);

            long xl = Long.parseLong(st.nextToken());
            long yt = Long.parseLong(st.nextToken());
            long xr = Long.parseLong(st.nextToken());
            long yb = Long.parseLong(st.nextToken());

            if (xl > xr) {
                long temp = xr;
                xr = xl;
                xl = temp;
            }

            if (yb > yt) {
                long temp = yt;
                yt = yb;
                yb = temp;
            }

            // 선분이 사각형 안에 있을 경우
            if (xs >= xl && xe <= xr && ys >= yb && ys <= yt && ye >= yb && ye <= yt) {
                bw.write("T\n");
                continue;
            }

            sq = new Line[4];
            sq[0] = new Line(xl, yb, xr, yb);
            sq[1] = new Line(xr, yb, xr, yt);
            sq[2] = new Line(xr, yt, xl, yt);
            sq[3] = new Line(xl, yt, xl, yb);



            boolean isCross = false;
            for (int i = 0; i < 4; i++) {
                if (isCrossed(line, sq[i])) {
                    isCross = true;
                    break;
                }
            }

            if (isCross) {
                bw.write("T\n");
            } else {
                bw.write("F\n");
            }
        }
        bw.close();
    }

    private static boolean isCrossed(Line a, Line b) {
        long chk1 = ((a.x2 - a.x1) * (b.y1 - a.y1) - (a.y2 - a.y1) * (b.x1 - a.x1)) *
                ((a.x2 - a.x1) * (b.y2 - a.y1) - (a.y2 - a.y1) * (b.x2 - a.x1));
        long chk2 = ((b.x2 - b.x1) * (a.y1 - b.y1) - (b.y2 - b.y1) * (a.x1 - b.x1)) *
                ((b.x2 - b.x1) * (a.y2 - b.y1) - (b.y2 - b.y1) * (a.x2 - b.x1));

        if (chk1 == 0 && chk2 == 0) {
            return isOverlapped(a, b);
        }

        return chk1 <= 0 && chk2 <= 0;
    }

    private static boolean isOverlapped(Line a, Line b) {
        if (min(a.x1, a.x2) > max(b.x1, b.x2)) {
            return false;
        }
        if (max(a.x1, a.x2) < min(b.x1, b.x2)) {
            return false;
        }

        if (min(a.y1, a.y2) > max(b.y1, b.y2)) {
            return false;
        }
        if (max(a.y1, a.y2) < min(b.y1, b.y2)) {
            return false;
        }

        return true;
    }

    private static long min(long a, long b) {
        return a < b ? a : b;
    }

    private static long max(long a, long b) {
        return a > b ? a : b;
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
