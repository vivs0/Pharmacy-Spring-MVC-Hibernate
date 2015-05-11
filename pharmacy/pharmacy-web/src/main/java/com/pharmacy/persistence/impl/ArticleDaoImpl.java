/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharmacy.persistence.impl;

import com.pharmacy.article.Article;
import com.pharmacy.persistence.api.ArticleDao;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexandr
 */
@Repository
public class ArticleDaoImpl extends AbstractJpaDAO<Article> implements ArticleDao {

    public ArticleDaoImpl() {
        super(Article.class);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Article> loadBestDiscountedArticles() {
        Session session = getSessionFactory().openSession();
        Query query = session.getNamedQuery("Article.findBestDicount");
        List<Article> articles = query.list();
        return articles;

    }

}
