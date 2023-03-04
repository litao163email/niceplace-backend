package com.taoli.niceplace.constant;

/**
 * 用户常量
 *
 * @author taoli
 */
public interface Constant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    //  ------- 权限 --------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

    //  ------- 评论 --------
    /**
     * 初始化评论
     */
    String INIT_COMMENT = "老司机们,说出你的骚话!";

    /**
     * 初始化评论人id
     */
    long INIT_COMMENT_USER =1;

    /**
     * 初始化评论人id
     */
    String INIT_COMMENT_USER_NAME ="抖影评论员";

    String INIT_COMMENT_USER_URL = "https://p6.toutiaoimg.com/origin/tos-cn-i-qvj2lq49k0/751cd665d716456785f7d6cdcaf468a9?from=pc";

    //  ------- redis --------
    /**
     * redission配置
     */
    int REDIS_DATABASE_NUM = 15;

}
