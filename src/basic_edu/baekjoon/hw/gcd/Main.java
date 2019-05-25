package basic_edu.baekjoon.hw.gcd;

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

    private static int A = 40000;
    private static int[] prime = new int[A];
    private static int cnt;
    private static boolean[] isPrime = new boolean[A];
    private static final long MOD = 1000000000L;

    private static int N, M;
    private static int[] primeA = new int[A];
    private static int[] primeB = new int[A];
    private static int[] lastPrimeA = new int[1000];
    private static int[] lastPrimeB = new int[1000];
    private static int lastA_cnt, lastB_cnt;


    // 백준 알고리즘
    // https://www.acmicpc.net/problem/2824
    // 최대공약수(2824): 소인수분해, 소수
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 에라토스테네스의 체
        getPrime();

        // 소수인자 배열 초기화
        for (int i = 0; i < cnt; i++) {
            primeA[i] = 0;
            primeB[i] = 0;
        }

        N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        int n;
        lastA_cnt = 0;
        for (int i = 0; i < N; i++) {
            n = Integer.parseInt(st.nextToken());

            // 소인수 분해
            for (int j = 0; j < cnt && prime[j] <= n; j++) {
                while (n % prime[j] == 0) {
                    primeA[j]++;
                    n /= prime[j];
                }
            }

            if (n > 1) {
                lastPrimeA[lastA_cnt++] = n;
            }
        }

        M = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        int m;
        lastB_cnt = 0;
        for (int i = 0; i < M; i++) {
            m = Integer.parseInt(st.nextToken());

            for (int j = 0; j < cnt && prime[j] <= m; j++) {
                while (m % prime[j] == 0) {
                    primeB[j]++;
                    m /= prime[j];
                }
            }

            if (m > 1) {
                lastPrimeB[lastB_cnt++] = m;
            }
        }

        // 소인수 분해 후 남은 큰 소수들의 데이터를 크기순으로 정렬
        Arrays.sort(lastPrimeA,0,lastA_cnt);
        Arrays.sort(lastPrimeB,0,lastB_cnt);

        long ans = 1;
        int flag = 0;   // 1000000000 보다 크면 1 작으면 0

        // 소인수분해 정보를 이용하여 공통 인자를 찾고 최대공약수 구하기
        for (int i = 0; i < cnt; i++) {
            int cnt = min(primeA[i], primeB[i]);
            while (cnt-- > 0) {
                ans *= (long) prime[i];
                if (ans > MOD) {
                    ans %= MOD;
                    flag = 1;
                }
            }
        }

        // 인수가 큰 소수인 경우 두 수에 모두 포함된 인수만 최대공약수에 곱함
        // 2 point 기법을 활용
        int idxA = 0;
        int idxB = 0;
        while (idxA < lastA_cnt && idxB < lastB_cnt) {
            if (lastPrimeA[idxA] < lastPrimeB[idxB]) {
                idxA++;
            } else if (lastPrimeA[idxA] > lastPrimeB[idxB]) {
                idxB++;
            } else {
                ans *= (long) lastPrimeA[idxA];
                if (ans > MOD) {
                    ans%=MOD;
                    flag=1;
                }
                idxA++;
                idxB++;
            }
        }

        if (flag == 1) {
            System.out.printf("%09d", ans);
        } else {
            System.out.printf("%d", ans);
        }

    }

    private static void getPrime() {
        for (int i = 2; i < A; i++) {
            isPrime[i] = true;
        }

        cnt = 0;
        for (int i = 2; i < A; i++) {
            if (!isPrime[i]) {
                continue;
            }

            prime[cnt++] = i;

            for (int j = i+i; j < A; j += i) {
                if (!isPrime[j]) {
                    continue;
                }
                isPrime[j] = false;
            }
        }
    }

    private static int min(int a, int b) {
        return (a < b) ? a : b;
    }
}
