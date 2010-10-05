package karp;

import java.io.*;
import java.util.*;

public class Main {

//  public static Path getPath(Edge[] p, int s, int t) {
//    Path path = new Path();
//    path.f = Integer.MAX_VALUE;
//    for (; t != s; t = p[t].s) {
//      path.edges.add(p[t]);
//      path.f = Math.min(path.f, p[t].cap - p[t].f);
//      System.out.println(s + " " + t + " " + path.f);
//    }
//    return path;
//  }

  public static int[][] fileRead() {

    int[][] grapho = null;
    int length = 0;

       // Считывание файла
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

  }
}