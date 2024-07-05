package com.gigglegazette.article_service.util;

import com.gigglegazette.article_service.model.Article;

public class ArticleResponse {
    private Article article;
    private Object articleAuthor;

    public ArticleResponse(Article article, Object articleAuthor) {
        this.article = article;
        this.articleAuthor = articleAuthor;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Object getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(Object articleAuthor) {
        this.articleAuthor = articleAuthor;
    }
}
