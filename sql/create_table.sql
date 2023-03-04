--一定要养成保存表字段和备份数据的习惯!

create
database if not exists niceplace;

use
niceplace;
--用户评论表
CREATE TABLE `video_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `video_id` int(11) DEFAULT NULL COMMENT '被评论视频id',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频url',
  `comment_description` varchar(255) DEFAULT NULL COMMENT '评论描述',
  `comment_user_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评论人头像',
  `comment_user_id` bigint(255) DEFAULT NULL COMMENT '评论人',
  `comment_user_name` varchar(255) DEFAULT NULL COMMENT '评论人名称',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  `status` int(255) NOT NULL COMMENT '审核状态(0-正常,1-下线2-仅评论人自己可见,3-不宜展示4审核中5审核不通过）',
  `review_person_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `review_person_name` varchar(255) DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_reply` varchar(255) DEFAULT NULL COMMENT '审核回复',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '增加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `is_delete` varchar(255) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--视频信息表
CREATE TABLE `video_info` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频描述',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频url',
  `oos_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '目前所在服务器ip',
  `src` varchar(32) DEFAULT NULL COMMENT '相对路径(方便转移)',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '缩略图',
  `tags` varchar(255) DEFAULT NULL COMMENT '视频标签',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '上传人id',
  `team_id` int(32) DEFAULT NULL COMMENT '团队id',
  `video_type_code` int(16) NOT NULL COMMENT '视频类型视频类型(2-优质视频集合,3-美女,4-风景,5-美食,6-电影,7-搞笑)',
  `status` int(8) DEFAULT NULL COMMENT '状态(0-正常，1-下架，2-作者设不可见，3-加密，4-审核中，5-审核不通过,7-用户取消审核,6-无效视频,7-被举报)',
  `review_person_id` int(128) DEFAULT NULL COMMENT '视频审核员(问责机制)',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_comment` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审批意见',
  `video_from` int(32) DEFAULT NULL COMMENT '视频上传方式(0-管理员上传,1-excel导入,,2-用户上传)',
  `video_source` int(32) DEFAULT NULL COMMENT '视频来源(0-抖音,1-网盘,,2-用户自己,3-其它)',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频密码',
  `view_count` int(255) DEFAULT NULL COMMENT '播放量',
  `like_count` int(255) DEFAULT NULL COMMENT '点赞数',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `is_delete` int(4) DEFAULT NULL COMMENT '是否删除(0/1删除)',
  `oos_is_delete` int(8) DEFAULT NULL COMMENT '在文件服务器中是否删除(0/1删除)',
  PRIMARY KEY (`id`,`video_type_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 用户表
CREATE TABLE `user` (
  `username` varchar(256) DEFAULT NULL COMMENT '用户昵称',
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) DEFAULT NULL COMMENT '账号',
  `avatarUrl` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `userPassword` varchar(512) NOT NULL COMMENT '密码',
  `phone` varchar(128) DEFAULT NULL COMMENT '电话',
  `email` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `userStatus` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0 - 正常,1-异常,2-黑名单,3-封禁,5-v证,6-vip,7-子账户,8-官方,9-s官方,10-最高级',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `userRole` int(11) NOT NULL DEFAULT '0' COMMENT '用户角色(0-普通用户,1-管理员,2-拟管理员,3-vip用户,4-审核员,5-黑名单,6-气氛员)',
  `niceCode` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '幸运编号',
  `tags` varchar(1024) DEFAULT NULL COMMENT '标签 json 列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=995032 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- 队伍表
create table team
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)       not null comment '队伍名称',
    description varchar(1024) null comment '描述',
    maxNum      int      default 1 not null comment '最大人数',
    expireTime  datetime null comment '过期时间',
    userId      bigint comment '用户id（队长 id）',
    status      int      default 0 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512) null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0 not null comment '是否删除'
) comment '队伍';

-- 用户队伍关系
create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint comment '用户id',
    teamId     bigint comment '队伍id',
    joinTime   datetime null comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0 not null comment '是否删除'
) comment '用户队伍关系';


-- 标签表（可以不创建，因为标签字段已经放到了用户表中）
create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tagName    varchar(256) null comment '标签名称',
    userId     bigint null comment '用户 id',
    parentId   bigint null comment '父标签 id',
    isParent   tinyint null comment '0 - 不是, 1 - 父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0 not null comment '是否删除',
    constraint uniIdx_tagName
        unique (tagName)
) comment '标签';

create index idx_userId
    on tag (userId);