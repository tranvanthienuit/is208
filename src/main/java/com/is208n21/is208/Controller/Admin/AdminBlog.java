package com.is208n21.is208.Controller.Admin;


import com.is208n21.is208.Entity.Model.Blog;
import com.is208n21.is208.Entity.Model.User;
import com.is208n21.is208.Sercurity.userDetail;
import com.is208n21.is208.Service.BlogService;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RestController
@Transactional
public class AdminBlog {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;


    @PostMapping("/admin/them-blog")
    public ResponseEntity<?> saveBlog(@RequestBody Blog blog) throws Exception {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        blog.setUser(user);
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        blog.setDayAdd(date);
        blogService.saveBlog(blog);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @DeleteMapping("/admin/xoa-blog/{blogId}")
    public ResponseEntity<?> deleteBlog(@RequestBody @PathVariable(name = "blogId") String blogId) {
        blogService.findAndDeleteBlog(blogId);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }


    @PostMapping("/admin/sua-blog")
    public ResponseEntity<?> updateBlog(@RequestBody Blog blog) {
        Blog blog1 = blogService.findBlog(blog.getBlogId());
        if (blog.getContent() != null)
            blog1.setContent(blog.getContent());
        if (blog.getContext() != null)
            blog1.setContext(blog.getContext());
        if (blog.getTitle() != null)
            blog1.setTitle(blog.getTitle());
        if (blog.getImage() != null)
            blog1.setImage(blog.getImage());
        blogService.saveBlog(blog1);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

}
