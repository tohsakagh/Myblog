<template>
  <!--导航-->
  <div>
    <div class="animate__animated animate__fadeIn title" :key="key" :style="{'background-image': bgUrl}"></div>
    <el-header :style="'margin-bottom:'+ headerBottom +'px'" class="animate__animated animate__fadeIn">
      <h2 class="animate__animated animate__swing logo" :key="shade" @click="shade++">孤桦</h2>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" background-color="rgba(0,0,0,0)"
               style="border: none;"
               router text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item :index="'/'+item.path" v-for="item in menulist" background-color="rgba(0,0,0,0)" :key="item.id" @click="changePage(item.path)">
          <template slot="title">
            <!--                图标-->
            <i :class="iconsObj[item.id]"></i>
            <!--                文本-->
            <span>{{item.authName}}</span>
          </template>
        </el-menu-item>
      </el-menu>
      <div v-if="menuHiddenVisiable">
        <el-menu :default-active="activeIndex" class="animate__animated animate__fadeInDown el-menu-hidden"
                 background-color="#545c64" router text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item :index="'/'+item.path" v-for="item in menulist" :key="item.id" @click="changePage(item.path)">
            <template slot="title">
              <!--                图标-->
              <i :class="iconsObj[item.id]"></i>
              <!--                文本-->
              <span>{{item.authName}}</span>
            </template>
          </el-menu-item>
        </el-menu>
      </div>

      <div class="menu-expend" @click="menuExpend">
        <i class="el-icon-menu"></i>
      </div>

      <div v-if="logined" style="margin-right: 50px">
        <el-button size="mini" effect="light" type="primary" @click="showLFV">登录</el-button>
        <el-button size="mini" effect="light" type="warning" @click="showRFV">注册</el-button>
      </div>
      <div v-else class="loginInfo">
        <el-avatar :src="userInfo.avatar"></el-avatar>
        <div class="user-option">
          <h3 class="web-font nickname">{{userInfo.nickname}}</h3>
          <p v-if="administrator" class="logout" @click="manageBlog">管理博客</p>
          <p class="logout" @click="logout">退出登录</p>
        </div>
      </div>
      <login></login>
      <register></register>
    </el-header>
  </div>

</template>

<script>

import Login from "../login/Login";
import Register from "../login/Register";
import {mapState} from "vuex";

export default {
  components: {
    Register,
    Login
  },
  data() {
    return {
      key: 0,
      shade: 0,
      scrollFlag: false,
      navClass: '',
      activeIndex: '1',
      menulist: [
        {
          id: 1,
          authName: '首页',
          path: 'index'
        },
        {
          id: 2,
          authName: '博客',
          path: 'blog'
        },
        {
          id: 3,
          authName: '随笔',
          path: 'essay'
        },
        {
          id: 4,
          authName: '项目',
          path: 'project'
        },
        {
          id: 5,
          authName: '留言',
          path: 'message'
        },
        {
          id: 6,
          authName: '归档',
          path: 'timeStamp'
        },
        {
          id: 7,
          authName: '关于我',
          path: 'about'
        },
      ],
      iconsObj: {
        '1': 'iconfont icon-menu_home',
        '2': 'iconfont icon-jilu2',
        '3': 'iconfont icon-jilu2',
        '4': 'iconfont icon-xiangmu1',
        '5': 'iconfont icon-liuyan',
        '6': 'el-icon-date',
        '7': 'iconfont icon-gerenzhongxin1',
      },
      isCollapse: false,
      menuHiddenVisiable: false,
      headerBottom: 0,
    };
  },
  computed: {
    logined() {
      return this.userInfo === null
    },
    ...mapState([
      'userInfo',
      'administrator',
    ])
  },
  watch: {
    'queryInfo.query': {
      handler(value) {
        if (this.timer) {
          clearTimeout(this.timer)
        }
        this.timer = setTimeout(() => {
          this.searchBlog()
        }, 300)
      }
    }
  },
  created() {
    const idx = Math.floor(Math.random()*11) + 1;
    this.bgUrl = `url("\thttps://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/background//${idx}.jpg")`
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },

  methods: {

    notSearching(){
      setTimeout( () => {
        this.searching = false
      },100)

    },
    changePage(name) {
      // console.log(name)
      this.$store.commit('changePage',name)
    }
    ,
    showLFV() {
      this.$store.commit('showLFV')
    }
    ,
    showRFV() {
      this.$store.commit('showRFV')
    }
    ,


    handleScroll() {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop > 60 && this.scrollFlag === false) {
        this.scrollFlag = true
      } else if (scrollTop <= 60 && this.scrollFlag === true) {
        this.scrollFlag = false
      }

    }
    ,
// 注销登录
    logout() {
      window.sessionStorage.clear()
      this.$store.commit('getUserInfo')
      this.$message({message: '退出登录成功', type: 'success', offset: 80});
    }
    ,
// 进入管理界面
    manageBlog() {
      this.$router.push('/theAdminOfMyBlogGh')
    }
    ,
// 展开菜单栏
    menuExpend() {
      this.menuHiddenVisiable = !this.menuHiddenVisiable
      if (this.menuHiddenVisiable === true) {
        this.headerBottom = 280
      } else {
        this.headerBottom = 0
      }
    }
  }
}
</script>

