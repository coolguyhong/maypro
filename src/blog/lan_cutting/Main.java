package blog.lan_cutting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int K, N;
    private static long[] lan;

    // 블로그 & 백준 알고리즘
    // https://code0xff.tistory.com/47?category=723759
    // https://www.acmicpc.net/problem/1654
    // 랜선자르기(1654): 이분탐색
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        lan = new long[K+1];
        for (int i = 1; i <= K; i++) {
            lan[i] = Integer.parseInt(br.readLine());
        }

        long min = 0;
        long max = Integer.MAX_VALUE;
        long ans = 0;
        while (min <= max) {
            long temp = (min + max) / 2;
            int cnt = 0;
            for (int i = 1; i <= K; i++) {
                cnt += lan[i] /temp;
            }

            if (cnt >= N) {
                min = temp + 1;
                if (temp > ans) {
                    ans = temp;
                }
            } else {
                max = temp - 1;
            }
        }

        bw.write(ans + "\n");
        bw.close();
    }
}
