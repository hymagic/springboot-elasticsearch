package cn.eakay.springboot.biz;

import cn.eakay.springboot.client.Eakay;
import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by magic~ on 2018/5/17.
 */
public interface EakayService
{

    public long bulkIndex(List<Eakay> eakays);
    public boolean deleteIndex(String indexName);
    public List<Eakay>  searchAll();
    public Eakay searchById(String id);
    public LinkedList<Eakay> searchByMultiId(List<String> ids);
    public void  bulkUpdate(List<Eakay> eakays);
    public Page<Eakay> searchSortResults();
    public Map getMapping();
    public Map getSetting();


}
