package com.example.demo.leetcode;

import java.util.*;

/**
 * @author SongNuoHui
 * @date 2021/11/30 17:58
 */
public class Q1 {
    /**
     * 并查集, Kruskal算法(最小生成树)
     * 1584. 连接所有点的最小费用
     * 给你一个points数组，表示 2D 平面上的一些点，其中points[i] = [xi, yi]。
     * 连接点[xi, yi] 和点[xj, yj]的费用为它们之间的 曼哈顿距离：|xi - xj| + |yi - yj|，其中|val|表示val的绝对值。
     * 请你返回将所有点连接的最小总费用。只有任意两点之间 有且仅有一条简单路径时，才认为所有点都已连接。
     * 输入：points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
     * 输出：20
     */
    private int[] parent;

    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e.dis));
        int n = points.length;
        parent = new int[n];
        int count = 1;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            for (int j = i + 1; j < n; j++) {
                Edge e = new Edge(points, i, j);
                queue.add(e);
            }
        }
        int res = 0;
        while (!queue.isEmpty()) {
            Edge e = queue.poll();
            int x = e.x;
            int y = e.y;
            int dis = e.dis;
            if (!isUnion(x, y)) {
                union(x, y);
                res += dis;
                count++;
            }
            if (count == n) {
                break;
            }
        }
        return res;
    }

    private int getParent(int i) {
        while (i != parent[i]) {
            int temp = parent[i];
            parent[i] = parent[temp];
            i = parent[i];
        }
        return i;
    }

    private boolean isUnion(int i, int j) {
        int pi = getParent(i);
        int pj = getParent(j);
        return pi == pj;
    }

    private void union(int i, int j) {
        int pi = getParent(i);
        int pj = getParent(j);
        if (pi != pj) {
            parent[pj] = pi;
        }
    }
}

class Edge {
    int x;
    int y;
    int dis;

    Edge(int[][] points, int i, int j) {
        x = i;
        y = j;
        dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }
/*    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        DisjointSetUnion dsu = new DisjointSetUnion(n);
        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(dist(points, i, j), i, j));
            }
        }
        edges.sort(Comparator.comparingInt(edge -> edge.len));
        int ret = 0, num = 1;
        for (Edge edge : edges) {
            int len = edge.len, x = edge.x, y = edge.y;
            if (dsu.unionSet(x, y)) {
                ret += len;
                num++;
                if (num == n) {
                    break;
                }
            }
        }
        return ret;
    }

    //花费：x坐标差+y坐标差
    public int dist(int[][] points, int x, int y) {
        return Math.abs(points[x][0] - points[y][0]) + Math.abs(points[x][1] - points[y][1]);
    }
}

class DisjointSetUnion {
    int[] f;
    int[] rank;
    int n;

    public DisjointSetUnion(int n) {
        this.n = n;
        this.rank = new int[n];
        Arrays.fill(this.rank, 1);
        this.f = new int[n];
        for (int i = 0; i < n; i++) {
            this.f[i] = i;
        }
    }

    //查找
    public int find(int x) {
        return f[x] == x ? x : (f[x] = find(f[x]));
    }

    //合并
    public boolean unionSet(int x, int y) {
        int fx = find(x), fy = find(y);
        if (fx == fy) {
            return false;
        }
        if (rank[fx] < rank[fy]) {
            int temp = fx;
            fx = fy;
            fy = temp;
        }
        rank[fx] += rank[fy];
        f[fy] = fx;
        return true;
    }
}

class Edge {
    int len, x, y;

    public Edge(int len, int x, int y) {
        this.len = len;
        this.x = x;
        this.y = y;
    }*/

    public static void main(String[] args) {
        int a[][] = {{0, 0}, {1, 1}, {2, 2}, {3, 1}};
        Q1 q1 = new Q1();
        System.out.println(q1.minCostConnectPoints(a));
    }
}
