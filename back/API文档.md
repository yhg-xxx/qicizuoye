# IT岗位求职记录系统 API 接口文档

## 1. 项目概述

本系统为IT岗位求职记录系统，用于记录适合学生的IT岗位信息，核心功能包括岗位的增删改查操作。

## 2. 接口概览

| 接口路径 | 方法 | 功能描述 |
|---------|-----|---------|
| `/api/jobs` | GET | 获取所有岗位列表 |
| `/api/jobs/{id}` | GET | 根据ID获取岗位详情 |
| `/api/jobs/search` | GET | 根据关键词搜索岗位 |
| `/api/jobs` | POST | 添加新岗位 |
| `/api/jobs/{id}` | PUT | 更新岗位信息 |
| `/api/jobs/{id}` | DELETE | 删除岗位 |

## 3. 详细接口说明

### 3.1 获取所有岗位列表

**请求信息**
- **URL**: `/api/jobs`
- **方法**: `GET`
- **参数**: 无

**响应信息**
- **状态码**: 200 OK
- **响应格式**: JSON数组

**示例响应**:
```json
[
  {
    "id": 1,
    "companyName": "腾讯科技",
    "positionName": "前端开发工程师",
    "salary": "15K-25K",
    "requirements": "熟悉Vue.js, React等前端框架",
    "benefits": "五险一金，年终奖",
    "location": "深圳",
    "postDate": "2024-01-15",
    "contactInfo": "hr@tencent.com",
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  }
]
```

### 3.2 根据ID获取岗位详情

**请求信息**
- **URL**: `/api/jobs/{id}`
- **方法**: `GET`
- **参数**:
  - `id`: 岗位ID (路径参数)

**响应信息**
- **状态码**: 200 OK
- **响应格式**: JSON对象

**示例响应**:
```json
{
  "id": 1,
  "companyName": "腾讯科技",
  "positionName": "前端开发工程师",
  "salary": "15K-25K",
  "requirements": "熟悉Vue.js, React等前端框架",
  "benefits": "五险一金，年终奖",
  "location": "深圳",
  "postDate": "2024-01-15",
  "contactInfo": "hr@tencent.com",
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T10:00:00"
}
```

### 3.3 根据关键词搜索岗位

**请求信息**
- **URL**: `/api/jobs/search`
- **方法**: `GET`
- **参数**:
  - `keyword`: 搜索关键词 (可选)

**响应信息**
- **状态码**: 200 OK
- **响应格式**: JSON数组

**示例响应**:
```json
[
  {
    "id": 1,
    "companyName": "腾讯科技",
    "positionName": "前端开发工程师",
    "salary": "15K-25K",
    "requirements": "熟悉Vue.js, React等前端框架",
    "benefits": "五险一金，年终奖",
    "location": "深圳",
    "postDate": "2024-01-15",
    "contactInfo": "hr@tencent.com",
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  }
]
```

### 3.4 添加新岗位

**请求信息**
- **URL**: `/api/jobs`
- **方法**: `POST`
- **参数**: JSON对象 (请求体)

**请求体示例**:
```json
{
  "companyName": "阿里巴巴",
  "positionName": "后端开发工程师",
  "salary": "20K-30K",
  "requirements": "熟悉Java, Spring Boot等技术",
  "benefits": "五险一金，股票期权",
  "location": "杭州",
  "postDate": "2024-01-16",
  "contactInfo": "hr@alibaba.com"
}
```

**响应信息**
- **状态码**: 200 OK
- **响应格式**: boolean
- **成功**: `true`
- **失败**: `false`

### 3.5 更新岗位信息

**请求信息**
- **URL**: `/api/jobs/{id}`
- **方法**: `PUT`
- **参数**:
  - `id`: 岗位ID (路径参数)
  - JSON对象 (请求体)

**请求体示例**:
```json
{
  "id": 1,
  "companyName": "腾讯科技",
  "positionName": "高级前端开发工程师",
  "salary": "25K-35K",
  "requirements": "熟悉Vue.js, React等前端框架，3年以上经验",
  "benefits": "五险一金，年终奖，股票期权",
  "location": "深圳",
  "postDate": "2024-01-15",
  "contactInfo": "hr@tencent.com"
}
```

**响应信息**
- **状态码**: 200 OK
- **响应格式**: boolean
- **成功**: `true`
- **失败**: `false`

### 3.6 删除岗位

**请求信息**
- **URL**: `/api/jobs/{id}`
- **方法**: `DELETE`
- **参数**:
  - `id`: 岗位ID (路径参数)

**响应信息**
- **状态码**: 200 OK
- **响应格式**: boolean
- **成功**: `true`
- **失败**: `false`

## 4. 数据结构定义

### 4.1 Job实体

| 字段名 | 数据类型 | 描述 |
|-------|---------|-----|
| `id` | Integer | 主键ID |
| `companyName` | String | 企业名称 |
| `positionName` | String | 岗位名称 |
| `salary` | String | 薪资 |
| `requirements` | String | 具体要求 |
| `benefits` | String | 福利待遇 |
| `location` | String | 工作地点 |
| `postDate` | String | 发布日期 |
| `contactInfo` | String | 联系方式 |
| `createdAt` | LocalDateTime | 创建时间 |
| `updatedAt` | LocalDateTime | 更新时间 |

## 5. 跨域配置

系统已配置跨域支持，允许前端应用访问后端API。

## 6. 错误处理

- 接口返回boolean类型时，`false`表示操作失败
- 发生异常时，系统会返回相应的错误状态码和错误信息

## 7. 版本信息

- 版本: 1.0
- 最后更新时间: 2024-01-16