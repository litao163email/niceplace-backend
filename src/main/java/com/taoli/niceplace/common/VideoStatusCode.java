package com.taoli.niceplace.common;

/**
 * 视频状态码
 *Encrypted, in review, review failed
 * @author taoli
 */
public enum VideoStatusCode {

    NORMAL(0, "正常", ""),
    REMOVE(1, "下架", ""),
    INVISIBLE(2, "作者设不可见", ""),
    ENCRYPTED(3, "加密", ""),
    REVIEW(4, "审核中", ""),
    REVIEW_FAILED(5, "审核不通过", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    VideoStatusCode(int code, String message, String description) {
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
