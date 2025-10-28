package com.example.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Job;
import com.example.mapper.JobMapper;
import com.example.serivce.JobService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位服务实现类
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Override
    public List<Job> searchJobs(String keyword) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("company_name", keyword)
                       .or().like("position_name", keyword)
                       .or().like("requirements", keyword)
                       .or().like("location", keyword);
        }
        return baseMapper.selectList(queryWrapper);
    }
}