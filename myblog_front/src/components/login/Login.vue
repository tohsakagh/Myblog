<template>
    <el-dialog class="login_dialog" title="请登录" :visible.sync="loginFormVisiable" @close="resetLoginForm" width="400px" center>
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="60px" class="login_form">
            <!--        用户名-->
            <el-form-item prop="nickname" label="账号">
                <el-input v-model="loginForm.nickname">
                </el-input>
            </el-form-item>
            <!--        密码-->
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="loginForm.password"></el-input>
            </el-form-item>
            <el-form-item style="text-align: right">
                <el-button @click="resetLoginForm">取消</el-button>
                <el-button type="primary" @click="userLogin">登录</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import {mapState} from 'vuex'

export default {
    data() {
        return {
            user:{
                nickname:'',
                password:'',
            },
            // 表单数据绑定对象
            loginForm: {
                nickname: '',
                password: ''
            },
            // 表单验证规则对象
            loginFormRules: {
                // 验证用户是否合法
                nickname: [
                    // 必填，提示消息，鼠标焦点消失时触发
                    {required: true, message: "请输入用户名", trigger: "blur"},
                    {min: 2, max: 10, message: "长度在2-10个字符之间"}
                ],
                // 验证密码是否合法
                password: [
                    {required: true, message: "请输入密码", trigger: "blur"},
                    {min: 6, max: 10, message: "长度在 6 到 10 个字符", trigger: "blur"}
                ]
            },
        }
    },
    computed:{
       ...mapState([
           'loginFormVisiable'
       ])
    },
    methods: {
        resetLoginForm() {
            this.$store.commit('cancelLFV')
            this.$refs.loginFormRef.resetFields()
        },
        // 用户登录
        userLogin() {
            this.$refs.loginFormRef.validate(async valid => {
                if (!valid) return;
                this.user.nickname = this.loginForm.nickname
                this.user.password = this.$md5(this.loginForm.password)
                const {data: res} = await this.$blog.post('/front/user/login', {
                    user:this.user
                });
                if (res.code !== 200) return this.$message({message: res.message, type: 'error', offset: 80})
                this.$message({message: res.message, type: 'success', offset: 80});
                this.$refs.loginFormRef.resetFields()
                window.sessionStorage.setItem("token", JSON.stringify(res.data.token));
                window.sessionStorage.setItem("user", JSON.stringify(res.data.user));
                this.$store.commit('getUserInfo')
                this.$store.commit('cancelLFV')
            })
        }
    }
}
</script>

<style lang="less" scoped>

    .login_form {
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }

    .btns {
        display: flex;
        justify-content: flex-end;
    }

    .login_dialog{
        opacity: 1;
    }

</style>
