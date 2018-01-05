/**
 * Created by wyt on 2017/9/26.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @wyt
 * @create 2017-09-26 19:11
 */
public class Testchu {
    public static void main(String[] args) {
        List<Map<String,Object>> items = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        Map<String,Object> map3 = new HashMap<>();
        map.put("zhongjiangtongzhi","0");
        map1.put("kaijaigngonggao","0");
        map2.put("ssq","0");
        map3.put("klsf","0");
        items.add(map);
        items.add(map2);
        items.add(map3);
        items.add(map1);
        String s = JSONArray.toJSONString(items);
        System.out.println(s);
    }
}
