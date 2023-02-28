package com.taoli.niceplace.dao;

import com.taoli.niceplace.entity.VideoComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (VideoComment)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-28 22:08:28
 */
public interface VideoCommentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    VideoComment queryById(Integer commentId);

    /**
     * 查询指定行数据
     *
     * @param videoComment 查询条件
     * @return 对象列表
     */
    List<VideoComment> queryAllByLimit(VideoComment videoComment);

    /**
     * 统计总行数
     *
     * @param videoComment 查询条件
     * @return 总行数
     */
    long count(VideoComment videoComment);

    /**
     * 新增数据
     *
     * @param videoComment 实例对象
     * @return 影响行数
     */
    int insert(VideoComment videoComment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<VideoComment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<VideoComment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<VideoComment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<VideoComment> entities);

    /**
     * 修改数据
     *
     * @param videoComment 实例对象
     * @return 影响行数
     */
    int update(VideoComment videoComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 影响行数
     */
    int deleteById(Integer commentId);

}

