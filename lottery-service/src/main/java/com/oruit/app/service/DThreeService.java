package com.oruit.app.service;/**
 * Created by wyt on 2017-11-03.
 */

import com.oruit.app.util.web.ResultBean;
import java.util.Map;

/**3D选号下单
 * @author @wyt
 * @create 2017-11-03 9:48
 */
public interface DThreeService {
    /**
     * 3d选号
     * @param maps
     * @return
     */
    ResultBean dThreeChoose(Map<String,Object> maps) throws Exception;

}