<template>
  <div>
    <!--    面包屑导航区-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/theAdminOfMyBlogGh' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>分类管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card shadow="never">
      <el-button type="primary" @click="createTypeDialog">新建分类</el-button>
      <el-table :data="typeList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="图片" prop="picUrl" width="150px">
          <template slot-scope="scope">
            <el-image :src="scope.row.picUrl"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="分类名称" prop="name"></el-table-column>
        <el-table-column label="博客数量" prop="blogCount"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--            修改按钮-->
            <el-button
                :disabled="scope.row.id === '0'"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="editBlogDialog(scope.row)"
            >编辑
            </el-button>
            <!--            删除按钮-->
            <el-button
                :disabled="scope.row.id === '0'"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="removeBlogById(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="分类修改" :visible.sync="createTypeDialogFormVisible">
      <el-form style="text-align: left" ref="createTypeFormRef" :model="createTypeForm"
               :rules="createTypeFormRules" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="createTypeForm.name"></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-upload
              ref="upload"
              action="http://localhost:8090//tencentcloud/upload/type"
              list-type="picture-card"
              :limit="1"
              :file-list="fileList"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-success="handleSuccess">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="backPage">取消</el-button>
        <el-button type="primary" @click="createType">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      typeList: [],
      createTypeDialogFormVisible: false,
      dialogImageUrl: '',
      createTypeForm: {
        name: ''
      },
      createTypeFormRules: {
        name: [
          {required: true, message: '分类名称不能为空', trigger: 'blur'}
        ],
      },
      type: {
        id: null,
        name: '',
        picUrl: ''
      },
      fileList: [],
      dialogVisible: false
    }
  },
  created() {
    this.getFullTypeList();
  },
  methods: {

    compare(property) {
      return function (a, b) {
        let value1 = a[property].length;
        let value2 = b[property].length;
        return value2 - value1;
      }
    },
    // 得到所有的分类
    async getFullTypeList() {
      const {data: res} = await this.$blog.get('/theAdminOfMyBlogGh/type/getAllType')
      this.typeList = res.data.sort(this.compare('blogCount'))
      console.log(this.typeList)
    },

    selectCard(id) {
      this.selectedCard = id
      // console.log(this.selectedCard)
    },
    createType() {
      this.$refs.createTypeFormRef.validate(async valid => {
        if (!valid) return
        this.type.name = this.createTypeForm.name
        this.type.picUrl = this.dialogImageUrl
        // console.log(this.type)
        const {data: res} = await this.$blog.post('/theAdminOfMyBlogGh/type/saveType', {
          type: this.type
        })
        // console.log(res)
        if (res.code === 200) {
          this.createTypeDialogFormVisible = false
          await this.getFullTypeList()
          return this.$message.success(res.message)
        } else {
          this.createTypeDialogFormVisible = false
          this.createTypeForm.name = ''
          return this.$message.error(res.message)
        }
      })
    },
    async removeBlogById(row) {
      const {data: res} = await this.$blog.get(`/theAdminOfMyBlogGh/type/delete/${row.id}`)
      // console.log(res)
      if (res.code === 200) {
        this.createTypeDialogFormVisible = false
        await this.getFullTypeList()
        return this.$message.success(res.message)
      } else {
        return this.$message.error(res.message)
      }
    },
    handleRemove() {
      this.dialogImageUrl = ''
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    async handleSuccess(res) {
      console.log(res)
      this.dialogImageUrl = res.data
    },
    backPage() {
      this.$refs.upload.clearFiles()
      this.blog.firstPicture = ''
      this.createTypeDialogFormVisible = false
      this.createTypeForm.name = ''
      this.$refs.createTypeFormRef.resetFields()
    },
    createTypeDialog(){
      this.createTypeForm.name = ''
      this.dialogImageUrl = ''
      console.log(this.dialogImageUrl)
      this.createTypeDialogFormVisible = true
    },
    editBlogDialog(row) {
      this.type = row
      // console.log(this.type)
      if (row.picUrl !== '') {
        this.dialogImageUrl = row.picUrl
      }
      this.fileList = [{name: row.picUrl, url: row.picUrl}]
      this.createTypeForm.name = row.name
      this.createTypeDialogFormVisible = true
    }
  },
}
</script>

<style scoped>

</style>