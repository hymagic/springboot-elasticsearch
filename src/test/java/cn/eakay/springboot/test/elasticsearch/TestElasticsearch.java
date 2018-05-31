package cn.eakay.springboot.test.elasticsearch;

import cn.eakay.springboot.biz.EakayService;
import cn.eakay.springboot.client.Eakay;
import cn.eakay.springboot.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by magic~ on 2018/5/19.
 */
public class TestElasticsearch extends BaseTest
{

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EakayService eakayService;

    @Test
     public void bulkIndex()
     {
         Eakay eakay=new Eakay();
         eakay.setId(Long.valueOf(2));
         eakay.setName("中国人2");
         eakay.setDescription("我爱中国人民共和国2");
         eakay.setScore(1);

         List<Eakay> list=new ArrayList<>();
         list.add(eakay);

         eakayService.bulkIndex(list);

     }

     @Test
    public void deleteIndex()
    {
          eakayService.deleteIndex("eakay_index");
    }

    @Test
    public void bulkUpdate()
    {
        Eakay eakay=new Eakay();
        eakay.setId(Long.valueOf(1));
        eakay.setName("中国人11");
        eakay.setDescription("我爱中国人民共和国111");
        eakay.setScore(2);

        Eakay eakay2=new Eakay();
        eakay2.setId(Long.valueOf(2));
        eakay2.setName("中国人22");
        eakay2.setDescription("我爱中国人民共和国222");
        eakay2.setScore(3);

        List<Eakay> list=new ArrayList<>();
        list.add(eakay);
        list.add(eakay2);

        eakayService.bulkUpdate(list);
    }
    @Test
    public void searchAll()
    {
        List<Eakay> lists=eakayService.searchAll();
        for (Eakay eakay:lists)
        {
            logger.info(eakay.getId()+"---"+eakay.getName());
        }
    }

    @Test
    public void searchById()
    {
        Eakay eakay=eakayService.searchById("1");
        logger.info(eakay.getId()+"---"+eakay.getName());
    }

    @Test
    public void searchByMultiId()
    {

        List<String> ids=new ArrayList<>();
        ids.add("1");
        ids.add("2");

     LinkedList<Eakay> lists= eakayService.searchByMultiId(ids);

     for (Eakay ekay:lists)
     {
         logger.info(ekay.getId()+"---"+ekay.getName());
     }



    }

    @Test
    public void searchSortResult()
    {
        Page<Eakay>  page=eakayService.searchSortResults();
        logger.info("page.TotalElements:{}",page.getTotalElements());

        for (Eakay eakay:page.getContent())
        {
            logger.info(eakay.getId()+"---"+eakay.getName()+"---"+eakay.getScore());
        }
    }

    @Test
    public void getMappingAndSetting()
    {

        Map mappingMap= eakayService.getMapping();
        logger.info(mappingMap.toString());

        Map<String,String> settingMap= eakayService.getSetting();
        logger.info(settingMap.toString());


    }



}
