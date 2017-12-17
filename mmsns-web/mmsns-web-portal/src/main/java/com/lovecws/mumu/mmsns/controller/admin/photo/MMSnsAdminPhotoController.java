package com.lovecws.mumu.mmsns.controller.admin.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 图库管理
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminPhotoController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/{userName}/photo/comment"}, method = RequestMethod.GET)
    public String photoComment(@PathVariable String userName) {
        request.setAttribute("adminModular", "photoComment");
        return "/admin/photo/comments";
    }

    @RequestMapping(value = {"/{userName}/photo/album"}, method = RequestMethod.GET)
    public String photoAlbum(@PathVariable String userName) {
        request.setAttribute("adminModular", "photoAlbum");
        return "/admin/photo/album";
    }

    @RequestMapping(value = {"/{userName}/photo/album/{operation}"}, method = RequestMethod.GET)
    public String photoAlbumCreate(@PathVariable String userName, @PathVariable String operation) {
        String photoAlbumTitle = null;
        if ("view".equals(operation)) {
            photoAlbumTitle = "相册详情";
        } else if ("edit".equals(operation)) {
            photoAlbumTitle = "编辑相册";
        } else if ("create".equals(operation)) {
            photoAlbumTitle = "创建相册";
        }
        request.setAttribute("adminModular", "photoAlbum");
        request.setAttribute("photoAlbumOperation", operation);
        request.setAttribute("photoAlbumTitle", photoAlbumTitle);
        return "/admin/photo/album_create";
    }
}
