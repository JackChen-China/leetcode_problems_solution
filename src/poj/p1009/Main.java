package poj.p1009;

import java.util.Scanner;
import java.util.Arrays;
/**
 * Created by JackChen on 2017/4/11.
 */
//这种方法碰上下面数据会失效
//1000000000
//4 1000000000
//0 0
public class Main {

        static boolean needAccelerate = true;
        static int n = 7;
        //	static int[][] data= new int[2][n];//15 4 4个15
        static int[][] data= {{15, 100, 25, 175, 25, 175, 25}, {4, 15, 2, 2, 5, 2, 5}};
        static short[][] dealingData = new short[3][n];
        static int m = 0;

        static int currentIndex = 0;
        static int currentCount = 0;//最后放入dealingData中的数字的个数
        public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()){
                n = scanner.nextInt();
                System.out.println(n);
                if(n == 0){
                    break;
                }
                data = new int[2][10000];
                m = 0;
                currentIndex = 0;
                currentCount = 0;

                int index = 0;
                while(scanner.hasNext()){
                    data[0][index] = scanner.nextInt();
                    data[1][index] = scanner.nextInt();
                    index++;
                    if(data[0][index-1] == 0 && data[1][index-1] == 0){
                        break;
                    }
                }
                dealingData = new short[3][n];
                int mn = 0;
                for(int i = 0; i < index; i++){
                    mn+=data[1][i];
                }
                m = mn/n;
                int value = -1;
                int count = 0;
                for(int i = 0; i< m; i++){
                    boolean needSkip = false;
                    if(needAccelerate) {
                        if(value == 0 && count >= n){
                            needSkip = true;
                            int re = generateDealingData(i, needSkip);
                            count += re;
                            i+=re/n;
                        }else{
                            generateDealingData(i, needSkip);
                        }
                    } else {
                        generateDealingData(i, needSkip);
                    }
                    for(int j = 0; j < n; j++){
                        int t = findMin(j);
                        if(t == value){
                            count++;
                        }else{
                            if(value != -1){
                                System.out.println(value + " " + count);
                            }
                            value = t;
                            count = 1;
                        }
                    }
                }
                System.out.println(value + " " + count);
                System.out.println("0 0");
            }
            scanner.close();
        }

        private static int findMin(int j){
            int[] minisValue = new int[9];
            if(j % n != 0){//不是左侧
                minisValue[0] = Math.abs(dealingData[1][j] - dealingData[0][j-1]);
                minisValue[3] = Math.abs(dealingData[1][j] - dealingData[1][j-1]);
                minisValue[6] = Math.abs(dealingData[1][j] - dealingData[2][j-1]);
            }
            if(j % n != (n - 1)){//bushi8右侧

                minisValue[2] = Math.abs(dealingData[1][j] - dealingData[0][j+1]);
                minisValue[5] = Math.abs(dealingData[1][j] - dealingData[1][j+1]);
                minisValue[8] = Math.abs(dealingData[1][j] - dealingData[2][j+1]);
            }
            minisValue[1] = Math.abs(dealingData[1][j] - dealingData[0][j]);
            minisValue[7] = Math.abs(dealingData[1][j] - dealingData[2][j]);
            Arrays.sort(minisValue);
            return minisValue[8];
        }

        private static int generateDealingData(int i, boolean needSkip){
            int reSkipCount = 0;
            if(i < m){
                if(needSkip){//加速模块，保证前后至少1整行min为0
                    int d_0 = data[0][currentIndex];//value
                    int d_1 = data[1][currentIndex];//total count
                    if(currentCount + n < d_1){
                        reSkipCount = ((d_1 - (currentCount - n))/n - 1)*n;//保证后续至少一整行data[0][currentIndex]
                        if(reSkipCount > 0){
                            currentCount += reSkipCount;

                            System.arraycopy(dealingData[2],0, dealingData[1], 0, n);//跳转后，末尾航一定是全data[0][currentIndex]，与进入加速模块的dealingData[2]相同，
                            i += reSkipCount/n;//因为下面对末尾行有特殊处理，所以提前补偿当前处理行指针，因为java是传值，因此外界还要再处理一次
                        }else{
                            reSkipCount = 0;
                        }
                    }

                }
                dealingData[0] = dealingData[1];
                dealingData[1] = new short[n];
                if(i == 0){
                    fill(dealingData[1]);
                    dealingData[0] = dealingData[1];
                }else{
                    dealingData[1] = dealingData[2];
                    dealingData[2] = new short[n];
                }
                if(i == m-1){
                    dealingData[2] = dealingData[1];
                }else{
                    fill(dealingData[2]);
                }
            }
            return reSkipCount;
        }

        private static void fill(short[] row){
            short d_0 = (short)data[0][currentIndex];//value
            int d_1 = data[1][currentIndex];//total count
            for(int i = 0; i < n; i++){
                if(currentCount < d_1){
                }else{
                    currentIndex++;
                    currentCount = 0;
                    d_0 = (short)data[0][currentIndex];//value
                    d_1 = data[1][currentIndex];//total count
                }
                row[i] = d_0;
                currentCount++;
            }
        }

}

