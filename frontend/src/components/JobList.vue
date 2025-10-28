<template>
  <div class="container">
    <!-- 开发者信息 -->
    <div class="developer-info">
      <el-tag type="info" class="big-tag">班级：信2305-3 学号：20234055 姓名：张同宁</el-tag>
    </div>
    
    <el-card class="mb-4">
      <template #header>
        <div class="card-header">
          <span class="title">IT岗位列表</span>
          <el-button type="primary" @click="openAddDialog">
            <el-icon><Plus /></el-icon>
            添加岗位
          </el-button>
        </div>
      </template>
      
      <!-- 搜索和筛选区域 -->
      <div class="search-filter-section">
        <div class="search-box">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索企业名、岗位名、要求或地点"
            prefix-icon="Search"
            clearable
            @clear="handleSearch"
          >
            <template #append>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>
        
        <!-- 筛选条件 -->
        <div class="filter-box">
          <el-select
            v-model="filters.positionName"
            placeholder="选择岗位名称"
            clearable
            filterable
            style="width: 200px; margin-right: 10px;"
          >
            <el-option
              v-for="position in positionOptions"
              :key="position"
              :label="position"
              :value="position"
            />
          </el-select>
          
          <!-- 薪资区间筛选 -->
          <div class="salary-filter-container" style="margin-right: 10px; display: flex; align-items: center;">
            <span style="margin-right: 10px;">薪资区间：</span>
            <el-input-number
              v-model="salaryMin"
              :min="0"
              :max="100"
              style="width: 100px; margin-right: 5px;"
              placeholder="最小"
            />
            <span>K -</span>
            <el-input-number
              v-model="salaryMax"
              :min="0"
              :max="100"
              style="width: 100px; margin-left: 5px; margin-right: 10px;"
              placeholder="最大"
            />
            <span>K</span>
          </div>
          
          <el-select
            v-model="filters.location"
            placeholder="选择工作地点"
            clearable
            filterable
            style="width: 150px; margin-right: 10px;"
          >
            <el-option
              v-for="loc in locationOptions"
              :key="loc"
              :label="loc"
              :value="loc"
            />
          </el-select>
          
          <el-button @click="applyFilters">应用筛选</el-button>
          <el-button @click="resetFilters">重置筛选</el-button>
        </div>
      </div>
      
      <el-table 
        :data="paginatedJobs" 
        style="width: 100%" 
        border
        @row-click="openDetailDialog"
        row-class-name="clickable-row"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="companyName" label="企业名称">
          <template #default="scope">
            <span v-html="highlightText(scope.row.companyName)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="positionName" label="岗位名称">
          <template #default="scope">
            <span v-html="highlightText(scope.row.positionName)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="salary" label="薪资" width="120" />
        <el-table-column prop="location" label="工作地点" width="120">
          <template #default="scope">
            <span v-html="highlightText(scope.row.location)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="postDate" label="发布日期" width="120" />
        <el-table-column prop="requirements" label="岗位要求" show-overflow-tooltip width="200">
          <template #default="scope">
            <span v-html="highlightText(scope.row.requirements)"></span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click.stop="openEditDialog(scope.row)">
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button type="danger" link @click.stop="handleDelete(scope.row)">
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页组件 -->
      <div class="pagination-container" v-if="total > 0">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 15, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="50%"
    >
      <el-form
        ref="jobFormRef"
        :model="jobForm"
        label-width="100px"
        :rules="rules"
      >
        <el-form-item label="企业名称" prop="companyName">
          <el-input v-model="jobForm.companyName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="岗位名称" prop="positionName">
          <el-input v-model="jobForm.positionName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="薪资" prop="salary">
          <el-input v-model="jobForm.salary" placeholder="例如：15K-25K" />
        </el-form-item>
        <el-form-item label="工作地点" prop="location">
          <el-input v-model="jobForm.location" placeholder="请输入工作地点" />
        </el-form-item>
        <el-form-item label="发布日期" prop="postDate">
          <el-input v-model="jobForm.postDate" placeholder="例如：2024-01-15" />
        </el-form-item>
        <el-form-item label="岗位要求" prop="requirements">
          <el-input
            v-model="jobForm.requirements"
            type="textarea"
            :rows="4"
            placeholder="请输入岗位要求"
          />
        </el-form-item>
        <el-form-item label="福利待遇" prop="benefits">
          <el-input
            v-model="jobForm.benefits"
            type="textarea"
            :rows="3"
            placeholder="请输入福利待遇"
          />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactInfo">
          <el-input v-model="jobForm.contactInfo" placeholder="请输入联系方式" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEditMode ? '更新' : '提交' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailVisible"
      title="岗位详情"
      width="60%"
    >
      <div class="detail-content">
        <div class="detail-header">
          <h3>{{ selectedJob.companyName }} - {{ selectedJob.positionName }}</h3>
          <div class="detail-salary">{{ selectedJob.salary }}</div>
        </div>
        
        <el-divider />
        
        <div class="detail-section">
          <div class="detail-item">
            <span class="detail-label">工作地点：</span>
            <span>{{ selectedJob.location }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">发布日期：</span>
            <span>{{ selectedJob.postDate }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">联系方式：</span>
            <span>{{ selectedJob.contactInfo }}</span>
          </div>
        </div>
        
        <el-divider />
        
        <div class="detail-section">
          <h4 class="detail-title">岗位要求</h4>
          <p class="detail-text" v-html="highlightText(selectedJob.requirements)"></p>
        </div>
        
        <el-divider />
        
        <div class="detail-section">
          <h4 class="detail-title">福利待遇</h4>
          <p class="detail-text" v-html="highlightText(selectedJob.benefits)"></p>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'

export default {
  name: 'JobList',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      jobList: [],
      originalJobList: [], // 存储原始数据用于筛选
      searchKeyword: '',
      dialogVisible: false,
      detailVisible: false,
      dialogTitle: '',
      isEditMode: false,
      loading: false,
      // 筛选条件
      filters: {
        positionName: '',
        location: ''
      },
      // 薪资范围
      salaryMin: 0,
      salaryMax: 50,
      // 下拉选项
      positionOptions: [],
      locationOptions: [],
      // 分页相关
      currentPage: 1,
      pageSize: 10,
      total: 0,
      // 选中的岗位用于详情
      selectedJob: {},
      // 表单数据
      jobForm: {
        id: '',
        companyName: '',
        positionName: '',
        salary: '',
        location: '',
        postDate: '',
        requirements: '',
        benefits: '',
        contactInfo: ''
      },
      // 表单验证规则
      rules: {
        companyName: [
          { required: true, message: '请输入企业名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        positionName: [
          { required: true, message: '请输入岗位名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        salary: [
          { required: true, message: '请输入薪资', trigger: 'blur' }
        ],
        requirements: [
          { required: true, message: '请输入岗位要求', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadJobs()
  },
  computed: {
    // 计算分页后的数据
    paginatedJobs() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      return this.jobList.slice(start, end)
    }
  },
  methods: {
    // 加载岗位列表
    loadJobs() {
      axios.get('/api/jobs')
        .then(res => {
          this.jobList = res.data
          this.originalJobList = res.data // 保存原始数据
          this.total = res.data.length
          this.updateFilterOptions() // 更新筛选选项
          this.resetFilters() // 加载数据时重置筛选条件
        })
        .catch(error => {
          console.error('获取岗位列表失败:', error)
          ElMessage.error('获取岗位列表失败')
        })
    },
    
    // 更新筛选下拉选项
    updateFilterOptions() {
      // 提取所有唯一的岗位名称
      const positions = [...new Set(this.originalJobList.map(job => job.positionName))]
      this.positionOptions = positions.sort()
      
      // 提取所有唯一的工作地点
      const locations = [...new Set(this.originalJobList.map(job => job.location))]
      this.locationOptions = locations.sort()
    },
    

    
    // 搜索岗位
    handleSearch() {
      if (this.searchKeyword) {
        axios.get('/api/jobs/search', { params: { keyword: this.searchKeyword } })
          .then(res => {
            this.originalJobList = res.data // 保存搜索结果用于筛选
            this.applyFilters() // 应用当前筛选条件
          })
          .catch(error => {
            console.error('搜索岗位失败:', error)
            ElMessage.error('搜索岗位失败')
          })
      } else {
        this.loadJobs() // 如果搜索关键词为空，加载所有数据
      }
    },
    
    // 应用筛选条件时确保数值有效性
    applyFilters() {
      // 确保数值有效性
      const min = Math.max(0, this.salaryMin || 0)
      const max = Math.max(min, this.salaryMax || 50) // 确保最大值不小于最小值

      if (max < min) {
        this.salaryMax = min
        ElMessage.warning('最大值不能小于最小值，已自动调整')
      }

      let filtered = [...this.originalJobList]
      
      // 按岗位名称筛选（精确匹配）
      if (this.filters.positionName) {
        filtered = filtered.filter(job => job.positionName === this.filters.positionName)
      }

// 按薪资区间筛选 - 修复后的逻辑
      if (min > 0 || max < 50) { // 只在用户设置了筛选条件时进行薪资筛选
        filtered = filtered.filter(job => {
          if (!job.salary) return false

          const salaryText = job.salary.toString().toUpperCase()

          // 多种薪资格式的匹配
          let minSalary = 0
          let maxSalary = 0

          // 匹配范围格式：15K-25K, 15k-25k, 15-25K
          const rangeMatch = salaryText.match(/([\d.]+)\s*K?\s*[-~至]\s*([\d.]+)\s*K?/)
          if (rangeMatch) {
            minSalary = parseFloat(rangeMatch[1])
            maxSalary = parseFloat(rangeMatch[2])
          }
          // 匹配单一薪资格式：15K, 15k
          else {
            const singleMatch = salaryText.match(/([\d.]+)\s*K?/)
            if (singleMatch) {
              minSalary = parseFloat(singleMatch[1])
              maxSalary = minSalary // 单一薪资时，最小最大值相同
            }
          }

          // 如果无法解析薪资，跳过该岗位的薪资筛选
          if (minSalary === 0 && maxSalary === 0) {
            return true
          }

          // 判断岗位薪资范围是否与筛选范围有重叠
          // 岗位薪资范围 [minSalary, maxSalary] 与筛选范围 [min, max] 有重叠的条件：
          // 岗位最低薪资 <= 筛选最高薪资 且 岗位最高薪资 >= 筛选最低薪资
          return minSalary <= max && maxSalary >= min
        })
      }

      // 按工作地点筛选（精确匹配）
      if (this.filters.location) {
        filtered = filtered.filter(job => job.location === this.filters.location)
      }

      this.jobList = filtered
      this.total = filtered.length
      this.currentPage = 1 // 重置到第一页
    },
    // 重置筛选条件
    resetFilters() {
      this.filters = {
        positionName: '',
        location: ''
      }
      this.salaryMin = 0
      this.salaryMax = 50
      this.searchKeyword = '' // 同时清空搜索关键词
      this.jobList = [...this.originalJobList]
      this.total = this.originalJobList.length
      this.currentPage = 1
    },
    
    // 打开详情对话框
    openDetailDialog(job) {
      this.selectedJob = { ...job }
      this.detailVisible = true
    },
    
    // 打开添加对话框
    openAddDialog() {
      this.isEditMode = false
      this.dialogTitle = '添加IT岗位'
      // 重置表单
      this.resetForm()
      this.dialogVisible = true
    },
    
    // 打开编辑对话框
    openEditDialog(job) {
      this.isEditMode = true
      this.dialogTitle = '编辑IT岗位'
      // 复制数据到表单
      this.jobForm = { ...job }
      this.dialogVisible = true
    },
    
    // 重置表单
    resetForm() {
      this.jobForm = {
        id: '',
        companyName: '',
        positionName: '',
        salary: '',
        location: '',
        postDate: '',
        requirements: '',
        benefits: '',
        contactInfo: ''
      }
      if (this.$refs.jobFormRef) {
        this.$refs.jobFormRef.resetFields()
      }
    },
    
    // 提交表单
    handleSubmit() {
      this.$refs.jobFormRef.validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.isEditMode) {
            // 更新操作
            axios.put(`/api/jobs/${this.jobForm.id}`, this.jobForm)
              .then(() => {
                ElMessage.success('更新成功')
                this.dialogVisible = false
                this.loadJobs() // 重新加载列表
              })
              .catch(error => {
                console.error('更新岗位失败:', error)
                ElMessage.error('更新失败')
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            // 添加操作
            axios.post('/api/jobs', this.jobForm)
              .then(() => {
                ElMessage.success('添加成功')
                this.dialogVisible = false
                this.loadJobs() // 重新加载列表
              })
              .catch(error => {
                console.error('添加岗位失败:', error)
                ElMessage.error('添加失败')
              })
              .finally(() => {
                this.loading = false
              })
          }
        }
      })
    },
    
    // 删除岗位
    handleDelete(job) {
      ElMessageBox.confirm(
        `确定要删除"${job.companyName}-${job.positionName}"吗？`,
        '删除确认',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          axios.delete(`/api/jobs/${job.id}`)
            .then(() => {
              ElMessage.success('删除成功')
              this.loadJobs() // 重新加载列表
            })
            .catch(error => {
              console.error('删除岗位失败:', error)
              ElMessage.error('删除失败')
            })
        })
        .catch(() => {
          // 取消删除
        })
    },
    
    // 分页大小变化
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1 // 重置到第一页
    },
    
    // 当前页变化
    handleCurrentChange(val) {
      this.currentPage = val
    },
    
    // 高亮关键词
    highlightText(text) {
      if (!text) return ''
      
      // 合并搜索关键词和筛选条件中的关键词
      const keywords = []
      if (this.searchKeyword) keywords.push(this.searchKeyword)
      if (this.filters.positionName) keywords.push(this.filters.positionName)
      if (this.filters.location) keywords.push(this.filters.location)
      
      if (keywords.length === 0) {
        return text
      }
      
      let result = text
      keywords.forEach(keyword => {
        if (!keyword) return
        const escaped = keyword.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
        const regex = new RegExp(`(${escaped})`, 'gi')
        result = result.replace(regex, '<span style="background-color: #fff3cd; color: #856404;">$1</span>')
      })
      
      return result
    }
  }
}
</script>

<style scoped>
.container {
  padding: 20px;
  min-height: 100vh;
  background: linear-gradient(135deg, #38467a 0%, #4274c8 100%);
}

/* 开发者信息 */
.developer-info {
  text-align: center;
  margin-bottom: 20px;
}

.big-tag {
  font-size: 18px;
  padding: 12px 24px;
  font-weight: bold;
}

/* 卡片样式优化 */
.el-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

/* 搜索和筛选区域 */
.search-filter-section {
  margin-bottom: 20px;
}

.search-box {
  margin-bottom: 15px;
}

.filter-box {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

/* 表格样式 */
.el-table {
  border-radius: 8px;
  overflow: hidden;
}

.clickable-row {
  cursor: pointer;
  transition: background-color 0.3s;
}

.clickable-row:hover {
  background-color: #f5f7fa;
}

/* 分页容器 */
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

/* 对话框样式 */
.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

/* 详情对话框样式 */
.detail-content {
  padding: 10px;
}

.detail-header {
  text-align: center;
  margin-bottom: 20px;
}

.detail-header h3 {
  color: #333;
  margin-bottom: 10px;
}

.detail-salary {
  font-size: 24px;
  font-weight: bold;
  color: #ff6b6b;
}

.detail-section {
  margin-bottom: 20px;
}

.detail-section .detail-title {
  color: #333;
  margin-bottom: 10px;
  font-weight: bold;
  font-size: 16px;
}

/* 薪资筛选容器样式 */
.salary-filter-container {
  padding: 5px 10px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.detail-item {
  margin-bottom: 10px;
}

.detail-label {
  font-weight: bold;
  color: #606266;
  margin-right: 10px;
  min-width: 100px;
  display: inline-block;
}

.detail-text {
  line-height: 1.8;
  color: #606266;
  white-space: pre-wrap;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .container {
    padding: 10px;
  }
  
  .filter-box {
    flex-direction: column;
  }
  
  .filter-box .el-input {
    width: 100% !important;
    margin-right: 0 !important;
    margin-bottom: 10px;
  }
}
</style>