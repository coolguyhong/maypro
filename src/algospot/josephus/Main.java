package algospot.josephus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {


    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int N, K;
    private static List<Integer> links;

    // 알고스팟
    // https://algospot.com/judge/problem/read/JOSEPHUS
    // 조세푸스 문제(JOSEPHUS): 선형자료구조, 동적배열
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int C = Integer.parseInt(br.readLine());
        while (C-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            links = new ArrayList<>();
            for (int i = 1; i <= N; i++) {
                links.add(i);
            }

            int idx = 0;
            while (links.size() > 2) {
                links.remove(idx);
                idx = (idx + K - 1) % links.size();
            }

            Collections.sort(links);
            for (int c : links) {
                bw.write(c + " ");
            }
            bw.newLine();
        }
        bw.close();
    }
}
