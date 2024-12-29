package com.training.aigoushopapi.config;
import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author 十五
 */
@Configuration
public class MyWXPayConfig implements WXPayConfig {
    @Override
    public String getAppID() {
        return "wx307113892f15a42e";
    }

    @Override
    public String getMchID() {
        return "1508236581";
    }

    @Override
    public String getKey() {
        return "HJd7sHGHd6djgdgFG5778GFfhghghgfg";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
