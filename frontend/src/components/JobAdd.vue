<template>
  <div class="container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>添加IT岗位</span>
        </div>
      </template>
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
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">提交</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
import { ElMessage } from 'element-plus'

export default {
  name: 'JobAdd',
  data() {
    return {
      loading: false,
      jobForm: {
        companyName: '',
        positionName: '',
        salary: '',
        location: '',
        postDate: '',
        requirements: '',
        benefits: '',
        contactInfo: ''
      },
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
  methods: {
    // 提交表单
    handleSubmit() {
      this.$refs.jobFormRef.validate((valid) => {
        if (valid) {
          this.loading = true
          axios.post('/api/jobs', this.jobForm)
            .then(() => {
              ElMessage.success('添加成功')
              this.$router.push('/job/list')
            })
            .catch(error => {
              console.error('添加岗位失败:', error)
              ElMessage.error('添加失败')
            })
            .finally(() => {
              this.loading = false
            })
        }
      })
    },
    // 重置表单
    handleReset() {
      this.$refs.jobFormRef.resetFields()
    },
    // 取消操作
    handleCancel() {
      this.$router.push('/job/list')
    }
  }
}
</script>

<style scoped>
.container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>