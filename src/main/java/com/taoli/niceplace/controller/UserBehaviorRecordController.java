package com.taoli.niceplace.controller;

import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.BaseResponse;
import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.common.ResultUtils;
import com.taoli.niceplace.entity.VideoInfo;
import com.taoli.niceplace.exception.BusinessException;
import com.taoli.niceplace.model.domain.User;
import com.taoli.niceplace.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RMap;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

import static com.taoli.niceplace.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户app行为记录
 *
 * @author taoli
 * @since 2023-3-1 14:08:43
 */
@RestController
@RequestMapping("/behavior")
@Slf4j
public class UserBehaviorRecordController {

    @Resource
    private RedissonClient redissonClient;

    /**
     * redis 视频标记
     */
    private static String VIDEO_MARK="video";
    /**
     * redis 视频id标记
     */
    private static String ID_MARK="id";

    /**
     * 埋点记录用户点击视频的id
     * redis布隆过滤器-防止重复看相同视频
     */
    @GetMapping("/recordUserWatchVideoId")
    public BaseResponse<String> recordUserWatchVideoId(VideoInfo videoInfo, HttpServletRequest request) {
        log.info("/recordUserWatchVideoId接口入参:{}",videoInfo);
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (StringUtils.isBlank(currentUser.getUserAccount())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long userId = currentUser.getId();
        String userRedisMark = VIDEO_MARK + ":" + ID_MARK + ":" + userId;

        RBloomFilter<Integer> seqIdBloomFilter = redissonClient.getBloomFilter(userRedisMark);
        //预期元素数量和误差率:预期元素数量是指布隆过滤器预期处理的元素数量，误差率是指布隆过滤器误判的概率。
        //预计视频数量在1000左右
        seqIdBloomFilter.tryInit(1000, 0.01);

        if(videoInfo.getId()>0){
                //向该用户id下添加视频的id
            seqIdBloomFilter.add(videoInfo.getId());
        }else{
            //仅作为用户注册布隆过滤器的接口(不先注册在查询时会出错),不作任何动作,此接口埋点在用户点击视频导航栏时触发
            //todo 预留其它操作

        }
        log.info("/recordUserWatchVideoId接口的布隆过滤器注册标识符:"+userRedisMark);
        return ResultUtils.success("success to set value videoId in redis");
    }
}

