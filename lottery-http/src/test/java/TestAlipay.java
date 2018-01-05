/**
 * Created by wyt on 2017/9/25.
 */

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.oruit.app.alipay.config.AlipayConfig;

import java.net.URLEncoder;

/**
 * @author @wyt
 * @create 2017-09-25 21:13
 */
public class TestAlipay {

    public static void main(String[] args) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "utf-8", AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setPassbackParams(URLEncoder.encode("app-h5-app"));  //描述信息  添加附加数据
        model.setSubject("充值"); //商品标题
        model.setOutTradeNo(com.oruit.app.util.PayUtil.getTradeNo()); //商家订单编号
        model.setTimeoutExpress("30m"); //超时关闭该订单时间
        model.setTotalAmount("0.01");  //订单总金额
        model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        request.setNotifyUrl("https://www.cetan.com.cn/lottery/chongzhinotify_url");  //回调地址
        String orderStr = "";
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
           // System.out.println(orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------");
        System.out.println("-------------------------:"+orderStr);
        System.out.println("-------------------------");
    }
}
