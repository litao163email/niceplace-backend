package com.taoli.niceplace.service;

import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.BaseResponse;
import com.taoli.niceplace.entity.VideoInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (VideoInfo)表服务接口
 *
 * @author makejava
 * @since 2023-02-25 23:46:13
 */
public interface VideoInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    PageInfo<VideoInfo> queryById(VideoInfo videoInfo,HttpServletRequest request);

    /**
     * 分页查询
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    PageInfo<VideoInfo> queryByPage(VideoInfo videoInfo, HttpServletRequest request);


    /**
     * 视频页面
     * @param videoInfo
     * @param request
     * @return
     */
    PageInfo<VideoInfo> getNewVideo(VideoInfo videoInfo, HttpServletRequest request);

    /**
     * 查询-待审核的视频
     *
     * @param videoInfo 筛选条件
     * @return 查询结果
     */
    PageInfo<VideoInfo> adminQuery(VideoInfo videoInfo, HttpServletRequest request);

    /**
     * 新增数据
     *
     * @param videoInfo 实例对象
     */
    Integer insert(VideoInfo videoInfo,HttpServletRequest request);

    /**
     * 修改数据
     *
     * @return 实例对象
     */
    Integer  update(VideoInfo videoInfo,HttpServletRequest request);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
