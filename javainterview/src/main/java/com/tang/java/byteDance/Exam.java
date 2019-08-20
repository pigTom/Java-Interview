package com.tang.java.byteDance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Exam {
    public static void mainq(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }
    }

    public static void  main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int ans = 0, x;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                x = sc.nextInt();
                ans += x;
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args){

        // list 中是多维数组
        int[][] arrs = {{1, 1, 1}, {1, 2, 1}, {1, 1, 1}};
        int i = 0;


        Set<String> changed = new HashSet<>();
        //
        int minute = 0;
        int j = 0;
        int manageNum = 0;
        for (i = 0; i < arrs.length; i++) {
            int[] ss = arrs[i];
            for (j = 0; j < ss.length; j++) {
                int s = ss[j];
                // 被修改过
                if (changed.contains(j + "" + i)){
                    continue;
                }
                if (s == 2) {
                    add(arrs, changed, i, j+1, arrs.length);
                    if (add(arrs,changed, i, j -1, arrs.length))
                        manageNum--;
                    add(arrs,changed, i + 1, j, arrs.length);
                    if (add(arrs,changed, i - 1, j, arrs.length))
                        manageNum--;

                }else
                    manageNum++;
            }
        }
        if (!changed.isEmpty()) {
            minute ++;
        }
        while (manageNum > 0 && !changed.isEmpty()) {
            Iterator<String> iterable = changed.iterator();
            Set<String> temp = new HashSet<>();
            while (iterable.hasNext()) {
                String key = iterable.next();
                iterable.remove();

                char ic = key.charAt(0);
                char jc = key.charAt(1);
                i = ic - '0';
                j = jc - '0';
                if (add(arrs, temp, i, j+1, arrs.length))
                    manageNum--;
                if (add(arrs,temp, i, j -1, arrs.length)){
                    manageNum --;
                }
                if(add(arrs,temp, i + 1, j, arrs.length))
                    manageNum--;
                if (add(arrs,temp, i - 1, j, arrs.length)) {
                    manageNum --;
                }
            }
            changed = temp;
            minute++;
        }
        if (manageNum > 0) {
            System.out.println(-1);
        } else{
            System.out.println(minute);
        }
    }

    public static boolean add (int[][] va, Set<String> changed, int row, int col, int rowLenth) {
        if (row < 0 || row > rowLenth -1 || col < 0 || col > va[row].length -1) {
            return false;
        }

        if (va[row][col] ==  1) {
            va[row][col] = 2;
            changed.add(row+""+col);
            return true;
        }
        return false;
    }
}
