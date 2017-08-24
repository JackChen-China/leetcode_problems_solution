package poj.p1010;

import java.util.Scanner;

/**
 * Created by JackChen on 2017/4/16.
 */
public class Main {

    static int MAX_STAMP_COUNT = 4;

    static int[] stamps = new int[150];//邮局提供的各种邮票
    static int stampNum = 0;//邮局提供的邮票种数
    static int customerNeed = -1;

    static int re_schemeCount = -1;//方案数
    static int[] re_scheme = new int[MAX_STAMP_COUNT];//当前最佳方案
    static int re_scheme_stampType = 0;//方案中邮票的种数
    static int re_scheme_stampCount = 0;//方案中邮票的数量
    static int re_scheme_maxStamp = -1;//方案中最大的邮票

    static int[] n_scheme = new int[MAX_STAMP_COUNT];//当前最佳方案
    static int n_scheme_stampType = 0;//方案中邮票的种数
    static int n_scheme_stampCount = 0;//方案中邮票的数量
    static int n_scheme_maxStamp = -1;//方案中最大的邮票
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            stampNum = 0;
            stamps = new int[100];
            customerNeed = -1;
            while(true){
                stamps[++stampNum] = scanner.nextInt();
                if(stamps[stampNum] == 0){
                    stampNum--;
                    break;
                }
            }
            while(true){
                if(scanner.hasNext()) {
                    customerNeed = scanner.nextInt();
                    if(customerNeed == 0){//这次发放的邮票，没有下一个顾客了。
                        break;
                    }else{
                        saleStamp();
                    }
                }else{
                    break;
                }
            }
        }
    }
    private static void saleStamp(){
        re_schemeCount = -1;//方案数
        re_scheme = new int[MAX_STAMP_COUNT];//当前最佳方案
        re_scheme_stampType = 0;//方案中邮票的种数
        re_scheme_stampCount = 0;//方案中邮票的数量
        re_scheme_maxStamp = -1;//方案中最大的邮票

        n_scheme = new int[MAX_STAMP_COUNT];//当前最佳方案
        n_scheme_stampType = 0;//方案中邮票的种数
        n_scheme_stampCount = 0;//方案中邮票的数量
        n_scheme_maxStamp = -1;//方案中最大的邮票

        dfsScheme(0,0,0);


        printResult();
    }

    private static void dfsScheme(int deep, int sum,int currentStampIndex){
        if(deep>=4){
            if(sum != customerNeed){
                return;
            }
            if(n_scheme_stampType > re_scheme_stampType){
                changeScheme();
            }else if(n_scheme_stampType == re_scheme_stampType){
                if(n_scheme_stampCount < re_scheme_stampCount){
                    changeScheme();
                }else if(n_scheme_stampCount == re_scheme_stampCount){
                    if(n_scheme_maxStamp > re_scheme_maxStamp){
                        changeScheme();
                    }else if(n_scheme_maxStamp == re_scheme_maxStamp){
                        re_schemeCount++;
                    }
                }
            }
            return;
        }
        for(int i = currentStampIndex; i <= stampNum; i++){
            if(i == 0){

            }else{
                n_scheme_stampCount++;
            }
            if(i == currentStampIndex){

            }else{
                n_scheme_stampType++;
            }
            int restoreMaxStamp = 0;
            n_scheme[deep] = stamps[i];
            if(stamps[i] > n_scheme_maxStamp){
                restoreMaxStamp = n_scheme_maxStamp;
                n_scheme_maxStamp = stamps[i];

            }
            dfsScheme(deep+1,sum+stamps[i],i);

            if(i == 0){

            }else{
                n_scheme_stampCount--;
            }
            if(i == currentStampIndex){

            }else{
                n_scheme_stampType--;
            }
            n_scheme_maxStamp = restoreMaxStamp;
        }
    }

    private static void changeScheme(){
        re_scheme_stampCount = n_scheme_stampCount;
        System.arraycopy(n_scheme, 0, re_scheme, 0, re_scheme.length);
        re_schemeCount = 1;
        re_scheme_stampType = n_scheme_stampType;
        re_scheme_maxStamp = n_scheme_maxStamp;
    }

    private static void printResult(){
        System.out.print(customerNeed);
        if(re_schemeCount == 0 || re_schemeCount == -1){
            System.out.println(" ---- none");
        }else{
            if(re_schemeCount == 1){
                System.out.print(" (" + re_scheme_stampType + "):");
                for(int i = 0; i < MAX_STAMP_COUNT; i++){
                    if(re_scheme[i] == 0){
                        continue;
                    }
                    System.out.print(" " + re_scheme[i]);
                }
                System.out.println();
            }else if(re_schemeCount > 1){
                System.out.println(" (" + re_scheme_stampType + "): tie");
            }
        }
    }

    private boolean ok(int stamp){
        int sum=0;
        for(int i=1; i<=stampNum; i++)
            if(stamps[i]==stamp) sum++;
        if(sum>=4)
            return false;
        else
            return true;
    }
}


