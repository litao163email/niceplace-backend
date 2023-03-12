package com.taoli.niceplace.common;

import io.swagger.models.auth.In;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.*;
import java.util.ArrayList;

/**
 * todo
 * 权限控制,对于登录用户进行接口的切面权限控制
 * @author taoli
 * @date 2023-3-3 17:11:07
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    //默认为正常用户
    UserTypeCode[] value() default {UserTypeCode.NORMAL};
}
