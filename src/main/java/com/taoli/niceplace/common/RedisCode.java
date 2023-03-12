package com.taoli.niceplace.common;

/**
 * redission 标识码
 * (所有常量都要记录在common这里,方便好改!不要写死在代码)
 * @author taoli
 */
public enum RedisCode {

    VIDEO_MARK("video", "视频标记", ""),
    ID_MARK("id", "id标记", "");

    private final String code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    RedisCode(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
