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
package org.casbin.casdoor.springboot.example.controller;

import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 角色控制器
 */
@Controller
public class RoleController {
    
    @Resource
    private CasdoorAuthService casdoorAuthService;
    
    @RequestMapping("/distributor")
    @ResponseBody
    public String distributor(HttpSession session) {
        CasdoorUser user = (CasdoorUser) session.getAttribute("casdoorUser");
        
        // 使用CasdoorAuthService验证用户角色
        if (user != null && user.getRoles() != null && user.getRoles().contains("distributor")) {
            return "分销商页面";
        }
        
        return "无权限访问，需要分销商角色";
    }
}