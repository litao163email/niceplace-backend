package com.taoli.niceplace.config;

import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.exception.BusinessException;
import com.taoli.niceplace.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.taoli.niceplace.constant.Constant.USER_LOGIN_STATE;

/**
 * todo
 * 拦截器
 * (为了不影响接口responseTime,应尽量避免进行复杂的操作)
 * @author taoli
 *
 * 需要先引入spring-boot-starter-Aspect的依赖
 */
@Slf4j
@Component
@Aspect
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        log.info("进入拦截器的用户:" + currentUser);


        //todo
//        if (!Optional.ofNullable(currentUser).isPresent()) {
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
