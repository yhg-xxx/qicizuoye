package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Job;
import org.apache.ibatis.annotations.Mapper;

/**
 * 岗位数据访问层
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    // BaseMapper已经提供了常用的CRUD方法
}