<style>

  .search input.el-input__inner {
    background-color: rgba(0, 0, 0, 0.1);
    /*border-radius: 20px;*/
    color: #cccccc;
  }
</style>

<style scoped lang="less">

  .title {
    position: fixed;
    top: 0;
    width: 100%;
    height: 120vh;
    background-repeat: no-repeat;
    background-size: cover;
    border-bottom: 3px solid #fff;
  }

  .el-header {
    transition: .2s;
  }

  .el-header:hover {
    opacity: 1 !important;
  }

  .el-menu {
    background-color: rgba(0, 0, 0, 0) !important;
  }

  .el-menu /deep/ .el-menu-item{
    background-color: rgba(0, 0, 0, 0) !important;
  }


  .el-menu /deep/ .el-menu-item i {
    color: rgba(255, 255, 255);
  }

  .el-menu /deep/ .el-menu-item:hover i {
    color: white;
  }

  .el-menu /deep/ .el-menu-item:hover {
    background-color: rgba(0, 0, 0, 0.5) !important;
  }

  .search_input {
    position: relative;
    box-sizing: border-box;
  }


  .search_input ul {
    position: absolute;
    top: 30px;
    width: 100%;
    border: 1px solid #e5e9ef;
    margin-top: 1px;
    background: #fff;
    z-index: 10000;
    border-radius: 2px;
    box-shadow: 0 2px 4px rgba(58, 118, 142, 0.16);
    padding: 10px 0;
    font-size: 14px;
    box-sizing: border-box;
  }

  .search_input ul li {
    padding: 0 16px;
    height: 32px;
    line-height: 32px;
    cursor: pointer;
    word-wrap: break-word;
    word-break: break-all;
    display: block;
    color: #222;
    position: relative;
    /*transition: .2 ease;*/
  }

  .search_input ul li:hover {
    background-color: #e8f3ff;
  }


  .search-blog {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    padding-left: 20px;
    padding-right: 20px;
  }

  .el-header {
    width: 100%;
    position: sticky;
    top: 0;
    z-index: 9999;
    background-color: rgba(0, 0, 0, 0.2);
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;

    > div {
      display: flex;
      align-items: center;
    }

    img {
      height: 40px;
    }

    span {
      margin-left: 15px;
    }

    button {
      opacity: 0.8;
    }

    .el-menu {
      flex-shrink: 0;
    }

    .logo {
      color: #ffd04b;
    }

    .logo:hover {
      cursor: pointer;
    }

  }

  .loginInfo {
    flex-shrink: 0;
    /*background-color: #545c64;*/
    color: white;
    height: 60px;
    border: none;
    width: 160px;
    position: relative;

    .el-avatar {
      width: 36px;
      height: 36px;
      margin: 0 auto;
      z-index: 200;
    }

    .user-option {
      position: absolute;
      top: 60px;
      width: 150px;
      left: 50%;
      transform: translate(-50%, 0);
      font-size: 14px;
      text-align: center;
      line-height: 30px;
      background-color: #fff;
      visibility: hidden;
      opacity: 0;
      color: #333;
      box-shadow: 0 2px 6px 0 rgb(0 0 0 / 10%);
      border: 1px solid #eee;
      border-radius: 5px;
    }

    .logout {
      margin: 10px auto;
      padding: 0;
      width: 100%;
    }

    .logout:hover {
      background-color: #eee;
    }
  }

  .loginInfo:hover {
    cursor: pointer;

    .el-avatar {
      transform: translate(0, 50%) scale(1.5);
      transition: .3s;
    }

    .user-option {
      visibility: visible;
      opacity: 1;
      transition: opacity .4s;
    }
  }

  .zZindex {
    margin-top: 300px;
    padding-top: 100px;
  }

  .menu-expend {
    display: none !important;
  }

  .el-menu-hidden {
    /*display: none;*/
    position: absolute;
    top: 60px;
    left: 0;
    border-top: 1px solid #ccc;
    border-right: none;
    width: 100%;

    .el-menu-item {
      border-bottom: 1px solid #ccc;
    }
  }

  @media screen and (max-width: 1000px) {
    .search_input {
      visibility: hidden;
    }
  }

  @media screen and (max-width: 768px) {
    .el-menu /deep/ .el-menu-item{
      background-color: rgba(0, 0, 0, 0.3) !important;
    }

    .el-menu-demo {
      display: none;
    }

    .title {
      width: 100%;
      background-position-x: center;
    }

    .menu-expend {
      display: block !important;
      position: fixed;
      top: 18px;
      left: 150px;
    }

    .menu-expend:hover {
      color: #ffd04b;
      cursor: pointer;
    }

    .title {
      background-position-y: 0;
    }
  }

</style>
