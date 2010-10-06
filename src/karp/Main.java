package karp;

import java.io.*;
import java.util.*;

public class Main {

    public static void dijkstra(int graph[][]) {
        
        int d[] = new int[graph.length];        
        int p[] = new int[graph.length];
        boolean flags[] = new boolean[graph.length];

        Arrays.fill(d, Integer.MAX_VALUE);
        Arrays.fill(flags, true);
        Arrays.fill(p, -1);

        d[0] = 0; 

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
                    p[j] = pos;
                }
            }
            i++;
            min = Integer.MAX_VALUE;
        }

        System.out.println();
        System.out.println("1 2 3 4 5 6");
        for (int j = 0; j < p.length; j++) System.out.print(p[j] + " ");
        System.out.println();
        path(p, 2);
        System.out.println();
        for (int j = 0; j < d.length; j++) System.out.print(d[j] + " ");
        System.out.println("--------------------");
    }


    public static void path(int[] p, int to) {
        if (p[to] != -1) {
            System.out.print(p[to] + " ");
            path(p, p[to]);
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
      int s = 0;
      int t = 2;
      dijkstra (turbik);

  }
}