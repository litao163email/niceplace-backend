package com.taoli.niceplace.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (VideoComment)实体类
 *
 * @author makejava
 * @since 2023-02-28 22:08:28
 */
@Data
public class VideoComment implements Serializable {
    private static final long serialVersionUID = -64242519618656408L;
    /**
     * 评论id
     */
    private Integer commentId;
    /**
     * 被评论视频id
     */
    private Integer videoId;
    /**
     * 视频url
     */
    private String videoUrl;
    /**
     * 评论描述
     */
    private String commentDescription;
    /**
     * 评论人头像
     */
    private String commentUserUrl;
    /**
     * 评论人
     */
    private Long commentUserId;
    /**
     * 评论人名称
     */
    private String commentUserName;
    /**
     * 评论时间
     */
    private Date commentTime;
    /**
     * 审核状态(0-正常,1-下线2-仅评论人自己可见,3-不宜展示4审核中5审核不通过）
     */
    private Integer status;
    /**
     * 审核人id
     */
    private Integer reviewPersonId;
    /**
     * 审核人名称
     */
    private String reviewPersonName;
    /**
     * 审核时间
     */
    private Date reviewTime;
    /**
     * 审核回复
     */
    private String reviewReply;
    /**
     * 增加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 逻辑删除
     */
    private String isDelete;

    /**
     * pageNUm
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;



}

