<template>
  <div>
    <el-card shadow="never">
      <el-row :gutter="5">
        <el-col :span="8">
          <el-input placeholder="请输入用户名或用户昵称" v-model="search"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="searchUser()">查找用户</el-button>
        </el-col>
      </el-row>
      <el-table :data="userList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="头像" prop="avatar" min-width="60px">
          <template slot-scope="scope">
            <el-avatar :src="scope.row.avatar"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column label="昵称" prop="nickname"></el-table-column>
        <el-table-column label="邮箱" prop="email" min-width="100px"></el-table-column>
        <el-table-column label="注册时间" prop="createTime" min-width="100px">
          <template slot-scope="scope">{{ scope.row.createTime | dataFormat5 }}</template>
        </el-table-column>
        <el-table-column label="最近登录" prop="loginTime">
          <template slot-scope="scope">
            {{ scope.row.lastLoginTime === null ? scope.row.createTime : scope.row.loginTime | dataFormat3 }}
          </template>
        </el-table-column>
        <!--                <el-table-column label="地址">-->
        <!--                    <template slot-scope="scope">{{scope.row.loginProvince +' '+ scope.row.loginCity }}</template>-->
        <!--                </el-table-column>-->
        <el-table-column label="管理员" min-width="70px">
          <template slot-scope="scope">
            <el-switch active-value="0" inactive-value="1" v-model="scope.row.type"
                       :disabled="userInfo.id=== scope.row.id || (userInfo.type === '1' && scope.row.id === '1') || userInfo.type === '0'"
                       @change="userStateChanged(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="60px">
          <template slot-scope="scope">
            <el-button size="mini" circle type="danger" icon="el-icon-delete"
                       :disabled="userInfo.id=== scope.row.id || scope.row.id === 1"
                       @click="deleteUser(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-pagination
        background
        :current-page.sync="current"
        :page-size="limit"
        :total="total"
        layout="total,prev, pager, next"
        @current-change="getUserList">
    </el-pagination>
  </div>

</template>

<script>
import {mapState} from 'vuex'

export default {
  data() {
    return {
      current: 1,
      limit: 10,
      total: 0,
      userList: [],
      search: ''
    }
  },
  computed: {
    ...mapState([
      'userInfo',
    ])
  },
  created() {
    this.getUserList()
  },
  methods: {
    async getUserList() {
      const {data: res} = await this.$blog.get(`/theAdminOfMyBlogGh/user/getUseList/${this.current}/${this.limit}`);
      if (res.code === 200) {
        if (this.search !== '') {
          let st = this.search
          let reg = RegExp(st)
          res.data = res.data.records.filter((item) => {
            return reg.test(item.nickname) || reg.test(item.username)
          })
        }
        this.userList = res.data.records
        this.total = res.data.total
      } else {
        this.$message.error("获取用户信息失败！")
      }
    },
    searchUser() {
      // console.log(this.search)
      this.getUserList()
    },
    // 修改用户权限
    async userStateChanged(row) {
      const {data: res} = await this.$blog.post('/theAdminOfMyBlogGh/user/updateUserAutho', {
        user: row
      });
      if (res.code !== 200) return this.$message.error("修改权限失败")
      this.$message.success("修改权限成功")

    },
    // 删除用户
    async deleteUser(id) {
      // 弹出对话框
      const confirmResult = await this.$confirm(
          '此操作将永久删除该用户, 是否继续',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const {data: res} = await this.$blog.get(`/theAdminOfMyBlogGh/user/delete/${id}`)
      if (res.code !== 200) return this.$message.error('删除用户失败！')
      this.$message.success('删除用户成功！')
      this.getUserList()
    }
  }
}
</script>

<style scoped lang="less">

</style>
