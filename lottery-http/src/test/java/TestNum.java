/**
 * Created by wyt on 2017/9/19.
 */

import java.util.*;

/**
 * @author @wyt
 * @create 2017-09-19 17:29
 */
public class TestNum {
    public static void main(String[] args) {
        List<String> lists = new ArrayList<>();
        String strs1 = "01 02 08";
        String strs2 = "01 02 05";
        String strs3 = "01 02 06 10";
        String strs4 = "01 02 03 10 15";
        lists.add(strs1);
        lists.add(strs2);
        lists.add(strs3);
        lists.add(strs4);

        Map<String, Object> stringObjectMap = GetMissing(lists);
        System.out.println(stringObjectMap.get("11"));


    }

    private static Map<String,Object> GetMissing(List<String> lists) {
        StringBuffer sb = new StringBuffer();
        for(int i = 1 ;i<21 ;i++){
            String format = String.format("%02d", i);
            sb = sb.append(format);
            sb.append(",");
        }
        String s2 = sb.toString().substring(0,sb.toString().length()-1);
        String[] split1 = s2.split(",");
        Map<String, String> map = new HashMap<>();
        for (int i = 0 ; i<lists.size();i++){
            String s = lists.get(i);
            String[] split = s.split(" ");

            if(i == 0){
                for (int z = 1 ; z < 21 ;z++){
                    String format = String.format("%02d", z);
                    map.put(format,"1");
                }
                for (int j = 0 ; j < split.length ; j++) {
                    map.put(split[j],"0");
                }
            }
            if(i > 0){
                for (int j = 0 ; j < split.length ; j++) {
                    map.put(split[j],"0");
                }
                String[] strings = arrContrast(split1, split);
                for (String strResult : strings) {
                    map.put(strResult,String.valueOf(Integer.parseInt(map.get(strResult))+1));
                }
            }


        }
        Set set = map.entrySet();
        Iterator it = set.iterator();
        Map<String,Object> maps = new HashMap<>();
         List<Map<String,Object>> list = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
            System.out.println(entry.getKey() + " 的遗漏次数 : " + entry.getValue());
            maps.put(entry.getKey(),entry.getValue());
            list.add(maps);
        }
        return maps;
    }

    //处理数组字符
    private static String[] arrContrast(String[] arr1, String[] arr2){
        List<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {      //如果第二个数组存在和第一个数组相同的值，就删除
            if(list.contains(str)){
                list.remove(str);
            }
        }
        String[] result = {};   //创建空数组
        return list.toArray(result);    //List to Array
    }
}
