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

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("USER".equals(session.getAttribute("scope"))) {
                return "layouts/index";
            }
        }
        return "layouts/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "layouts/index";
    }

    @GetMapping("/schedule")
    public String schedule(HttpServletRequest request) {
        return "layouts/schedule";
    }

    @GetMapping("/introduce")
    public String introduce(HttpServletRequest request) {
        return "layouts/introduce";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("USER".equals(session.getAttribute("scope"))) {
                return "layouts/profile";
            }
        }
        return "layouts/login";
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

    @GetMapping("/admin")
    public String homeAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/admin";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "admin/login";
    }

    @GetMapping("/admin/home")
    public String adminPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/admin";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/busmanage")
    public String busManage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/busmanagement";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/dashboard";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/ticketmanage")
    public String ticketManagement(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/ticketmanagement";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/usermanage")
    public String userManagement(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            if ("ADMIN".equals(session.getAttribute("scope"))) {
                return "admin/usermanagement";
            }
        }
        return "admin/login";
    }

    @RequestMapping("/check/login")
    @ResponseBody
    public Map<String, Boolean> checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Map<String, Boolean> rs = new HashMap<>();
        if (session != null && session.getAttribute("userName") != null) {
            if ("USER".equals(session.getAttribute("scope"))) {
                rs.put("state", true);
                return rs;
            }
        }
        rs.put("state", false);
        return rs;
    }
}
