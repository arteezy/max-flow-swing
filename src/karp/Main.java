package karp;

import java.io.*;
import java.util.*;

public class Main {

    public static void dijkstra(int graph[][]) {
        int d[] = new int[graph.length];
        int dC[] = new int[graph.length];
        int p[] = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            d[i] = 100;
            dC[i] = 100;
            p[i] = -1;
        }
        d[0] = 0;
        dC[0] = 0;

        int i = 0, min = 200, pos = 0; //You can change the min to 1000 to make it the largest number
        while (i < graph.length) {
            //extract minimum
            for (int j = 0; j < dC.length; j++) {
                if (min > d[j] && dC[j] != -1) {
                    min = d[j];
                    pos = j;
                }
            }
            dC[pos] = -1;

            //relax
            for (int j = 0; j < graph.length; j++) {
                if (d[j] > graph[pos][j] + d[pos]) {
                    d[j] = graph[pos][j] + d[pos];
                    p[j] = pos;
                }
            }
            i++;
            min = 200;
        }

        System.out.println();
        System.out.println("1 2 3 4 5 6");
        for (int j = 0; j < p.length; j++) System.out.print(p[j] + " ");
        System.out.println();
        path(p, 2);
        System.out.println();
        for (int j = 0; j < d.length; j++) System.out.print(d[j] + " ");
        System.out.println();
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
      System.out.println(turbik.length);
      int s = 0;
      int t = 2;
      dijkstra (turbik);

  }
}