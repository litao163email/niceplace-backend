package com.taoli.niceplace.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.mapper.UserMapper;
import com.taoli.niceplace.utils.DateTimeUtils;
import com.taoli.niceplace.utils.ImageUrlApi;
import com.taoli.niceplace.common.VideoStatusCode;
import com.taoli.niceplace.entity.VideoInfo;
import com.taoli.niceplace.dao.VideoInfoDao;
import com.taoli.niceplace.exception.BusinessException;
import com.taoli.niceplace.model.domain.User;
import com.taoli.niceplace.service.UserService;
import com.taoli.niceplace.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.taoli.niceplace.constant.UserConstant.USER_LOGIN_STATE;

/**
 * (VideoInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 23:46:13
 */
@Service("videoInfoService")
@Slf4j
public class VideoInfoServiceImpl implements VideoInfoService {
    @Resource
    private VideoInfoDao videoInfoDao;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper mapper;

    /**
     * 查询我发布的视频(中心)
     *
     * @return 实例对象
     */
    @Override
    public PageInfo<VideoInfo> queryById(VideoInfo videoInfo, HttpServletRequest request) {

        //如果是用户自己，只能查询0(正常)、1(下架)、2(作者设置不可见)、3(加密)、4(审核中)，其余不允许
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser != null) {
            videoInfo.setUserId(currentUser.getId());
            videoInfo.setStatusList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)));
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        PageHelper.startPage(videoInfo.getPageNum(), videoInfo.getPageSize());
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);

        videoInfos.forEach(res -> {
            //获取用户头像,用作视频头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });

        return new PageInfo<>(videoInfos);
    }

    /**
     * 主页页-查询全部视频
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    @Override
    public PageInfo<VideoInfo> queryByPage(VideoInfo videoInfo, HttpServletRequest request) {

        log.info("视频查询入参:" + videoInfo);
        //一、主页面
        //只能查询该用户状态0正常、且id是ta的视频，状态(0-正常，1-下架，2-作者设不可见，3-加密，4-审核中，5-审核不通过)'
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser != null) {
            videoInfo.setStatusList(new ArrayList<>(Arrays.asList(0)));
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        PageHelper.startPage(videoInfo.getPageNum(), videoInfo.getPageSize());
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);

        videoInfos.forEach(res -> {
            //获取用户头像,用作视频头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });

        return new PageInfo<>(videoInfos);
    }


    /**
     * 用户页-管理员-查询待审核的视频
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    @Override
    public PageInfo<VideoInfo> adminQuery(VideoInfo videoInfo, HttpServletRequest request) {

        log.info("审核视频查询入参:" + videoInfo);
        //能查询审核的视频，状态(4-审核中)'
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        log.info("审核视频的人:" + currentUser);
        if (currentUser != null && userService.isAdmin(currentUser)) {
            videoInfo.setStatusList(new ArrayList<>(Collections.singletonList(VideoStatusCode.REVIEW.getCode())));
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        PageHelper.startPage(videoInfo.getPageNum(), videoInfo.getPageSize());
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);
        //时间转码
        DateTimeFormatter fmt = DateTimeUtils.dateTimeFormatter2Str();
        videoInfos.forEach(res -> {
            //获取用户头像,用作视频头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });

        return new PageInfo<>(videoInfos);
    }

    /**
     * 上传视频
     *
     * @param videoInfo 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(VideoInfo videoInfo, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;

        //用户自己的id
        videoInfo.setUserId(currentUser.getId());

        if (userService.isAdmin(currentUser)) {
            //管理员权限:任意上传任何userid
            if (null == videoInfo.getUserId()) {
                //若传入为空,则是管理员自己的id
                videoInfo.setUserId(currentUser.getId());
            } else {
                //若不为空,则是传入的"其它用户的id"
            }

        } else if (currentUser != null && !userService.isAdmin(currentUser)) {
            //无权限则置空
            videoInfo.setUserId(null);
            videoInfo.setTeamId(null);
            videoInfo.setStatus(null);

            //非管理员权限下,设置成用户自己的id
            videoInfo.setUserId(currentUser.getId());
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        //设置审核中
        videoInfo.setStatus(VideoStatusCode.REVIEW.getCode());
        //设置是否删除
        videoInfo.setIsDelete(0);
        videoInfo.setOosIsDelete(0);
        videoInfo.setPictureUrl(ImageUrlApi.getImageUrl());
        videoInfo.setExpireTime(LocalDateTime.now());
        //这里不对视频类型进行审批,由用户决定,在管理员审批阶段再可以移去某些视频类型

        int insert = this.videoInfoDao.insert(videoInfo);
        return insert;
    }

    /**
     * 修改数据
     *
     * @return 实例对象
     */
    @Override
    public Integer update(VideoInfo videoInfo, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;

        //审核员id
        videoInfo.setUserId(currentUser.getId());

        if (!userService.isAdmin(currentUser)) {
            //无权限
            throw new BusinessException(ErrorCode.NO_AUTH);
        }


        log.info("审核意见:" + videoInfo.getStatus());
        int update = this.videoInfoDao.update(videoInfo);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.videoInfoDao.deleteById(id) > 0;
    }
}
