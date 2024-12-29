package com.training.aigoushopapi.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 十五
 * 白名单配置
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> whitelist;
}
