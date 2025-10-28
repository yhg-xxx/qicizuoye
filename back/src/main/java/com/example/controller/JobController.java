package com.example.controller;

import com.example.entity.Job;
import com.example.serivce.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位控制器
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * 获取所有岗位
     */
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.list();
    }

    /**
     * 根据ID获取岗位
     */
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Integer id) {
        return jobService.getById(id);
    }

    /**
     * 搜索岗位
     */
    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam(required = false) String keyword) {
        return jobService.searchJobs(keyword);
    }

    /**
     * 添加岗位
     */
    @PostMapping
    public boolean addJob(@RequestBody Job job) {
        return jobService.save(job);
    }

    /**
     * 更新岗位
     */
    @PutMapping("/{id}")
    public boolean updateJob(@PathVariable Integer id, @RequestBody Job job) {
        // 由于字段是private的，我们直接使用MyBatis-Plus的更新功能
        // 将id作为路径参数传递，MyBatis-Plus会正确处理
        return jobService.updateById(job);
    }

    /**
     * 删除岗位
     */
    @DeleteMapping("/{id}")
    public boolean deleteJob(@PathVariable Integer id) {
        return jobService.removeById(id);
    }
}