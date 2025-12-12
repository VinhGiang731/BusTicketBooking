package com.project.busticket.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.project.busticket.enums.DataEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // Dùng lombok để inject
public class DataSourceInterceptor implements HandlerInterceptor {

    private final DatabaseStatusProvider statusProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (statusProvider.isMySqlAvailable()) {
            DynamicRoutingDatabase.set(DataEnum.MYSQL);
        } else {
            DynamicRoutingDatabase.set(DataEnum.SUBMYSQL);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        DynamicRoutingDatabase.clear();
    }
}