package com.taoli.niceplace.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.entity.VideoComment;
import com.taoli.niceplace.dao.VideoCommentDao;
import com.taoli.niceplace.service.VideoCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (VideoComment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-28 22:08:29
 */
@Service("videoCommentService")
public class VideoCommentServiceImpl implements VideoCommentService {
    @Resource
    private VideoCommentDao videoCommentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    @Override
    public VideoComment queryById(Integer commentId) {
        return this.videoCommentDao.queryById(commentId);
    }

    /**
     * 分页查询
     *
     * @param videoComment 筛选条件
     * @return 查询结果
     */
    @Override
    public PageInfo<VideoComment> queryByPage(VideoComment videoComment,HttpServletRequest request) {
        PageHelper.startPage(videoComment.getPageNum(), videoComment.getPageSize());
        return new PageInfo<>(this.videoCommentDao.queryAllByLimit(videoComment));
    }

    /**
     * 新增评论
     *
     * @param videoComment 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(VideoComment videoComment) {
        int insert = this.videoCommentDao.insert(videoComment);
        return insert;
    }

    /**
     * 修改数据
     *
     * @param videoComment 实例对象
     * @return 实例对象
     */
    @Override
    public VideoComment update(VideoComment videoComment) {
        this.videoCommentDao.update(videoComment);
        return this.queryById(videoComment.getCommentId());
    }

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer commentId) {
        return this.videoCommentDao.deleteById(commentId) > 0;
    }
}
