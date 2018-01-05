package com.oruit.app.threeD;/**
 * Created by wyt on 2017-10-30.
 */

/**
 * @author @wyt
 * @create 2017-10-30 16:13
 */
        /*zx 		28	直选标准 31040
        zxHz	31	直选和值 21040
        zhuxuan 29001组三标准 3346
        zs 29 组三包号 1346
        zsHz 32 组三和值 2346
        zl 30 组六包号 1173
        zlHz 33 组六和值 2173
        zlDt 38 组六胆拖 22:1-2#1:1-*173 // 关联行号-关联行号:最少个数-最多个数  # 关联行号-关联行号:最少个数-最多个数
        glzx 	28	直选标准 01040
        glzhux 	40	组选单式 01040
        zxDt 29002 直选胆拖 22:1-2#1:1-*1040 // 关联行号-关联行号:最少个数-最多个数  # 关联行号-关联行号:最少个数-最多个数
        zxKd 29003 直选跨度 11040
        zxBh 29004 直选包号 31040
        1d 29008 1D 310
        2d 29009 2D 3104
        c1d 29010 猜1D 1230
        c2d 29011 猜2D 237
        tx 29012 通选 3470
        hs 29013 和数 11040
        bx3 29014 包选三 3693
        bx6 29015 包选六 3606
        dx 29016 大小 16
        jiou 29017 奇偶 18
        c3t 29018 猜三同 1104
        tlj 29019 拖拉机 365
        zhixdsUpload 29006 直选单式上传 1
        zuxdsUpload 29007 组选单式上传 1*/
public class D3ZhuShu {


    public static int getZhuShu(String playType,String[][] m){
        if("zx".equals(playType) || "glzx".equals(playType) || "tx".equals(playType) ){
            int count = 1;
            for(int i = 0; i< m.length; i++){
                int row = m[i].length;
                if(row < 1){
                    return 0;
                }
                count *= row;
            }
            return count;
        }else if("zhuxuan".equals(playType)){
            String[] row1 = m[0];
            String[] row2 = m[2];
            int zhushu = row1.length*row2.length;
            int baozi = 0;
            //减去豹子的注数
            for(int i=0;i<row1.length;i++){
                for(int j=0; j<row2.length; j++){
                    if(row2[j].contains(row1[i]))
                    {
                        baozi ++;
                    }
                }
            }
            zhushu -= baozi;
            return zhushu;
        }else {
            return -1;
        }
    }

    /**
     * 组选六
     * @param num 个数
     * @return
     */
    public static Integer zhuliu(Integer num){
        Integer getstring = getstring(num, 3);
        return getstring;
    }
    public static Integer getstring(Integer n, Integer m){
        int n1=1, n2=1;
        for (int i=n,j=1; j<=m; n1*=i--,n2*=j++);
        Integer s = n1/n2;
        return n1/n2;
    }

    public static void main(String[] args) {
        String str1 = "0 1 2 3 4";
        String str2 = "0 1";
        String str3 = "0";
        String[] split1 = str1.split(" ");
        String[] split2 = str2.split(" ");
        String[] split3 = str3.split(" ");

        String[][] m = {split1,split2,split3};
        int zx = zhuliu(3);
        //System.out.println(zx);
        /*Integer zhuliu = zhuliu(10);
        System.out.println(zhuliu);*/
    }
}
