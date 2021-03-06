package com.ruoyi.web.platform.comments.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.web.platform.comments.domain.Comments;
import com.ruoyi.web.platform.comments.service.ICommentsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 留言板评论互动Controller
 *
 * @author party-platform
 * @date 2021-03-06
 */
@Controller
@Api("留言板评论互动Controller")
@RequestMapping("/admin/comments")
public class CommentsController extends BaseController {
    private String prefix = "admin/comments";

    @Autowired
    private ICommentsService commentsService;

    @RequiresPermissions("admin:comments:view")
    @GetMapping()
    public String comments() {
        return prefix + "/comments";
    }

    /**
     * 查询留言板评论互动列表
     */
    @ApiOperation(value = "查询留言板评论互动列表", notes = "查询留言板评论互动列表详情", tags = {"留言板评论互动Controller"})
    @RequiresPermissions("admin:comments:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Comments comments) {
        startPage();
        List<Comments> list = commentsService.selectCommentsList(comments);
        return getDataTable(list);
    }

    /**
     * 导出留言板评论互动列表
     */
    @ApiOperation(value = "导出留言板评论互动列表", notes = "导出留言板评论互动列表详情", tags = {"留言板评论互动Controller"})
    @RequiresPermissions("admin:comments:export")
    @Log(title = "留言板评论互动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Comments comments) {
        List<Comments> list = commentsService.selectCommentsList(comments);
        ExcelUtil<Comments> util = new ExcelUtil<Comments>(Comments.class);
        return util.exportExcel(list, "comments");
    }

    /**
     * 新增留言板评论互动
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存留言板评论互动
     */
    @ApiOperation(value = "新增保存留言板评论互动", notes = "新增保存留言板评论互动详情", tags = {"留言板评论互动Controller"})
    @RequiresPermissions("admin:comments:add")
    @Log(title = "留言板评论互动", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Comments comments) {
        return toAjax(commentsService.insertComments(comments));
    }

    /**
     * 修改留言板评论互动
     */
    @ApiOperation(value = "修改留言板评论互动", notes = "修改留言板评论互动详情", tags = {"留言板评论互动Controller"})
    @ApiImplicitParam(name = "commentsId", value = "主键ID", dataType = "Long", paramType = "path")
    @GetMapping("/edit/{commentsId}")
    public String edit(@PathVariable("commentsId") Long commentsId, ModelMap mmap) {
        Comments comments = commentsService.selectCommentsById(commentsId);
        mmap.put("comments", comments);
        return prefix + "/edit";
    }

    /**
     * 修改保存留言板评论互动
     */
    @ApiOperation(value = "修改保存留言板评论互动", notes = "修改保存留言板评论互动详情", tags = {"留言板评论互动Controller"})
    @RequiresPermissions("admin:comments:edit")
    @Log(title = "留言板评论互动", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Comments comments) {
        return toAjax(commentsService.updateComments(comments));
    }

    /**
     * 删除留言板评论互动
     *
     * @ApiOperation("删除留言板评论互动")
     */
    @ApiOperation(value = "删除留言板评论互动", notes = "删除留言板评论互动详情", tags = {"留言板评论互动Controller"})
    @RequiresPermissions("admin:comments:remove")
    @Log(title = "留言板评论互动", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(commentsService.deleteCommentsByIds(ids));
    }
}
