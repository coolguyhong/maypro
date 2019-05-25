package samsung.sw0427;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, C, K;
    private static List<Point>[] sd, ed;
    private static Point S, E;

    // sw 0427
    // 기하
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            S = new Point(0, 0);
            E = new Point(K, K);

            sd = new ArrayList[C+1];
            ed = new ArrayList[C+1];
            for (int i = 1; i <= C; i++) {
                sd[i] = new ArrayList<>();
                ed[i] = new ArrayList<>();
            }

            long x, y;
            int c;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                x = Long.parseLong(st.nextToken());
                y = Long.parseLong(st.nextToken());
                c = Integer.parseInt(st.nextToken());

                sd[c].add(new Point(x, y));
                ed[c].add(new Point(x, y));
            }

            for (int i = 1; i <= C; i++) {
                Collections.sort(sd[i], (a, b) -> {
                    long ccw = ccw(S, a, b);
                    if (ccw == 0) {
                        return Double.compare(dist(S, b), dist(S, a));
                    }
                    return Long.compare(0, ccw);
                });
            }

            for (int i = 1; i <= C; i++) {
                Collections.sort(ed[i], (a, b) -> {
                    long ccw = ccw(E, a, b);
                    if (ccw == 0) {
                        return Double.compare(dist(E, b), dist(E, a));
                    }
                    return Long.compare(0, ccw);
                });
            }

            boolean notCrossed = true;
            for (int i = 1; i <= C; i++) {
                if (isCrossed(S, E, sd[i].get(0), sd[i].get(sd[i].size()-1))) {
                    notCrossed = false;
                    break;
                }
            }

            double ans = Double.MAX_VALUE;
            if (notCrossed) {
                ans = dist(S, E);
            } else {
                List<Point> candidateFromS = new ArrayList<>();
                for (int i = 1; i <= C; i++) {
                    int s = 0;
                    for (int j = 1; j <= C; j++) {
                        if (i == j) {
                            continue;
                        }

                        if (isCrossed(S, sd[i].get(0), sd[j].get(0), sd[j].get(sd[j].size()-1))) {
                            s++;
                            break;
                        }
                    }

                    if (s == 0) {
                        candidateFromS.add(sd[i].get(0));
                    }
                }
                for (int i = 1; i <= C; i++) {
                    int s = 0;
                    for (int j = 1; j <= C; j++) {
                        if (i == j) {
                            continue;
                        }

                        if (isCrossed(S, sd[i].get(sd[i].size()-1), sd[j].get(0), sd[j].get(sd[j].size()-1))) {
                            s++;
                            break;
                        }
                    }

                    if (s == 0) {
                        candidateFromS.add(sd[i].get(sd[i].size()-1));
                    }
                }

                List<Point> candidateFromE = new ArrayList<>();
                for (int i = 1; i <= C; i++) {
                    int e = 0;
                    for (int j = 1; j <= C; j++) {
                        if (i == j) {
                            continue;
                        }

                        if (isCrossed(S, ed[i].get(0), ed[j].get(0), ed[j].get(ed[j].size()-1))) {
                            e++;
                            break;
                        }
                    }

                    if (e == 0) {
                        candidateFromE.add(ed[i].get(0));
                    }
                }
                for (int i = 1; i <= C; i++) {
                    int e = 0;
                    for (int j = 1; j <= C; j++) {
                        if (i == j) {
                            continue;
                        }

                        if (isCrossed(S, ed[i].get(ed[i].size()-1), ed[j].get(0), ed[j].get(ed[j].size()-1))) {
                            e++;
                            break;
                        }
                    }

                    if (e == 0) {
                        candidateFromE.add(ed[i].get(ed[i].size()-1));
                    }
                }

                for (Point cfs : candidateFromS) {
                    for (Point cfe : candidateFromE) {
                        double[] A = findA(cfs, E, cfe);
                        if (A != null && ans > dist(S, A) + dist(E, A)) {
                            ans = dist(S, A) + dist(E, A);
                        }
                    }
                }
            }

            bw.write("#" + testCase + " " + ans + "\n");
        }
        bw.close();
    }

    private static double[] findA(Point cfs, Point e, Point cfe) {
        double temp = (double) cfs.x / (double) cfs.y;
        double y = ((double) cfe.y * (double) e.x - (double) cfe.x * (double) e.y) /
                (((double) cfe.y - (double) e.y) * temp - ((double) cfe.x - (double) e.x));
        double x = temp * y;
        if (x >= 0 && x <= K && y >= 0 && y <= K) {
            return new double[]{x, y};
        }
        return null;
    }

    private static boolean isCrossed(Point p1, Point p2, Point p3, Point p4) {
        long chk1 = ccw(p1, p2, p3) * ccw(p1, p2, p4);
        long chk2 = ccw(p3, p4, p1) * ccw(p3, p4, p2);

        if (chk1 == 0 && chk2 == 0) {
            return isOverlapped(p1, p2, p3, p4);
        }

        return chk1 < 0 && chk2 < 0;
    }

    private static boolean isOverlapped(Point p1, Point p2, Point p3, Point p4) {
        if (min(p1.x, p2.x) > max(p3.x, p4.x)) {
            return false;
        }
        if (max(p1.x, p2.x) < min(p3.x, p4.x)) {
            return false;
        }

        if (min(p1.y, p2.y) > max(p3.y, p4.y)) {
            return false;
        }
        if (min(p1.y, p2.y) < max(p3.y, p4.y)) {
            return false;
        }

        return true;
    }

    private static double dist(Point s, Point b) {
        Long x = new Long((s.x - b.x) * (s.x - b.x));
        Long y = new Long((s.y - b.y) * (s.y - b.y));
        double X = x.doubleValue();
        double Y = y.doubleValue();
        return Math.sqrt(X + Y);
    }

    private static double dist(Point p, double[] a) {
        double x = (double) p.x;
        double y = (double) p.y;
        double X = (x - a[0]) * (x - a[0]);
        double Y = (y - a[1]) * (y - a[1]);
        return Math.sqrt(X + Y);
    }

    private static long ccw(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    private static long min(long a, long b) {
        return a < b ? a : b;
    }

    private static long max(long a, long b) {
        return a > b ? a : b;
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
