// Copyright 2021 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.casbin.casdoor.springboot.example.config;

import org.casbin.casdoor.service.CasdoorAuthService;
import org.casbin.casdoor.springboot.example.interceptor.DistributorRoleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CasdoorAuthService casdoorAuthService;
    
    @Bean
    public DistributorRoleInterceptor distributorRoleInterceptor() {
        logger.info("创建分销商角色拦截器");
        return new DistributorRoleInterceptor(casdoorAuthService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 分销商角色拦截器
        logger.info("注册分销商角色拦截器，拦截路径: /distributor");
        registry.addInterceptor(distributorRoleInterceptor())
                .addPathPatterns("/distributor");
    }
}