package com.is208n21.is208.Controller.User;



import com.is208n21.is208.Entity.Model.Book;
import com.is208n21.is208.Entity.Model.Comment;
import com.is208n21.is208.Entity.Model.User;
import com.is208n21.is208.Sercurity.userDetail;
import com.is208n21.is208.Service.BookService;
import com.is208n21.is208.Service.CommentService;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;

@RestController
@Transactional
public class UserComment {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @PostMapping("/user/luu-comment/{bookId}")
    public ResponseEntity<?> saveComment(@PathVariable(name = "bookId") String bookId, @RequestBody Comment comment) {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        comment.setUser(user);
        comment.setBook(bookService.findBookByBookId(bookId));
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        comment.setDayAdd(date);
        Book book = bookService.findBookByBookId(bookId);
        book.setCmt(true);
        bookService.saveBook(book);
        commentService.saveComment(comment);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @PostMapping(value = "/user/xoa-comment")
    public ResponseEntity<?> deleteComment(@RequestBody Map<String, Object> comment) {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(user1.getUserId());
        commentService.deleteUserAndComment(user.getUserId(), comment.get("commentId").toString());
        Book book = bookService.findBookByBookId(comment.get("bookId").toString());
        book.setCmt(false);
        bookService.saveBook(book);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @PostMapping("/user/sua-comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") String commentId, @RequestBody Map<String, Object> content) {
        commentService.updateComment(commentId, content.get("content").toString());
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @GetMapping("/book/comment/{bookId}")
    public ResponseEntity<?> commentBook(@PathVariable(name = "bookId") String bookId) {
        return new ResponseEntity<>(commentService.findAllByBookId(bookId), HttpStatus.OK);
    }
}
