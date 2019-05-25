package algospot.christmas;

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

    private static final int mod = 20091101;
    private static int N, K;
    private static int[] D, psum;

    // 알고스팟
    // https://algospot.com/judge/problem/read/CHRISTMAS
    // 크리스마스 인형(christmas) : 부분 합
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            D = new int[N+1];
            psum = new int[N+1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                D[i] = Integer.parseInt(st.nextToken());
                psum[i] = psum[i-1] + D[i];
                psum[i] %= K;
            }

            bw.write((waysToBuy() + " " + maxBuys()) + "\n");
        }
        bw.close();
    }

    private static int maxBuys() {
        int[] prev = new int[K];
        Arrays.fill(prev, -1);

        int[] d = new int[N+1];
        for (int i = 0; i <= N; i++) {
            if (i == 0) {
                d[i] = 0;
            } else {
                d[i] = d[i-1];
            }

            int loc = prev[psum[i]];
            if (loc != -1) {
                d[i] = Math.max(d[i], d[loc] + 1);
            }
            prev[psum[i]] = i;
        }
        return d[N];
    }

    private static long waysToBuy() {
        long[] cnt = new long[K];
        for (int i = 0; i <= N; i++) {
            cnt[psum[i]]++;
        }

        long ret = 0;
        for (int i = 0; i < K; i++) {
            if (cnt[i] >= 2) {
                ret = (ret + (cnt[i] * (cnt[i]-1)) / 2) % mod;
            }
        }
        return ret;
    }
}
