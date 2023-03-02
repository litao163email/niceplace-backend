package com.taoli.niceplace.controller;

import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.BaseResponse;
import com.taoli.niceplace.common.ResultUtils;
import com.taoli.niceplace.entity.VideoInfo;
import com.taoli.niceplace.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (VideoInfo)表控制层
 *
 * @author taoli
 * @since 2023-02-25 23:46:13
 */
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoInfoController {
    /**
     * 服务对象
     */
    @Resource
    private VideoInfoService videoInfoService;



    /**
     * 主页面查询
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    @GetMapping("/getVideoList")
    public BaseResponse<PageInfo<VideoInfo>> queryByPage(VideoInfo videoInfo,HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.queryByPage(videoInfo,request));
    }

    /**
     * 主页面查询
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    @GetMapping("/getNewVideo")
    public BaseResponse<PageInfo<VideoInfo>> getNewVideo(VideoInfo videoInfo,HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.getNewVideo(videoInfo,request));
    }


    /**
     * 用户页-查询我发布的视频
     *
     * @return 数据
     */
    @GetMapping("/getMyVideo")
    public BaseResponse<PageInfo<VideoInfo>> queryById(VideoInfo videoInfo,HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.queryById(videoInfo,request));
    }

    /**
     * 个人页-管理员-审核发布的视频
     *
     * @return 数据
     */
    @GetMapping("/reviewVideo")
    public BaseResponse<PageInfo<VideoInfo>> adminQuery(VideoInfo videoInfo, HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.adminQuery(videoInfo,request));
    }

    /**
     * 上传视频
     *
     * @param videoInfo 实体
     * @return 新增结果
     */
    @PostMapping("/addVideo")
    public BaseResponse<Integer> add(@RequestBody VideoInfo videoInfo,HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.insert(videoInfo,request));
    }

    /**
     * 审核视频(管理员)
     *
     * @return 编辑结果
     */
    @PostMapping("/updateVideoStatus")
    public BaseResponse<Integer> updateVideoStatus(@RequestBody VideoInfo videoInfo,HttpServletRequest request) {
        return ResultUtils.success(this.videoInfoService.update(videoInfo,request));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.videoInfoService.deleteById(id));
    }

}

