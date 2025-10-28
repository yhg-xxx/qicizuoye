package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * IT岗位实体类
 */
@Data
@TableName("job")
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id; // 主键ID

    private String companyName; // 企业名称

    private String positionName; // 岗位名称

    private String salary; // 薪资

    private String requirements; // 具体要求

    private String benefits; // 福利待遇

    private String location; // 工作地点

    private String postDate; // 发布日期

    private String contactInfo; // 联系方式
    
    // 数据库表中的时间字段
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}