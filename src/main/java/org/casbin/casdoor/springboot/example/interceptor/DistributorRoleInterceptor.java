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
package org.casbin.casdoor.springboot.example.interceptor;

import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 分销商角色拦截器
 */
public class DistributorRoleInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(DistributorRoleInterceptor.class);
    
    private final CasdoorAuthService casdoorAuthService;
    
    public DistributorRoleInterceptor(CasdoorAuthService casdoorAuthService) {
        this.casdoorAuthService = casdoorAuthService;
        logger.info("DistributorRoleInterceptor 已初始化");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截请求: {}", request.getRequestURI());
        
        HttpSession session = request.getSession();
        CasdoorUser user = (CasdoorUser) session.getAttribute("casdoorUser");
        
        if (user == null) {
            logger.warn("用户未登录，重定向到登录页面");
            // 拦截器中不方便添加Flash消息，直接使用URL参数
            response.sendRedirect(request.getContextPath() + "/toLogin");
            return false;
        }
        
        logger.info("当前用户: {}, 角色: {}", user.getName(), user.getTitle());
        
        // 使用CasdoorAuthService验证用户角色
        if ((user.getTitle() != null && user.getTitle().contains("root"))||(user.getTitle() != null && user.getTitle().contains("distributor"))){
            logger.info("用户 {} 有分销商角色，允许访问", user.getName());
            return true;
        }
        
        logger.warn("用户 {} 没有分销商角色，拒绝访问", user.getName());
        // 拦截器中不方便添加Flash消息，直接使用URL参数
        response.sendRedirect(request.getContextPath() + "/?error=no_distributor_role");
        return false;
    }
} 