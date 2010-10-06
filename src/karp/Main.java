package karp;

import java.io.*;
import java.util.*;

public class Main {

    public static void dijkstra(int graph[][],int s, int t) {
        
        int d[] = new int[graph.length];        
        int prev[] = new int[graph.length];
        boolean flags[] = new boolean[graph.length];

        Arrays.fill(d, Integer.MAX_VALUE);
        Arrays.fill(flags, true);
        Arrays.fill(prev, -1);

        d[s] = 0;

        int i = 0, min = Integer.MAX_VALUE, pos = 0;
        while (i < graph.length) {
            //minimum
            for (int j = 0; j < flags.length; j++) {
                if (min > d[j] && flags[j] != false) {
                    min = d[j];
                    pos = j;
                }
            }
            flags[pos] = false;

            //relax
            for (int j = 0; j < graph.length; j++) {
                if (d[j] > graph[pos][j] + d[pos]) {
                    d[j] = graph[pos][j] + d[pos];
                    prev[j] = pos;
                }
            }
            i++;
            min = Integer.MAX_VALUE;
        }

        Stack invpath = new Stack();

        System.out.println();
        System.out.println("1 2 3 4 5 6");
        for (int j = 0; j < prev.length; j++) System.out.print(prev[j] + " ");
        System.out.println();
        path(prev, t, invpath);
        System.out.println();
        for (int j = 0; j < d.length; j++) System.out.print(d[j] + " ");
        int[] path = new int[invpath.size() + 1];
        path[path.length - 1] = t;
        for (int j = 0; j < path.length - 1; j++) path[j] = (Integer) invpath.pop();
        System.out.print("\n----------------------\nPath: ");
        for (int j = 0; j < path.length; j++) System.out.print(path[j] + " -> ");
        System.out.println("= " + d[t]);
    }


    public static void path(int[] prev, int to, Stack invpath) {
        if (prev[to] != -1) {
            invpath.push(prev[to]);
            path(prev, prev[to], invpath);
        }
    }



  public static int[][] fileRead() {

    int[][] grapho = null;
    int length = 0;

    try {
            Scanner sc = new Scanner (new File("666.txt"));

            length = sc.nextInt();
            System.out.println("Размерность: " + length);
            grapho = new int [length][length];
            for(int i=0;i<length;i++){
                for(int j=0;j<length;j++){
                    grapho[i][j] = sc.nextInt();
                    }
            }
            sc.close();
       }
       catch (Exception e) {
           System.out.println("Ошибка: " + e);
       }

        for(int i=0;i<length;i++){
            for(int j=0;j<length;j++){
                System.out.print(grapho[i][j] + " ");
                }
            System.out.println();
            }    
    return grapho;
    }

  public static void main(String[] args) {
      int[][] turbik = fileRead();
      int s = 2;
      int t = 5;
      dijkstra (turbik, s, t);
  }
}