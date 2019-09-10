package algospot.brackets2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    // 알고스팟
    // https://algospot.com/judge/problem/read/BRACKETS2
    // 짝이 맞지 않는 괄호(BRACKETS2): stack
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int C = Integer.parseInt(br.readLine());
        while (C-- > 0) {
            String parentheses = br.readLine();
            int len = parentheses.length();

            boolean flag = true;
            Deque<Character> open = new ArrayDeque<>();
            for (int i = 0; i < len; i++) {
                char c = parentheses.charAt(i);
                if (c == '(' || c == '[' || c == '{') {
                    open.add(c);
                } else {
                    if (open.isEmpty()) {
                        flag = false;
                        break;
                    }

                    if (open.peekLast() == '(') {
                        if (c != ')') {
                            flag = false;
                            break;
                        }
                    } else if (open.peekLast() == '[') {
                        if (c != ']') {
                            flag = false;
                            break;
                        }
                    } else if (open.peekLast() == '{') {
                        if (c != '}') {
                            flag = false;
                            break;
                        }
                    }

                    open.pollLast();
                }
            }

            if (flag && open.isEmpty()) {
                bw.write("YES\n");
            } else {
                bw.write("NO\n");
            }
        }
        bw.close();
    }
}
