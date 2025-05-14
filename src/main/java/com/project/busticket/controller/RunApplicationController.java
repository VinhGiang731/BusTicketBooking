package com.project.busticket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/busticket")
public class RunApplicationController {
    @GetMapping("/homepage")
    public String home(HttpServletRequest request) {
        return "layouts/index";
    }

    @GetMapping("/admin")
    public String homeAdmin(HttpServletRequest request) {
        return "admin/login";
    }

    @GetMapping("/admin/home")
    public String adminPage(HttpServletRequest request) {
        return "admin/admin";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if (session.getAttribute("scope") == "USER") {
                return "layouts/index";
            }
        }
        return "layouts/login";
    }

    @GetMapping("/schedule")
    public String schedule(HttpServletRequest request) {
        return "layouts/schedule";
    }

    @GetMapping("/introduce")
    public String introduce(HttpServletRequest request) {
        return "layouts/introduce";
    }

    @GetMapping("/session-info")
    @ResponseBody
    public Map<String, Object> getSessionInf(HttpSession session) {
        Map<String, Object> rs = new HashMap<>();
        rs.put("userName", session != null ? session.getAttribute("userName") : null);
        rs.put("token", session != null ? session.getAttribute("token") : null);
        rs.put("scope", session != null ? session.getAttribute("scope") : null);
        return rs;
    }
}