//runtimeError 之前没有ac是因为1.re_stamps数组越界，因为n_scheme[currentStampIndex] = stamps[i];  当stamp数量超过4才会暴露；2.非法答案，没有return

////下面的可以ac
//import java.util.*;
//public class Main
//{
//    public static void main(String[] args)
//    {
//        Scanner in=new Scanner(System.in);
//        while(in.hasNext())
//        {
//            int[] arr=new int[100];
//            int n=0;
//            while(true)
//            {
//                int a=in.nextInt();
//                if(a==0)break;
//                arr[n++]=a;
//            }
//            Arrays.sort(arr,0,n);
//            while(true)
//            {
//                int a=in.nextInt();
//                if(a==0)break;
//                int ser=0,num=0,max=0,a1=0,a2=0,a3=0,a4=0;
//                boolean tie=false;
//                for(int i=0;i< n;i++)
//                {
//                    if(arr[i]>a)break;
//                    if(arr[i]==a)
//                    {
//                        if(ser==0)
//                        {
//                            ser=1;num=1;max=i;a1=i;tie=false;
//                        }
//                        else if(ser==1&&num>1)
//                        {
//                            ser=1;num=1;max=i;a1=i;tie=false;
//                        }
//                    }
//                    for(int j=i;j< n;j++)
//                    {
//                        if(arr[i]+arr[j]>a)break;
//                        if(arr[i]+arr[j]==a)
//                        {
//                            int ser1=1;
//                            if(i!=j)ser1++;
//                            if(ser1>ser)
//                            {
//                                ser=ser1;max=j;num=2;a1=i;a2=j;tie=false;
//                            }
//                            else if(ser1==ser)
//                            {
//                                if(num>2||(num==2&&arr[max]< arr[j]))
//                                {
//                                    ser=ser1;max=j;num=2;a1=i;a2=j;tie=false;
//                                }
//                                else if(num==2&&arr[max]==arr[j])
//                                    tie=true;
//                            }
//                        }
//                        for(int k=j;k< n;k++)
//                        {
//                            if(arr[i]+arr[j]+arr[k]>a)break;
//                            if(arr[i]+arr[j]+arr[k]==a)
//                            {
//                                int ser1=1;
//                                if(i!=j)ser1++;if(j!=k)ser1++;
//                                if(ser1>ser)
//                                {
//                                    ser=ser1;max=k;num=3;a1=i;a2=j;a3=k;tie=false;
//                                }
//                                else if(ser1==ser)
//                                {
//                                    if(num>3||(num==3&&arr[max]< arr[k]))
//                                    {
//                                        ser=ser1;max=k;num=3;a1=i;a2=j;a3=k;tie=false;
//                                    }
//                                    else if(num==3&&arr[max]==arr[k])tie=true;
//                                }
//                            }
//                            for(int w=k;w< n;w++)
//                            {
//                                if(arr[i]+arr[j]+arr[k]+arr[w]>a)break;
//                                if(arr[i]+arr[j]+arr[k]+arr[w]==a)
//                                {
//                                    int ser1=1;
//                                    if(i!=j)ser1++;if(j!=k)ser1++;if(k!=w)ser1++;
//                                    if(ser1>ser)
//                                    {
//                                        ser=ser1;max=w;num=4;
//                                        a1=i;a2=j;a3=k;a4=w;
//                                        tie=false;
//                                    }
//                                    else if(ser1==ser&&num==4)
//                                    {
//                                        if(arr[w]>arr[max])
//                                        {
//                                            max=w;
//                                            a1=i;a2=j;a3=k;a4=w;
//                                            tie=false;
//                                        }
//                                        else if(arr[w]==arr[max])
//                                            tie=true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                if(num==0)System.out.println(a+" ---- none");
//                else if(tie)System.out.println(a+" ("+ser+"):"+" tie");
//                else {
//                    System.out.print(a+" ("+ser+"):");
//                    if(num==1)System.out.println(" "+arr[a1]);
//                    else if(num==2)System.out.println(" "+arr[a1]+" "+arr[a2]);
//                    else if(num==3)System.out.println(" "+arr[a1]+" "+arr[a2]+" "+arr[a3]);
//                    else if(num==4)System.out.println(" "+arr[a1]+" "+arr[a2]+" "+arr[a3]+" "+arr[a4]);
//                }
//            }
//        }
//    }
//}