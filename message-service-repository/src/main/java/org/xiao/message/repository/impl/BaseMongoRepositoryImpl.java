package org.xiao.message.repository.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.WriteResult;
import org.xiao.message.repository.BaseMongoRepository;
/***
 * 
 * @author zycen
 *
 * @param <T>
 * @param <ID>
 */
public class BaseMongoRepositoryImpl<T, ID extends Serializable> implements BaseMongoRepository<T, ID> {

    @Autowired
    MongoTemplate mongoTemplate;

    Class<T> repositoryClass = null;

    String collectionName;

    public BaseMongoRepositoryImpl(Class<T> t) {
        this.repositoryClass = t;
        Document document = t.getAnnotation(Document.class);
        this.collectionName = document.collection();

    }

    @Override
    public T save(T param) {
        mongoTemplate.save(param);
        return param;
    }

    @Override
    public int deleteAll(List<String> ids) {
        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("_id").in(ids)), repositoryClass);
        return result.getN();
    }

    @Override
    public int delete(Serializable id) {
        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), repositoryClass);
        return result.getN();
    }

    @Override
    public T get(Serializable id) {
        return mongoTemplate.findById(id, repositoryClass);
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(repositoryClass);
    }

    @Override
    public List<T> findByCondition(Query query) {
        return mongoTemplate.find(query, repositoryClass);
    }

    @Override
    public void insertAll(List<T> paramList) {
        mongoTemplate.insertAll(paramList);
    }

    @Override
    public void insert(T param) {
        mongoTemplate.insert(param);
    }

    @Override
    public Page<T> queryByConditionPage(Query query, PageRequest pageable) {
        Long count = mongoTemplate.count(query, repositoryClass);
        query.limit(pageable.getPageSize());
        query.skip((pageable.getPageNumber() - 1) * pageable.getPageSize());
        List<T> list = mongoTemplate.find(query, repositoryClass);
        return new PageImpl<T>(list, new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize()) , count);
    }

    @Override
    public Page<T> queryByConditionPage(Query query, PageRequest pageable, Sort sort) {
        Long count = mongoTemplate.count(query, repositoryClass);
        query.limit(pageable.getPageSize());
        query.skip((pageable.getPageNumber() - 1) * pageable.getPageSize());
        query.with(sort);
        List<T> list = mongoTemplate.find(query, repositoryClass);
        return new PageImpl<T>(list, new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize()) , count);
    }

    @Override
    public <C> Page<C> queryByConditionPage(Query query, PageRequest pageable, Sort sort, Class<C> c) {
        Long count = mongoTemplate.count(query, repositoryClass);
        query.limit(pageable.getPageSize());
        query.skip((pageable.getPageNumber() - 1) * pageable.getPageSize());
        query.with(sort);
        List<C> list = mongoTemplate.find(query, c, collectionName);
      
        return new PageImpl<C>(list, new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize()) , count);
    }
}
