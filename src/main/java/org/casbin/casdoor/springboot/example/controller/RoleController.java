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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 角色控制器
 */
@Controller
public class RoleController {
    
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    
    @Resource
    private CasdoorAuthService casdoorAuthService;
    
    @RequestMapping("/distributor")
    public String distributor(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        logger.info("访问 /distributor 端点");
        
        CasdoorUser user = (CasdoorUser) session.getAttribute("casdoorUser");

        logger.info("user：{}",user);
        // 使用CasdoorAuthService验证用户角色
        if ((user != null && user.getTitle() != null && user.getTitle().contains("distributor"))||(user != null && user.getTitle() != null && user.getTitle().contains("root"))) {
            logger.info("用户 {} 有分销商角色，显示分销商页面", user.getName());
            model.addAttribute("user", user);
            return "distributor";
        }
        logger.info("userTitle:{}",user.getTitle());
        logger.warn("用户 {} 没有分销商角色，显示无权限信息", user != null ? user.getName() : "未登录");
        return "redirect:/?error=no_distributor_role";
    }
    
    @RequestMapping("/admin")
    public String admin(HttpSession session, Model model) {
        logger.info("访问 /admin 端点");
        
        CasdoorUser user = (CasdoorUser) session.getAttribute("casdoorUser");
        
        // 使用CasdoorAuthService验证用户角色
        if (user != null && user.getTitle() != null && user.getTitle().contains("root")) {
            logger.info("用户 {} 有管理员角色，显示管理员页面", user.getName());
            model.addAttribute("user", user);
            return "root";
        }
        
        logger.warn("用户 {} 没有管理员角色，显示无权限信息", user != null ? user.getName() : "未登录");
        return "redirect:/?error=no_admin_role";
    }
}