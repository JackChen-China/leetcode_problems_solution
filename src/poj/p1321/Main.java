package poj.p1321;

import java.util.Scanner;

/**
 * Created by JackChen on 2017/5/16.
 */
public class Main {

    private static int n;
    private static int k;
    private static String[] chessboard;
    private static boolean[] columnFlag;
    private static int answer;
    private static int putedChess;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            n = sc.nextInt();
            k = sc.nextInt();
            if (n == -1 && k == -1) {
                break;
            }
            sc.nextLine();
            chessboard = new String[n];
            columnFlag = new boolean[n];
            answer = 0;
            putedChess = 0;
            for (int i = 0; i < n; i++) {
                chessboard[i] = sc.nextLine();
            }
            DFS(0);
            System.out.println(answer);
        }
    }

    private static void DFS(int deep){//每次本行选一个
        if(putedChess == k){
            answer++;
            return;
        }
        if(deep >= n){
            return;
        }
        if(k-putedChess > n - deep){
            return;
        }
        for(int i = 0; i < chessboard[deep].length(); i++){
            if(('#' == chessboard[deep].charAt(i)) && !columnFlag[i]){
                columnFlag[i] = true;
                putedChess++;
                DFS(deep+1);
                columnFlag[i] = false;
                putedChess--;
            }
        }
        DFS(deep+1);
    }
}

//号称0毫秒的答案，学习一下
//#include <iostream>
//#include <cstdio>
//#include <cstring>
//#include <algorithm>
//using namespace std;
//        #define N 10
//        #define M 1<<9-1
//        int n,m;
//        int dp[N][M][N];
//        char ma[N][N];
//        int dfs(int row,int col,int num)
//        {
//        if(num==m) return 1;
//        if(row>=n) return 0;
//        if(dp[row][col][num]!=-1) return dp[row][col][num];
//        int sum=0;
//        for(int i=0;i<n;i++)
//        {
//        if(ma[row][i]=='#')
//        {
//        if(col&(1<<i)) continue;
//        sum+=dfs(row+1,col|(1<<i),num+1);
//        }
//        }
//        sum+=dfs(row+1,col,num);
//        return dp[row][col][num]=sum;
//        }
//        int main()
//        {
//        while(~scanf("%d %d",&n,&m)&&n!=-1&&m!=-1)
//        {
//        for(int i=0; i<n; i++)
//        scanf("%s",ma[i]);
//        memset(dp,-1,sizeof(dp));
//        printf("%d\n",dfs(0,0,0));
//        }
//        return 0;
//        }
