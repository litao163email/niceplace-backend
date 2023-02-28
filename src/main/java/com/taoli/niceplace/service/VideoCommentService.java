package com.taoli.niceplace.service;

import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.entity.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * (VideoComment)表服务接口
 *
 * @author makejava
 * @since 2023-02-28 22:08:29
 */
public interface VideoCommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    VideoComment queryById(Integer commentId);

    /**
     * 分页查询
     *
     * @param videoComment 筛选条件
     * @return 查询结果
     */
    PageInfo<VideoComment> queryByPage(VideoComment videoComment, HttpServletRequest request);

    /**
     * 新增数据
     *
     * @param videoComment 实例对象
     * @return 实例对象
     */
    Integer insert(VideoComment videoComment);

    /**
     * 修改数据
     *
     * @param videoComment 实例对象
     * @return 实例对象
     */
    VideoComment update(VideoComment videoComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer commentId);

}
