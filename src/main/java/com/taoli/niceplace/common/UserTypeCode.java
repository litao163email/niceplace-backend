package com.taoli.niceplace.common;

/**
 * 用户分类
// *  0 - 正常,1-异常,2-黑名单,3-封禁,5-v证,6-vip,7-子账户,8-官方,9-s官方,10-最高级用户
 * @author taoli
 */
public enum UserTypeCode {

    NORMAL(0, "正常", ""),
    ABNORMAL(1, "异常", ""),
    BLACK(2, "黑名单", ""),
    BAN(3, "封禁", ""),
    VIEWER_ROOT(4, "拟管理员(展示用户)", ""),
    PROVE(5, "v证", ""),
    MEMBERS(6, "vip", ""),
    SUB_ACCOUNT(7, "子账户", ""),
    OFFICIAL(8, "官方", ""),
    S_OFFICIAL(9, "s官方", ""),
    ROOT(10, "root用户", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    UserTypeCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
