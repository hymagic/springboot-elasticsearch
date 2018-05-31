package cn.eakay.springboot.client;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "eakay_index",type = "eakay_type", shards = 3,replicas = 2)
public class Eakay implements Serializable
{


    private static final long serialVersionUID = -1L;

    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    @Field
    private String name;

    /**
     * 描述
     */
    @Field
    private String description;

    /**
     * 评分
     */
    @Field
    private Integer score;


    private GeoPoint location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public GeoPoint getLocation()
    {
        return location;
    }

    public void setLocation(GeoPoint location)
    {
        this.location = location;
    }
}