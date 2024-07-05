package com.gigglegazette.article_service.util;

import com.gigglegazette.article_service.model.Comment;

public class CommentResponse {
    private Comment comment;
    private Object commentAuthor;

    public CommentResponse(Comment comment, Object commentAuthor) {
        this.comment = comment;
        this.commentAuthor = commentAuthor;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Object getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(Object commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
