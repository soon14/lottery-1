/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HongBaoAlgorithmUtil {

    static Random random = new Random();

    static {
        random.setSeed(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        
        int[] x = {1,2,3,4,5,6,7,8,9};  
        List list = new ArrayList();  
        for(int i = 0;i < x.length;i++){  
            System.out.print(x[i]+", ");  
            list.add(x[i]);  
        }  
        System.out.println();  
          
        Collections.shuffle(list);  
          
        Iterator ite = list.iterator();  
        while(ite.hasNext()){  
            System.out.print(ite.next()+", ");  
        }  
        
       int redCount = 5;
        for (int i=0; i<1000 ; i++) {
            List<Integer> result = HongBaoAlgorithmUtil.generate(1000, redCount, 1000 / redCount * 2, 2);
                System.out.println("-----第"+i+"个红包-------");
            for (Integer long1 : result) {
                 System.out.print( long1+",");
            }
                 System.out.println("");
        }
    }

    /**
     * 生产min和max之间的随机数，但是概率不是平均的，从min到max方向概率逐渐加大。
     * 先平方，然后产生一个平方值范围内的随机数，再开方，这样就产生了一种“膨胀”再“收缩”的效果。
     *
     * @param min
     * @param max
     * @return
     */
    static long xRandom(long min, long max) {
        return sqrt(nextLong(sqr(max - min)));
    }

    /**
     *
     * @param total 红包总额
     * @param count 红包个数
     * @param max 每个小红包的最大额
     * @param min 每个小红包的最小额
     * @return 存放生成的每个小红包的值的数组
     */
    public static List<Integer> generate(long total, int count, long max, long min) {
        List<Integer> resultList = new ArrayList<>();
        resultList = new ArrayList<>();
        long[] result = new long[count];
        long average = total / count;
        //这样的随机数的概率实际改变了，产生大数的可能性要比产生小数的概率要小。
        //这样就实现了大部分红包的值在平均数附近。大红包和小红包比较少。

        for (int i = 0; i < result.length; i++) {
			//因为小红包的数量通常是要比大红包的数量要多的，因为这里的概率要调换过来。
            //当随机数>平均值，则产生小红包
            //当随机数<平均值，则产生大红包
            if (nextLong(min, max) > average) {
                // 在平均线上减钱
//				long temp = min + sqrt(nextLong(range1));
                long temp = min + xRandom(min, average);
                result[i] = temp;
                total -= temp;
            } else {
                // 在平均线上加钱
//				long temp = max - sqrt(nextLong(range2));
                long temp = max - xRandom(average, max);
                result[i] = temp;
                total -= temp;
            }
        }
        // 如果还有余钱，则尝试加到小红包里，如果加不进去，则尝试下一个。
        while (total > 0) {
            for (int i = 0; i < result.length; i++) {
                if (total > 0 && result[i] < max) {
                    result[i]++;
                    total--;
                }
            }
        }
        // 如果钱是负数了，还得从已生成的小红包中抽取回来
        while (total < 0) {
            for (int i = 0; i < result.length; i++) {
                if (total < 0 && result[i] > min) {
                    result[i]--;
                    total++;
                }
            }
        }
         //排序
        Arrays.sort(result);
        int size = result.length;
       // 最大和最小的2个值不能相等
        if (result[0] == result[1]) {
            result[0] = result[0] - 1;
            result[1] = result[1] + 1;
        }
        if (result[size - 1] == result[size - 2]) {
            result[size - 1] = result[size - 1] + 1;
            result[size - 2] = result[size - 2] - 1;
        }
        for (int i = 0; i < result.length; i++) {
            Integer l = Long.valueOf(result[i]).intValue();
            resultList.add(l);
        }
        Collections.shuffle(resultList);  
        return resultList;
    }
    

    static long sqrt(long n) {
        // 改进为查表？
        return (long) Math.sqrt(n);
    }

    static long sqr(long n) {
        // 查表快，还是直接算快？
        return n * n;
    }

    static long nextLong(long n) {
        return random.nextInt((int) n);
    }

    static long nextLong(long min, long max) {
        return random.nextInt((int) (max - min + 1)) + min;
    }
}
