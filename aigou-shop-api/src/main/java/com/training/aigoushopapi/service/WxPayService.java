package com.training.aigoushopapi.service;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.training.aigoushopapi.config.MyWXPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信支付Service
 *
 * @author 十五
 */
@Slf4j
@Service
public class WxPayService {
    public static final String DEVICE_INFO = "WEB";
    public static final String BODY = "泡泡教育";
    public static final String FEE_TYPE = "CNY";
    //支付成功回调接口的URL，内网测试时需要配置内网穿透
    public static final String NOTIFY_URL = "http://wxpay.dushiwu.cn/wxpay/callback";
    public static final String TRADE_TYPE = "NATIVE";
    public static final String SIGN = "5E00F9F72173C9449F802411E36208734B8138870ED3F66D8E2821D55B317078";

    /**
     * 下单
     */
    public Map<String, String> makeWxOrder(long orderId, long price) throws Exception {
        //创建支付对象
        MyWXPayConfig config = new MyWXPayConfig();
        WXPay wxPay = new WXPay(config);
        //配置微信支付
        Map<String, String> map = new HashMap<>();
        map.put("appid", config.getAppID());
        map.put("mch_id", config.getMchID());
        map.put("device_info", DEVICE_INFO);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body", BODY);
        //创建随机订单号
        String tradeNo = UUID.randomUUID().toString().replace("-", "");
        map.put("out_trade_no", tradeNo);
        map.put("fee_type", FEE_TYPE);
        //价格
        map.put("total_fee", String.valueOf(price));
        //微信对商户后台的回调接口，更新订单状态
        map.put("notify_url", NOTIFY_URL);
        map.put("trade_type", TRADE_TYPE);
        map.put("product_id", String.valueOf(orderId));
        //执行统一下单
        Map<String, String> result = wxPay.unifiedOrder(map);
        log.info("微信下单:{}", result);
        if (result != null) {
            //保存订单号
            result.put("trade_no", tradeNo);
            //创建课程订单
            // 减少课程库存
            // 发送超时订单处理消息，超时会进入死信队列
            log.info("完成下单，订单：{}", result);
        }
        return result;
    }

    /**
     * 生成二维码
     *
     * @param url      url地址
     * @param response 响应
     */
    public void makeQRCode(String url, HttpServletResponse response) {
        //通过支付链接生成二维码
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            //把code_url包装到二维码图片
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
                    BarcodeFormat.QR_CODE, 200, 200, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG",
                    response.getOutputStream());
            log.info("创建二维码完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查订单状态
     */
    public String checkWxOrder(String tradeNo) throws Exception {
        MyWXPayConfig config = new MyWXPayConfig();
        WXPay pay = new WXPay(config);
        Map<String, String> map = new HashMap<>();
        map.put("appid", config.getAppID());
        map.put("mch_id", config.getMchID());
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("out_trade_no", tradeNo);
        map.put("sign", SIGN);
        //查询订单
        Map<String, String> res = pay.orderQuery(map);
        String state = res.get("trade_state");
        log.info("订单" + tradeNo + ",状态" + state);
        return state;
    }

    /**
     * 微信支付成功回调
     *
     * @param request  请求
     * @param response 响应
     * @throws Exception 异常
     */
    public void wxpayCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得微信传来的xml字符串
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String str = sb.toString();
        // 将字符串xml转换为Map
        Map<String, String> map = WXPayUtil.xmlToMap(str);
        // 给微信后台发送消息
        response.getWriter().println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
        // 读取订单号
        String tradeNo = map.get("out_trade_no");
        log.info("支付成功：{}", tradeNo);
        // 查询课程id
        // 增加课程销量
        // 修改订单状态
    }
}
