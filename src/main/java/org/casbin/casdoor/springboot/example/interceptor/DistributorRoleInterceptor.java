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
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 分销商角色拦截器
 */
public class DistributorRoleInterceptor implements HandlerInterceptor {

    private final CasdoorAuthService casdoorAuthService;
    
    public DistributorRoleInterceptor(CasdoorAuthService casdoorAuthService) {
        this.casdoorAuthService = casdoorAuthService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        CasdoorUser user = (CasdoorUser) session.getAttribute("casdoorUser");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/toLogin");
            return false;
        }
        
        // 使用CasdoorAuthService验证用户角色
        if (user.getRoles() != null && user.getRoles().contains("distributor")) {
            return true;
        }
        
        response.sendRedirect(request.getContextPath() + "/");
        return false;
    }
} 