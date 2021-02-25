package method;

public class Solution {


    public static void solution(int num, int[][] square) {
        if (num % 2 == 1) odd_magic (num, square);
        else even_magic (num, square);
        check(num,square);
    }

    //奇数阶魔方阵
    private static void odd_magic(int num, int[][] square) {

        /*
         * 根据奇数阶魔方阵的填写方法，先在第一行中间填写数字1，然后向右上角（超出范围就绕回去）移动
         * 当右上角有数字的时候，就向下移动一格直到将n*n个数全部填写完成
         */


        int x = 0, y = num / 2;
        int cnt = 1;//计数器，用于记录填写到几
        while (cnt <= num * num) {
            square[x][y] = cnt;

            if (cnt % num == 0) {
                x = x + 1;
                if (x >= num) x = 0;
            }//右上角有数字，向下移动
            else {
                //向右上角移动
                x = x - 1;
                y = y + 1;
                if (x < 0) x = num - 1;
                if (y >= num) y = 0;
            }
            cnt++;
        }
    }


    private static void even_magic(int num, int[][] square) {
        if (num % 4 == 0) {
            int cnt = 1;//计数器
            for (int i = 0; i < num; i++)
                for (int j = 0; j < num; j++) {
                    if ((i + j) % 4 == 3 || (i - j) % 4 == 0) square[i][j] = num * num - cnt + 1;
                    else square[i][j] = cnt;
                    cnt++;
                }
        } else {
            int k = num / 4;//k决定需要交换的个数
            int n = num / 2;
            //将整个数组分为ABCD四个区域，四个均为奇数阶的方阵
            int[][] domainA = new int[n][n];
            int[][] domainB = new int[n][n];
            int[][] domainC = new int[n][n];
            int[][] domainD = new int[n][n];
            //按照填写奇数阶方阵的规则，依次填写ADBC方阵
            odd_magic_for_even (n, 1, domainA);
            odd_magic_for_even (n, 1 + n * n, domainD);
            odd_magic_for_even (n, 1 + 2 * n * n, domainB);
            odd_magic_for_even (n, 1 + 3 * n * n, domainC);

            // 在A象限的中间行、中间格开始，按自左向右的方向，标出k格。
            // A象限的其它行则标出最左边的k格。将这些格，和C象限相对位置上的数，互换位置。
            for (int i = 0; i < n; i++) {
                if (i == n / 2) {
                    int count = 0;
                    while (count < k) {
                        int t = domainA[i][n / 2 + count];
                        domainA[i][n / 2 + count] = domainC[i][n / 2 + count];
                        domainC[i][n / 2 + count] = t;
                        count++;
                    }
                } else {
                    int count = 0;
                    while (count < k) {
                        int t = domainA[i][count];
                        domainA[i][count] = domainC[i][count];
                        domainC[i][count] = t;
                        count++;
                    }
                }
            }

            //在B象限任一行的中间格，自右向左，标出k-1列。
            // (注：6阶幻方由于k-1=0，所以不用再作B、D象限的数据交换)，
            // 将B象限标出的这些数，和D象限相对位置上的数进行交换，就形成幻方。

            for (int i = 0; i < n; i++) {
                //遍历每一行
                int count = 0;//计数器
                while (count < k - 1) {
                    int t = domainB[i][n / 2 - count];
                    domainB[i][n / 2 - count] = domainD[i][n / 2 - count];
                    domainD[i][n / 2 - count] = t;
                    count++;
                }

            }


            //将ABCD四个矩阵合并
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    square[i][j] = domainA[i][j];
                    square[i][j + n] = domainB[i][j];
                    square[i + n][j] = domainC[i][j];
                    square[i + n][j + n] = domainD[i][j];
                }
        }
    }


    private static void odd_magic_for_even(int num, int cnt, int[][] square) {
        //方阵阶数为num，从cnt开始填写，用于填写ABCD四个区域
        int x = 0, y = num / 2;
        int cntt = 1;
        while (cntt <= num * num) {
            square[x][y] = cnt;

            if (cntt % num == 0) {
                x = x + 1;
                if (x >= num) x = 0;
            }//右上角有数字，向下移动
            else {
                //向右上角移动
                x = x - 1;
                y = y + 1;
                if (x < 0) x = num - 1;
                if (y >= num) y = 0;
            }
            cnt++;
            cntt++;
        }
    }


    private static void print(int num, int[][] square) {
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                System.out.print (square[i][j] + " ");
            }
            System.out.println ( );
        }


    }


    private static void check(int num, int[][] square) {

        int[] sum_row = new int[num];//每一行的和
        int[] sum_column = new int[num];//每一列的和
        int sum_main_diag = 0, sum_counter_diag = 0;//主对角线的和，副对角线的和
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                sum_row[i] = sum_row[i] + square[i][j];
                sum_column[j] = sum_column[j] + square[i][j];
            }
            sum_main_diag = sum_main_diag + square[i][i];
            sum_counter_diag = sum_counter_diag + square[i][num - i - 1];
        }
        for (int i = 0; i < num; i++) {
            System.out.println ("第" + i + "行的和：" + sum_row[i]);
        }
        for (int j = 0; j < num; j++) {
            System.out.println ("第" + j + "列的和：" + sum_column[j]);
        }
        System.out.println ("主对角线的和：" + sum_main_diag);
        System.out.println ("副对角线的和：" + sum_counter_diag);
    }
}
