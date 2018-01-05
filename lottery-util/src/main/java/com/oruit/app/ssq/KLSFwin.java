package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/22.
 */

/**
 * @author @wyt
 * @create 2017-09-22 11:32
 */
public class KLSFwin {
    private static final Integer two = 8;
    private static final Integer tree = 24;
    private static final Integer four = 80;
    private static final Integer five = 320;

    public static void main(String[] args) {
        String str = "01020304";//
        String source = "01 02 03 04";
        /*int result = getWinMoney(str, source);
        System.out.println("result:"+result);*/

    }

    /**
     * 快乐十分的中奖金额
     * @param str 中奖号码
     * @param source 购买的号码
     * @param type 类型
     * @return
     */
    private static int getWinMoney(String str, String source ,String type) {

        int j = 0 ;
        String s = "";
        for (int i = 0 ;i<str.length();i++){
            if(j<2){
                j++;
                s = s +str.substring(i,i+1);
                if(j == 2){
                    s =s + " ";
                }
            }else{
                j = 0;
                i--;
            }
        }
        s = s.substring(0,s.length()-1);
        String[] split = s.split(" ");
        int result = 0 ;
        for (int i = 0 ; i < split.length ; i ++){
            if(source.indexOf(split[i]) >= 0) {
                result ++ ;
            }
        }
       for(int i = 2 ; i < 6 ; i++){
           int i1 = Integer.parseInt(type);
           if(i == i1 && i == result){
                if(result<i1){
                    return 0;
                }else{
                    if(i1==2){
                        return  two;
                    }
                    if(i1==3){
                        return  tree;
                    }
                    if(i1==4){
                        return  four;
                    }
                    if(i1==5){
                        return  five;
                    }
                }
           }
           if(i == Integer.parseInt(type) && result == split.length){

           }else if(i == Integer.parseInt(type) && result > split.length){

           }else{
           }
       }
        return result;
    }
}
