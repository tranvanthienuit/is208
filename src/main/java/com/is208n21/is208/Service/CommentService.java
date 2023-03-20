package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.Model.Comment;
import com.is208n21.is208.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteUserAndComment(String userId, String commentId) {
        Comment comment = commentRepository.findByUserIdAndCommentId(userId, commentId);
        commentRepository.delete(comment);
    }

    public void updateComment(String commentId, String content) {
        commentRepository.updateComment(commentId, content);
    }

    public List<Comment> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
