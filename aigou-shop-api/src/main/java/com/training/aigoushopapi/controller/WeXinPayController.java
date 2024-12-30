package com.training.aigoushopapi.controller;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.service.WxPayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信支付
 *
 * @author 十五
 */
@RestController
@ResponseResult
@RequestMapping("/wxpay")
public class WeXinPayController {

    private static final Logger log = LoggerFactory.getLogger(WeXinPayController.class);
    @Resource
    private WxPayService payService;

    /**
     * 微信下单
     */
    @RequestMapping("makeOrder")
    public Map<String, String> makeOrder(Long
                                                 orderId, Long price) throws Exception {
        //微信下单
        return payService.makeWxOrder(orderId, price);
    }

    /**
     * 生成支付二维码
     *
     * @param url      url
     * @param response 响应
     */
    @RequestMapping("code")
    public void createWxPayCode(String url, HttpServletResponse response) {
        //创建支付二维码
        payService.makeQRCode(url, response);
        log.info("创建二维码完成{}", url);
    }


    /**
     * 检查订单状态
     */
    @RequestMapping("checkOrder")
    public String checkOrder(String tradeNo) throws Exception {
        if (StringUtils.isEmpty(tradeNo)) {
            return null;
        }
        String status = payService.checkWxOrder(tradeNo);
        log.info("检查订单{} 状态：{}", tradeNo, status);
        return status;
    }

    /**
     * 微信支付平台支付成功的回调
     */
    @RequestMapping("callback")
    public void paySuccessCallback(HttpServletRequest request, HttpServletResponse
            response) throws Exception {
        payService.wxpayCallback(request, response);
    }
}
