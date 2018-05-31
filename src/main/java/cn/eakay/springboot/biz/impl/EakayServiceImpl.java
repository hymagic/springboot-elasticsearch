package cn.eakay.springboot.biz.impl;

import cn.eakay.springboot.biz.EakayService;
import cn.eakay.springboot.client.Eakay;
import cn.eakay.springboot.repository.EsEakayRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by magic~ on 2018/5/17.
 */
@Service
public class EakayServiceImpl implements EakayService
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String EAKAY_INDEX_NAME = "eakay_index";
    private static final String EAKAY_INDEX_TYPE = "eakay_type";


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private EsEakayRepository esEakayRepository;

    /**
     * 批量建索引
     *
     * @param eakays
     * @return
     */
    public long bulkIndex(List<Eakay> eakays)
    {

        int counter = 0;
        try
        {
            if (!elasticsearchTemplate.indexExists(Eakay.class))
            {
                elasticsearchTemplate.createIndex(Eakay.class);
            }

            List<IndexQuery> queries = new ArrayList<>();
            for (Eakay eakay : eakays)
            {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(eakay.getId().toString());
                indexQuery.setObject(eakay);
                indexQuery.setIndexName(EAKAY_INDEX_NAME);
                indexQuery.setType(EAKAY_INDEX_TYPE);

                //上面的那几步也可以使用IndexQueryBuilder来构建
                //IndexQuery index = new IndexQueryBuilder().withId(person.getId() + "").withObject(person).build();

                queries.add(indexQuery);
                if (counter % 500 == 0)
                {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    logger.info("bulkIndex counter : ()", counter);
                }
                counter++;
            }
            //不足批的索引最后不要忘记提交
            if (queries.size() > 0)
            {
                elasticsearchTemplate.bulkIndex(queries);
            }
            elasticsearchTemplate.refresh(EAKAY_INDEX_NAME);
            logger.info("bulkIndex completed.");
        } catch (Exception e)
        {
            logger.error("IndexerService.bulkIndex error", e);
            //logger.error("IndexerService.bulkIndex e{};" , e.getMessage());
            throw e;
        }

        return -1;

    }

    /**
     * 批量修改
     *
     * @param eakays
     */
    public void bulkUpdate(List<Eakay> eakays)
    {
        try
        {
            int counter = 0;
            List<UpdateQuery> queries = new ArrayList<>();
            for (Eakay eakay : eakays)
            {


                IndexRequest indexRequest = new IndexRequest();
                Map<String, String> sourceMap = new HashMap<>();
                sourceMap.put("name", eakay.getName());
                sourceMap.put("description", eakay.getDescription());
                indexRequest.source(sourceMap);
                UpdateQuery updateQuery = new UpdateQueryBuilder().withId(String.valueOf(eakay.getId()))
                        .withClass(Eakay.class).withIndexRequest(indexRequest).withIndexName(EAKAY_INDEX_NAME).withType(EAKAY_INDEX_TYPE).build();

                queries.add(updateQuery);

                if (counter % 500 == 0)
                {
                    elasticsearchTemplate.bulkUpdate(queries);
                    queries.clear();
                    logger.info("bulkUpdate counter :{}", counter);
                }
                counter++;
            }

            //不足批的索引最后不要忘记提交
            if (queries.size() > 0)
            {
                elasticsearchTemplate.bulkUpdate(queries);
            }
            elasticsearchTemplate.refresh(EAKAY_INDEX_NAME);
            logger.info("bulkUpdate completed.");
        } catch (Exception e)
        {
            logger.error("EakayService.bulkUpdate error", e);
            //logger.error("IndexerService.bulkIndex e{};" , e.getMessage());
            throw e;
        }


    }

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName)
    {
        return elasticsearchTemplate.deleteIndex(indexName);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Eakay> searchAll()
    {

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        return elasticsearchTemplate.queryForList(searchQuery, Eakay.class);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Eakay searchById(String id)
    {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id);

        return elasticsearchTemplate.queryForObject(getQuery, Eakay.class);

    }

    /**
     * 多id查询
     *
     * @param ids
     * @return
     */
    public LinkedList<Eakay> searchByMultiId(List<String> ids)
    {
        SearchQuery query = new NativeSearchQueryBuilder().withIds(ids).build();
        return elasticsearchTemplate.multiGet(query, Eakay.class);
    }

    /**
     * score排序
     *
     * @return
     */
    public Page<Eakay> searchSortResults()
    {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withSort(new FieldSortBuilder("score").order(SortOrder.ASC)).build();
        // when
        return elasticsearchTemplate.queryForPage(searchQuery, Eakay.class);
    }

    public Map getMapping()
    {
        return elasticsearchTemplate.getMapping(Eakay.class);
    }

    public Map getSetting()
    {
        return elasticsearchTemplate.getSetting(Eakay.class);
    }


}

