package com.taoli.niceplace.controller;

import com.github.pagehelper.PageInfo;
import com.taoli.niceplace.common.BaseResponse;
import com.taoli.niceplace.common.ResultUtils;
import com.taoli.niceplace.entity.VideoComment;
import com.taoli.niceplace.service.VideoCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (VideoComment)表控制层
 *
 * @author taoli
 * @since 2023-02-28 22:08:28
 */
@RestController
@RequestMapping("/videoComment")
@CrossOrigin(origins = {"http://localhost:5173"})
public class VideoCommentController {
    /**
     * 服务对象
     */
    @Resource
    private VideoCommentService videoCommentService;

    /**
     * 分页查询评论
     *
     * @param videoComment 筛选条件
     * @return 查询结果
     */
    @GetMapping("/getVideoCommentList")
    public BaseResponse<PageInfo<VideoComment>> getVideoCommentList(VideoComment videoComment,HttpServletRequest request) {
        return ResultUtils.success(this.videoCommentService.queryByPage(videoComment, request));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getVideoLis")
    public ResponseEntity<VideoComment> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.videoCommentService.queryById(id));
    }

    /**
     * 新增评论
     *
     * @param videoComment 实体
     * @return 新增结果
     */
    @PostMapping("/addComment")
    public BaseResponse<Integer> add(@RequestBody VideoComment videoComment,HttpServletRequest request) {
        return ResultUtils.success(this.videoCommentService.insert(videoComment,request));
    }

    /**
     * 编辑数据
     *
     * @param videoComment 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<VideoComment> edit(VideoComment videoComment) {
        return ResponseEntity.ok(this.videoCommentService.update(videoComment));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.videoCommentService.deleteById(id));
    }

}

