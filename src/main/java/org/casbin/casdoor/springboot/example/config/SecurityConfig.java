
package org.casbin.casdoor.springboot.example.config;

import org.casbin.casdoor.service.CasdoorAuthService;
import org.casbin.casdoor.springboot.example.interceptor.DistributorRoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置，用于角色权限控制
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private CasdoorAuthService casdoorAuthService;
    
    @Bean
    public DistributorRoleInterceptor distributorRoleInterceptor() {
        return new DistributorRoleInterceptor(casdoorAuthService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 分销商角色拦截器
        registry.addInterceptor(distributorRoleInterceptor())
                .addPathPatterns("/distributor");
    }
}