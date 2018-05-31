package cn.eakay.springboot.repository;

import cn.eakay.springboot.client.Eakay;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by magic~ on 2018/5/17.
 */
@Component
public class EsEakayRepository implements ElasticsearchRepository<Eakay,Integer>
{
    @Override
    public <S extends Eakay> S index(S s)
    {
        return null;
    }

    @Override
    public Iterable<Eakay> search(QueryBuilder queryBuilder)
    {
        return null;
    }

    @Override
    public Page<Eakay> search(QueryBuilder queryBuilder, Pageable pageable)
    {
        return null;
    }

    @Override
    public Page<Eakay> search(SearchQuery searchQuery)
    {
        return null;
    }

    @Override
    public Page<Eakay> searchSimilar(Eakay eakay, String[] strings, Pageable pageable)
    {
        return null;
    }

    @Override
    public void refresh()
    {

    }

    @Override
    public Class<Eakay> getEntityClass()
    {
        return null;
    }

    @Override
    public Iterable<Eakay> findAll(Sort sort)
    {
        return null;
    }

    @Override
    public Page<Eakay> findAll(Pageable pageable)
    {
        return null;
    }

    @Override
    public <S extends Eakay> S save(S entity)
    {
        return null;
    }

    @Override
    public <S extends Eakay> Iterable<S> saveAll(Iterable<S> entities)
    {
        return null;
    }

    @Override
    public Optional<Eakay> findById(Integer integer)
    {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer)
    {
        return false;
    }

    @Override
    public Iterable<Eakay> findAll()
    {
        return null;
    }

    @Override
    public Iterable<Eakay> findAllById(Iterable<Integer> integers)
    {
        return null;
    }

    @Override
    public long count()
    {
        return 0;
    }

    @Override
    public void deleteById(Integer integer)
    {

    }

    @Override
    public void delete(Eakay entity)
    {

    }

    @Override
    public void deleteAll(Iterable<? extends Eakay> entities)
    {

    }

    @Override
    public void deleteAll()
    {

    }
}
