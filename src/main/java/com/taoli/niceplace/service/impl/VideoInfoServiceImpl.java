package com.taoli.niceplace.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.common.RedisCode;
import com.taoli.niceplace.constant.Constant;
import com.taoli.niceplace.dao.VideoCommentDao;
import com.taoli.niceplace.entity.VideoComment;
import com.taoli.niceplace.mapper.UserMapper;
import com.taoli.niceplace.utils.ApiUtils;
import com.taoli.niceplace.common.VideoStatusCode;
import com.taoli.niceplace.entity.VideoInfo;
import com.taoli.niceplace.dao.VideoInfoDao;
import com.taoli.niceplace.exception.BusinessException;
import com.taoli.niceplace.model.domain.User;
import com.taoli.niceplace.service.UserService;
import com.taoli.niceplace.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.taoli.niceplace.constant.Constant.USER_LOGIN_STATE;

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
    private VideoCommentDao videoCommentDao;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper mapper;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 查询我发布的视频(中心)
     *
     * @return 实例对象
     */
    @Override
    public PageInfo<VideoInfo>queryById(VideoInfo videoInfo, HttpServletRequest request) {

        //如果是用户自己，只能查询0(正常)/1(下架)/2(作者设置不可见)/3(加密)/4(审核中)
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser != null) {
            videoInfo.setUserId(currentUser.getId());
            videoInfo.setStatusList(new ArrayList<>(Arrays.asList(
                    VideoStatusCode.NORMAL.getCode(),
                    VideoStatusCode.REMOVE.getCode(),
                    VideoStatusCode.INVISIBLE.getCode(),
                    VideoStatusCode.ENCRYPTED.getCode(),
                    VideoStatusCode.REVIEW.getCode()
                    )));
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        int currentPage = ((null == videoInfo.getPageNum()) ? 1 : videoInfo.getPageNum());
        int pageSize = ((null == videoInfo.getPageSize()) ? 10 : videoInfo.getPageSize());

        PageHelper.startPage(currentPage, pageSize);
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);

        videoInfos.forEach(res -> {
            //获取用户头像,用作视频头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });
        return new PageInfo<>(videoInfos);
    }

    /**
     * 主页页-查询用户的全部视频
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
            //查看该用户的视频
            videoInfo.setUserId(videoInfo.getUserId());
            videoInfo.setStatusList(new ArrayList<>(Arrays.asList(
                    VideoStatusCode.NORMAL.getCode()
            )));
        } else {
            //未登录

            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        int currentPage = ((null == videoInfo.getPageNum()) ? 1 : videoInfo.getPageNum());
        int pageSize = ((null == videoInfo.getPageSize()) ? 10 : videoInfo.getPageSize());
            //注释掉,避免mybatis缓存导致重复错误
        PageHelper.startPage(currentPage, pageSize);
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);

        //注释原因:暂时不在用户页看视频过滤用户看过的,保持视频流动性
        //布隆过滤器:过滤重复的视频
//        long id = currentUser.getId();
//        String userRedisMark = RedissionMarkCode.VIDEO_MARK.getCode() + ":" + RedissionMarkCode.ID_MARK.getCode() + ":" + id;
//        RBloomFilter<Integer> seqIdBloomFilter = redissonClient.getBloomFilter(userRedisMark);
//        videoInfos.forEach(res -> {
//            if (seqIdBloomFilter.contains(res.getId())) {
//                res.setId(0);
//            }
//            //获取用户头像,用作视频所属者头像
//            User user = mapper.selectById(res.getUserId());
//            res.setUser(user);
//        });
//        //过滤掉等于0的视频
//        List<VideoInfo> videoInfoList = videoInfos.stream().filter(item -> item.getId() != 0).collect(Collectors.toList());

        videoInfos.forEach(res -> {
            //获取用户头像,用作视频所属者头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });

        return new PageInfo<>(videoInfos);
    }


    /**
     * 视频页面
     * 采用布隆过滤器过滤用户看过的重复视频
     */
    @Override
    public PageInfo<VideoInfo> getNewVideo(VideoInfo videoInfo, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        log.info("视频查询入参:{}", videoInfo);
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser != null) {
            //暂时不限制推荐品类
//            videoInfo.setVideoTypeCodeList(new ArrayList<>(Arrays.asList(
//                    VideoTypeCode.BEAUTY.getCode(),
//                    VideoTypeCode.MOVIE.getCode()
//            )));
            videoInfo.setStatus(VideoStatusCode.NORMAL.getCode());
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        long userId = currentUser.getId();
        String userRedisMark = RedisCode.VIDEO_MARK.getCode() + ":" + RedisCode.ID_MARK.getCode() + ":" + userId;
        //用户id对应的布隆过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(userRedisMark);

        //开始查询视频信息,默认是第1个(视频按时间顺序倒序)
        int currentPage = ((null == videoInfo.getPageNum()) ? 1 : videoInfo.getPageNum());
        int pageSize = ((null == videoInfo.getPageSize()) ? 10 : videoInfo.getPageSize());

        //返回的视频数量
        int pageEnd = 10;

        //存一直剩下来的值
        List<VideoInfo> videoInfoSum = new ArrayList<>();

        long startBloom = System.currentTimeMillis();

        //第n次循环
        int n = 1;
        //循环停止条件是当加起来的值>分页返回的视频数量
        //这里为什么是pageSize 20 而不是 pageEnd,因为需要提高命中率

        while (videoInfoSum.size() < pageSize) {

            long sqlStart = System.currentTimeMillis();

            PageHelper.startPage(currentPage, pageSize);
            List<VideoInfo> videoInfoFromMysql = this.videoInfoDao.getNewVideo(videoInfo);
            //这上面的查询语句存在一个问题,每次查都要从头开始查,数据库查询次数会很多,下面的视频是没机会先查到吗?
            //优化思路:
            // 1、两边向中间查
            // 2、"423"模式:先查最新的(第4批数据),如果为空,说明下一批(第3批)很大概率也是用户已看过的;
            //      则优先跳两个查去查(第2批),如果bloom后(第2批)用户全部没看过,说明(第3批)是用户目前看到的阶段; (若也为空,继续跳)
            //      此时再去查(第3批)。
            //      这样就能减少一半的查询次数,这个可以调整为跳(2/3/4),对应降低(50%/30%/25%)查询次数,可以自行优化
            // 3、(推荐) 二分法:直接获取数据库中间的数据,收缩查看用户次数已经看到的视频位置
            // todo 待实现

            long sqlEnd = System.currentTimeMillis();
            log.info("sql执行总时间耗时:{}", sqlEnd - sqlStart);
            //如果查出来没有数据,直接返回合集
            if (videoInfoFromMysql.size() == 0) {
                break;
            }

            long bloomStart = System.currentTimeMillis();
            //首先第一次过滤
            if (bloomFilter != null) {
                videoInfoFromMysql.forEach(res -> {
                    //布隆过滤器防重推荐视频
                    if (bloomFilter.contains(res.getId())) {
                        //将视频id设置为-1(不可能的值,用于过滤)
                        res.setId(-1);
                    }
                });
            }

            long bloomEnd = System.currentTimeMillis();
            log.info("bloomEnd执行总时间耗时:{}", bloomEnd - bloomStart);
            //第一次过滤:过滤掉等于0的视频
            List<VideoInfo> streamList = videoInfoFromMysql.stream().filter(item -> item.getId() != -1).collect(Collectors.toList());

            //如果第一次过滤查出来的视频数小于10个
            if (streamList.size() < pageEnd) {
                currentPage = pageSize * n + currentPage;
                //下一轮查询
                n++;
            }

            //将过滤后的值放入合集中
            videoInfoSum.addAll(streamList);
            //清空
            videoInfoFromMysql.clear();
            streamList.clear();
        }
        videoInfoSum.forEach(res -> {
            //获取用户头像,用作视频所属者头像
            User user = mapper.selectById(res.getUserId());
            res.setUser(user);
        });
        long end = System.currentTimeMillis();

        log.info("布隆过滤器与sql执行总时间耗时:{}", end - startBloom);
        log.info("视频页用了布隆过滤器总时间耗时:{}", end - start);

        return new PageInfo<>(videoInfoSum);
    }

    /**
     * 用户页-管理员-查询待审核的视频
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    @Override
    public PageInfo<VideoInfo> adminQuery(VideoInfo videoInfo, HttpServletRequest request) {

        int currentPage = ((null == videoInfo.getPageNum()) ? 1 : videoInfo.getPageNum());
        int pageSize = ((null == videoInfo.getPageSize()) ? 10 : videoInfo.getPageSize());

        log.info("审核视频查询入参:" + videoInfo);

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        log.info("审核视频的人:" + currentUser);
        if (currentUser != null && userService.isAdmin(currentUser)) {
            //能查询审核的视频，状态(4-审核中)'
            videoInfo.setStatusList(new ArrayList<>(Collections.singletonList(4)));
        } else {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        PageHelper.startPage(currentPage,pageSize);
        List<VideoInfo> videoInfos = this.videoInfoDao.queryAllByLimit(videoInfo);
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
        videoInfo.setPictureUrl(ApiUtils.getImageUrl());
        videoInfo.setExpireTime(LocalDateTime.now());
        //这里不对视频类型进行审批,由用户决定,在管理员审批阶段再可以移去某些视频类型

        int insert = this.videoInfoDao.insert(videoInfo);
        return insert;
    }

    /**
     * 视频审核
     *
     * @return 实例对象
     */
    @Override
    public Integer update(VideoInfo videoInfo, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;

        if (!userService.isAdmin(currentUser)) {
            //无权限
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        //审核员id
        videoInfo.setReviewPersonId(currentUser.getId());
//        videoInfo.setUserId(currentUser.getId());
        //对于导入的视频进行数据上的补充
        videoInfo.setDescription(ApiUtils.getText());
        videoInfo.setVideoUrl(ApiUtils.getImageUrl());


        log.info("视频id为{}的审核意见:{}",videoInfo.getId(),videoInfo.getStatus());
        int update = this.videoInfoDao.update(videoInfo);

        //对审核通过的数据插入一条评论
        asyncInsertCommentWhenReview(videoInfo);

        return update;
    }

    @Async
    public void asyncInsertCommentWhenReview(VideoInfo videoInfo){
        //对审核通过的数据插入一条评论
        VideoComment videoComment = new VideoComment();
        videoComment.setVideoId(videoInfo.getId());
        videoComment.setVideoUrl(videoInfo.getVideoUrl());
        videoComment.setCommentDescription(Constant.INIT_COMMENT);
        videoComment.setCommentUserUrl(Constant.INIT_COMMENT_USER_URL);
        videoComment.setCommentUserId(Constant.INIT_COMMENT_USER);
        videoComment.setCommentUserName(Constant.INIT_COMMENT_USER_NAME);
        videoComment.setStatus(0);
        int insert = this.videoCommentDao.insert(videoComment);
        if(insert>0){
            log.info("插入评论成功,视频id为:{}",videoInfo.getId());
        }else {
            log.info("插入评论失败,视频id为:{}",videoInfo.getId());
        }
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
