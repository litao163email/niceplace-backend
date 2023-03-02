package com.taoli.niceplace.entity;

import com.taoli.niceplace.model.domain.User;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

/**
 * (VideoInfo)实体类
 *
 * @author makejava
 * @since 2023-02-25 23:46:13
 */
@Data
public class VideoInfo implements Serializable {
    private static final long serialVersionUID = -81721029826522896L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 视频描述
     */
    private String description;
    /**
     * 相对路径(方便转移)
     */
    private String src;
    /**
     * 视频标签
     */
    private String tags;
    /**
     * 状态(0-正常，1-下架，2-作者设不可见，3-加密，4-审核中，5-审核不通过)'
     */
    private Integer status;

    private ArrayList<Integer> statusList;

    private Integer reviewPersonId;

    /**
     * 视频类型码
     */
    private Integer videoTypeCode;

    private ArrayList<Integer> videoTypeCodeList;
    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 密码
     */
    private String password;
    /**
     * 视频url
     */
    private String videoUrl;
    /**
     * 目前所在服务器ip
     */
    private String oosIp;
    /**
     * 缩略图
     */
    private String pictureUrl;
    /**
     * 上传人id
     */
    private Long userId;
    /**
     * 上传人信息
     */
    private User user;
    /**
     * 团队id
     */
    private Integer teamId;
    /**
     * 播放量
     */
    private Integer viewCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    private String expireTimeStr;
    /**
     * 是否删除(0/1删除)
     */
    private Integer isDelete;
    /**
     * 在文件服务器中是否删除(0/1删除)
     */
    private Integer oosIsDelete;


    /**
     * pageNUm
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;
}