//
//import java.util.Arrays;
//
//
//public class Main {
//
//	static int n = 7;
////	static int[][] data= new int[2][n];//15 4 4个15
//	static int[][] data= {{15, 100, 25, 175, 25, 175, 25}, {4, 15, 2, 2, 5, 2, 5}};
//	static int[][] dealingData = new int[3][n];
//	static int m = 0;
//
//	static int currentIndex = 0;
//	static int currentCount = 0;
//	public static void main(String[] args){
//		System.out.println(n);
//		int mn = 0;
//		for(int i = 0; i < data[1].length; i++){
//			mn+=data[1][i];
//		}
//		m = mn/n;
//		int value = -1;
//		int count = 0;
//		for(int i = 0; i< m; i++){
//			generateDealingData(i);
//			for(int j = 0; j < n; j++){
//				int t = findMin(j);
//					if(t == value){
//						count++;
//					}else{
//						if(value != -1){
//							System.out.println(value + " " + count);
//						}
//						value = t;
//						count = 1;
//					}
//			}
//		}
//		System.out.println(value + " " + count);
//		System.out.println("0 0");
//	}
//
//	private static int findMin(int j){
//		int[] minisValue = new int[9];
//		if(j % n != 0){//不是左侧
//			minisValue[0] = Math.abs(dealingData[1][j] - dealingData[0][j-1]);
//			minisValue[3] = Math.abs(dealingData[1][j] - dealingData[1][j-1]);
//			minisValue[6] = Math.abs(dealingData[1][j] - dealingData[2][j-1]);
//		}
//		if(j % n != (n - 1)){//bushi8右侧
//
//			minisValue[2] = Math.abs(dealingData[1][j] - dealingData[0][j+1]);
//			minisValue[5] = Math.abs(dealingData[1][j] - dealingData[1][j+1]);
//			minisValue[8] = Math.abs(dealingData[1][j] - dealingData[2][j+1]);
//		}
//		minisValue[1] = Math.abs(dealingData[1][j] - dealingData[0][j]);
//		minisValue[7] = Math.abs(dealingData[1][j] - dealingData[2][j]);
//		Arrays.sort(minisValue);
//		return minisValue[8];
//	}
//
//	private static void generateDealingData(int i){
//		if(i < m){
//			dealingData[0] = dealingData[1];
//			dealingData[1] = new int[n];
//			if(i == 0){
//				fill(dealingData[1]);
//				dealingData[0] = dealingData[1];
//			}else{
//				dealingData[1] = dealingData[2];
//				dealingData[2] = new int[n];
//			}
//			if(i == m-1){
//				dealingData[2] = dealingData[1];
//			}else{
//				fill(dealingData[2]);
//			}
//		}
////		System.out.println(dealingData);
//	}
//
//	private static void fill(int[] row){
//		int d_0 = data[0][currentIndex];//value
//		int d_1 = data[1][currentIndex];//total count
//		for(int i = 0; i < n; i++){
//			if(currentCount < d_1){
//			}else{
//				currentIndex++;
//				currentCount = 0;
//				d_0 = data[0][currentIndex];//value
//				d_1 = data[1][currentIndex];//total count
//			}
//			row[i] = d_0;
//			currentCount++;
//		}
////		System.out.println("debug:" + row);
//	}
//}


//测试数据
//3
//255 1
//10 1
//255 2
//10 1
//255 2
//10 1
//255 1
//0 0
//10
//35 500000000
//200 500000000
//0 0
//7
//15 4
//100 15
//25 2
//175 2
//25 5
//175 2
//25 5
//0 0
//8
//4 8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//0 0
//8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//0 0
//8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//0 0
//5
//15 12
//100 3
//15 1
//100 4
//15 2
//100 3
//0 0
//30
//10 41
//20 41
//15 41
//30 41
//25 41
//0 5
//0 0
//1
//255 2
//0 0
//10
//4 20
//6 20
//0 0
//1
//4 1000000000
//0 0
//1000000000
//4 1000000000
//0 0
//30
//10 41
//20 41
//15 41
//30 41
//25 41
//0 5
//0 0
//8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//0 0
//8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//4 8
//0 0
//8
//4 8
//1 1
//2 1
//4 1
//8 1
//6 1
//4 1
//2 1
//0 1
//5 8
//0 0
//5
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//30 11
//20 11
//10 11
//20 11
//0 0
//0