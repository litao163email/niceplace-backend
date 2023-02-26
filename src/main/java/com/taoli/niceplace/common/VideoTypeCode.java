package com.taoli.niceplace.common;

/**
 * 视频类型码
 * 视频类型(0-美女,1-风景,2-美食,3-电影,4-搞笑,5-生活)
 * @author taoli
 */
public enum VideoTypeCode {

    BEAUTY(0, "美女", ""),
    SCENERY(1, "风景", ""),
    FOOD(2, "美食", ""),
    MOVIE(3, "电影", ""),
    FUNNY(4, "搞笑", ""),
    LIFE(5, "生活", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    VideoTypeCode(int code, String message, String description) {
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
