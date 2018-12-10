package org.xiao.message.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

public interface BaseMongoRepository<T, ID extends Serializable> {

    T get(ID id);

    T save(T param);

    int delete(ID id);

    int deleteAll(List<String> ids);

    List<T> findAll();

    List<T> findByCondition(Query query);

    void insertAll(List<T> paramList);
   
    void insert(T param);
    /***
     * 
     * @param query
     * @param request
     * @return
     */
    Page<T> queryByConditionPage(Query query, PageRequest request);
    /***
     * 
     * @param query
     * @param request
     * @param sort
     * @return
     */
    Page<T> queryByConditionPage(Query query, PageRequest request, Sort sort);
    /***
     * 
     * @param query
     * @param pageable
     * @param sort
     * @param c
     * @return
     */
    <C> Page<C> queryByConditionPage(Query query, PageRequest pageable, Sort sort, Class<C> c);

}
