-- 创建数据库
CREATE DATABASE IF NOT EXISTS it_job_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE it_job_system;

-- 创建岗位表
CREATE TABLE IF NOT EXISTS job (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    company_name VARCHAR(100) NOT NULL COMMENT '企业名称',
    position_name VARCHAR(100) NOT NULL COMMENT '岗位名称',
    salary VARCHAR(50) NOT NULL COMMENT '薪资',
    requirements TEXT COMMENT '具体要求',
    benefits TEXT COMMENT '福利待遇',
    location VARCHAR(100) COMMENT '工作地点',
    post_date VARCHAR(20) COMMENT '发布日期',
    contact_info VARCHAR(200) COMMENT '联系方式',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IT岗位表';

-- 插入示例数据
INSERT INTO job (company_name, position_name, salary, requirements, benefits, location, post_date, contact_info)
VALUES
('科技有限公司', 'Java开发工程师', '15K-25K', '熟悉Spring Boot框架，掌握MySQL数据库', '五险一金，年终奖', '北京', '2024-01-15', 'hr@example.com'),
('互联网科技公司', '前端开发工程师', '12K-20K', '精通Vue.js，熟悉React', '弹性工作，带薪年假', '上海', '2024-01-14', 'recruit@example.com'),
('软件开发公司', '测试工程师', '10K-18K', '掌握自动化测试，熟悉接口测试', '周末双休，定期团建', '深圳', '2024-01-13', 'jobs@example.com');