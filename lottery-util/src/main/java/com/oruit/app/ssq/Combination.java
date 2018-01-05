package com.oruit.app.ssq;
//组合算法   
//本程序的思路是开一个数组，其下标表示1到m个数，数组元素的值为1表示其下标   
//代表的数被选中，为0则没选中。   
//首先初始化，将数组前n个元素置1，表示第一个组合为前n个数。   
//然后从左到右扫描数组元素值的"10"组合，找到第一个"10"组合后将其变为   
//"01"组合，同时将其(在本算法中具体指i的位置)左边的所有"1"全部移动到数组的最左端。   
//当第一个"1"移动到数组的m-n的位置，即n个"1"全部移动到最右端时，就得   
//到了最后一个组合。   
//例如求5中选3的组合：   
//1 1 1 0 0 //1,2,3   
//1 1 0 1 0 //1,2,4   
//1 0 1 1 0 //1,3,4   
//0 1 1 1 0 //2,3,4   
//1 1 0 0 1 //1,2,5   
//1 0 1 0 1 //1,3,5   
//0 1 1 0 1 //2,3,5   
//1 0 0 1 1 //1,4,5   
//0 1 0 1 1 //2,4,5   
//0 0 1 1 1 //3,4,5   
import java.util.ArrayList;
import java.util.List;

/**  
* java 代码实现组合的算法  
* 从n个数里取出m个数的组合是n*(n-1)*...*(n-m+1)/m*(m-1)*...2*1   
* @author dubensong  
*  
*/
public class Combination {
  /**  
   * @param a:组合数组  
   * @param m:生成组合长度  
   * @return :所有可能的组合数组列表  
   */
  private static List<String[]> combination(String[] a, int m) {
      Combination c = new Combination();
      List<String[]> list = new ArrayList<String[]>();
      int n = a.length;
      boolean end = false; // 是否是最后一种组合的标记   
      // 生成辅助数组。首先初始化，将数组前n个元素置1，表示第一个组合为前n个数。   
      int[] tempNum = new int[n];
      for (int i = 0; i < n; i++) {
          if (i < m) {
              tempNum[i] = 1;

          } else {
              tempNum[i] = 0;
          }
      }
      printVir(tempNum);// 打印首个辅助数组   
      list.add(c.createResult(a, tempNum, m));// 打印第一种默认组合   
      int k = 0;//标记位   
      while (!end) {
          boolean findFirst = false;
          boolean swap = false;
          // 然后从左到右扫描数组元素值的"10"组合，找到第一个"10"组合后将其变为"01"   
          for (int i = 0; i < n; i++) {
              int l = 0;
              if (!findFirst && tempNum[i] == 1) {
                  k = i;
                  findFirst = true;
              }
              if (tempNum[i] == 1 && tempNum[i + 1] == 0) {
                  tempNum[i] = 0;
                  tempNum[i + 1] = 1;
                  swap = true;
                  for (l = 0; l < i - k; l++) { // 同时将其左边的所有"1"全部移动到数组的最左端。   
                      tempNum[l] = tempNum[k + l];
                  }
                  for (l = i - k; l < i; l++) {
                      tempNum[l] = 0;
                  }
                  if (k == i && i + 1 == n - m) {//假如第一个"1"刚刚移动到第n-m+1个位置,则终止整个寻找   
                      end = true;
                  }
              }
              if (swap) {
                  break;
              }
          }
          printVir(tempNum);// 打印辅助数组   
          list.add(c.createResult(a, tempNum, m));// 添加下一种默认组合   
      }
      return list;
  }

  // 根据辅助数组和原始数组生成结果数组   
  public String[] createResult(String[] a, int[] temp, int m) {
      String[] result = new String[m];
      int j = 0;
      for (int i = 0; i < a.length; i++) {
          if (temp[i] == 1) {
              result[j] = a[i];
              System.out.println("result[" + j + "]:" + result[j]);
              j++;
          }
      }
      return result;
  }

  // 打印整组数组   
  public void print(List<String[]> list) {
      System.out.println("具体组合结果为:");
      for (int i = 0; i < list.size(); i++) {
          String[] temp = (String[]) list.get(i);
          for (int j = 0; j < temp.length; j++) {
              //java.text.DecimalFormat df = new java.text.DecimalFormat("00");//将输出格式化   
              //System.out.print(df.format(temp[j]) + " ");
        	  System.out.print(temp[j]+" ");
          }
          System.out.println();
      }
  }

  // 打印辅助数组的方法   
  public static void printVir(int[] tempNum) {
      System.out.println("生成的辅助数组为：");
      for (int i = 0; i < tempNum.length; i++) {
          System.out.print(tempNum[i]);
      }
      System.out.println();
  }

  public static void main(String[] args) {
      String[] a = { "01", "02","03","04","05","06","07"}; // 整数数组
      int m = 6; // 待取出组合的个数   
      Combination c = new Combination();
      List<String[]> list = c.combination(a, m);
      c.print(list);
      System.out.println("一共" + list.size() + "组!");
  }
  
  
  public static List<String[]> createCaiPiao(String[] arr){
	  
	  int m = 6;//双色球组合六个红球
	  List<String[]> list = combination(arr, m);
	  return list;
	  
  }
  
}
