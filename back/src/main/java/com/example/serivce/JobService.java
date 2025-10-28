package com.example.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Job;

import java.util.List;

/**
 * 岗位服务接口
 */
public interface JobService extends IService<Job> {
    // IService已经提供了常用的CRUD方法
    
    // 可以添加自定义方法
    List<Job> searchJobs(String keyword); // 根据关键词搜索岗位
}