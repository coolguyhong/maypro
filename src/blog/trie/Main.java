package blog.trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    private static BufferedReader br;
    private static BufferedWriter bw;

    private static int res;

    // 블로그, 백준 알고리즘
    // https://code0xff.tistory.com/76?category=723759
    // https://www.acmicpc.net/problem/5052
    // 트라이(trie) : 문자열 알고리즘
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            Node root = new Node('r');

            for (int i = 0; i < n; i++) {
                String str = br.readLine();
                Node node = root;
                for (int j = 0; j < str.length(); j++) {
                    char c = str.charAt(j);
                    if (node.tree[c - '0'] == null) {
                        node.cnt++;
                        node.tree[c - '0'] = new Node(c);
                    }
                    node = node.tree[c - '0'];
                }
            }

            res = 0;
            searchLeafNode(root);
            if (res == n) {
                bw.write("YES\n");
            } else {
                bw.write("NO\n");
            }
        }
        bw.close();
    }

    private static void searchLeafNode(Node node) {
        if (node.cnt == 0) {
            res++;
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (node.tree[i] == null) {
                continue;
            }
            searchLeafNode(node.tree[i]);
        }
    }
}

class Node {
    char n;
    int cnt;
    Node[] tree = new Node[10];

    Node(char n) {
        this.n = n;
        this.cnt = 0;
    }
}