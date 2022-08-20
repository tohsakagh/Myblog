<template>
  <div class="box">
    <aside class="animate__animated animate__bounceInLeft sidebar">
      <div class="avatar">
        <img src="https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about_avator.png" title="Hikari">
      </div>
      <nav class="nav">
        <a v-for="intro in introduceList" @click="showSection(intro.name)" :key="intro.id"><i :class="intro.icon" style="margin-right: 10px"></i> {{intro.title}}</a>
      </nav>
    </aside>
    <main>
      <section class="animate__animated animate__fadeInRight" v-if="showSectionId === '#info'" id="info">
        <div class="wrap">
          <h2 class="title"><i class="iconfont icon-gerenzhongxin1"></i> 个人资料</h2>
          <el-row :gutter="20" class="row">
            <el-col :sm="8" :xs="24" class="col-l-4">
              <p><i class="iconfont icon-name" style="margin-right: 5px"></i>姓名：李进</p>
              <p><i class="iconfont icon-sex" style="margin-right: 5px"></i>性别：男</p>
              <p><i class="iconfont icon-nianling" style="margin-right: 5px"></i>年龄：21</p>
              <p><i class="iconfont icon-xueli" style="margin-right: 5px"></i>学历：本科</p>
              <p><i class="iconfont icon-qq1" style="margin-right: 5px"></i>QQ：2277299512</p>
              <p><i class="iconfont icon-shouye" style="margin-right: 5px"></i>血型：A型</p>
            </el-col>
            <el-col :sm="16" :xs="24" class="col-l-4">
              <p><i class="iconfont icon-nickname" style="margin-right: 5px"></i>昵称：孤桦</p>
              <p><i class="iconfont icon-chushengriqi" style="margin-right: 5px"></i>出生日期：2001-05-18</p>
              <p><i class="iconfont icon-xuexiao1" style="margin-right: 5px"></i>学校：重庆邮电大学大学</p>
              <p><i class="iconfont icon-zhuanye1" style="margin-right: 5px"></i>专业：物联网工程</p>
              <p><i class="iconfont icon-weixin" style="margin-right: 5px"></i>微信：ljmyblogrin</p>
              <p><i class="iconfont icon-email" style="margin-right: 5px"></i>邮箱：<a href="mailto:2569757226@qq.com">2277299512@qq.com</a></p>
            </el-col>
          </el-row>
        </div>
      </section>
      <section class="animate__animated animate__fadeInRight" v-show="showSectionId === '#skills'" id="skills">
        <div class="wrap">
          <h2 class="title"><i class="iconfont icon-zhuanye"></i> 掌握的技能</h2>
          <el-row :gutter="40" class="row scrollable">
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-java"></i>
              </div>
              <div class="skills-title">
                <h3>Java</h3>
                <p>熟悉 Java语言 了解面对对象思想 对JVM 多线程有一定了解</p>
              </div>
            </el-col>
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-linux"></i>
              </div>
              <div class="skills-title">
                <h3>Linux</h3>
                <p>熟悉使用 linux 常用命令等</p>
              </div>
            </el-col>
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-MySQL"></i>
              </div>
              <div class="skills-title">
                <h3>MySQL</h3>
                <p>熟悉MySQL 能对SQL语句进行索引优化</p>
              </div>
            </el-col>
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-spring"></i>
              </div>
              <div class="skills-title">
                <h3>Spring</h3>
                <p>熟悉Spring SpringBoot 能熟练使用 SpringBoot 进行开发</p>
              </div>
            </el-col>
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-redis"></i>
              </div>
              <div class="skills-title">
                <h3>Redis</h3>
                <p>熟悉Redis基本数据结构 了解主从复制 哨兵模式 持久化等</p>
              </div>
            </el-col>
            <el-col :sm="8" :xs="24" class="center-fixed">
              <div class="skills-icon">
                <i class="iconfont icon-cloud"></i>
              </div>
              <div class="skills-title">
                <h3>微服务</h3>
                <p>熟悉SpringCloud SpringCloud Alibaba 常用微服务组件</p>
              </div>
            </el-col>
          </el-row>
        </div>
      </section>
      <section class="animate__animated animate__fadeInRight" v-show="showSectionId === '#works'" id="works">
        <div class="wrap">
          <h2 class="title"><i class="iconfont icon-zuopin1"></i>个人作品</h2>
          <el-row :gutter="30">
            <el-col :xs="24" :sm="12" :lg="8" v-for="work in workList" :key="work.id">
              <div class="works-item">
                <el-card class="works-item-front">
                  <el-image :src="work.pic_url"/>
                  <p>{{work.name}}</p>
                </el-card>
                <el-card class="works-item-back">
                  <p>{{work.desp}}</p>
                </el-card>
              </div>
            </el-col>
          </el-row>
        </div>
      </section>
      <section class="animate__animated animate__fadeInRight" v-show="showSectionId === '#hobbys'" id="hobbys">
          <h2 class="title"><i class="iconfont icon-xingquaihao"></i>我的爱好</h2>
          <div class="row">
            <el-carousel :interval="2000" :type="cardOpen? 'card':''" height="350px">
              <el-carousel-item v-for="hobby in hobbyList" :key="hobby.id">
                <el-card class="hobby">
                  <el-image style="width: 100%" :src="hobby.pic_url"></el-image>
                  <h3 class="tit">{{hobby.name}}</h3>
                  <p>{{hobby.desp}}</p>
                </el-card>
              </el-carousel-item>
            </el-carousel>
        </div>
      </section>
      <section class="animate__animated animate__fadeInRight" v-show="showSectionId === '#summary'" id="summary">
        <div class="wrap">
          <h2 class="title"><i class="iconfont icon-ziwopingjia"></i>自我评价</h2>
          <div class="row">
            <ul>
              <li>一个对一切未知的事物都会抱有好奇心的人</li>
              <li>一个希望用自己所有的精力将事情做到最尽可能完美的人</li>
              <li>一个喜欢拥抱不确定性，爱折腾，去见不同的人，感受不同的环境，不喜欢一成不变的人</li>
            </ul>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      screenWidth: document.documentElement.clientWidth,  //实时屏幕宽度,
      showSectionId: '#info',
      introduceList: [
        {
          id: 0,
          title: '资料',
          name: '#info',
          icon:'iconfont icon-zhuanye1'
        },
        {
          id: 1,
          title: '技能',
          name: '#skills',
          icon:'iconfont icon-jinengliang-xian'
        },
        {
          id: 2,
          title: '作品',
          name: '#works',
          icon:'iconfont icon-zuopin'
        },
        {
          id: 3,
          title: '爱好',
          name: '#hobbys',
          icon:'iconfont icon-xingquaihao'
        },
        {
          id: 4,
          title: '评价',
          name: '#summary',
          icon:'iconfont icon-ziwopingjia'
        },
      ],
      workList:[
        {
          id:0,
          name:'个人博客',
          desp:'一个简单的个人博客项目，分为前台展示和后台管理系统，前台展示部分实现了博客的浏览，查找，分类，标签，评论，留言等功能，后台管理系统实现了博客发表，博客管理，用户管理等功能。',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/essay/info/2022_08_19_13_42_03_QQ%E5%9B%BE%E7%89%8720220819133231.png'
        },
        {
          id:1,
          name:'智慧知识学堂',
          desp:'以SpringCloud、SpringBoot框架开发，前后端分离、实现了单点登录、课程购买、课程评论、视频播放、微信支付等功能',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about_work_school.png'
        },
        {
          id:2,
          name:'安卓电子商城App',
          desp:'一个仿照京东、淘宝等电商App的项目，为用户提供了商家和买家两个角色。实现了商品展示、购物车、收藏、搜索、发布商品等功能。',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about_work_shop.png',
        },
      ],
      hobbyList:[
        {
          id: 0,
          name:'看书',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about-hobby-read.jpg',
          desp:'从小喜欢看书，尤其喜欢看小说，我能够从小说中看到其他人不同却同样精彩的人生'
        },
        {
          id: 1,
          name:'听音乐',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about-hobbt-listen.jpg',
          desp:'闲暇十分喜欢听音乐放松心情'
        },
        {
          id: 2,
          name:'看动画',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about-hobby-donghua.jpg',
          desp:'不管是国产动画还是日本动画，只要好看的我都喜欢看'
        },
        {
          id: 4,
          name:'神话',
          pic_url:'https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/about/about-hobby-mythology.jpg',
          desp:'喜欢了解中国的古典神话和志怪故事'
        }
      ],
    }
  },
  computed:{
    cardOpen(){
      return this.screenWidth >= 748;
    }
  },
  created() {
    window.addEventListener('resize', this.screenAdapter)
  },
  methods: {
    showSection(name) {
      this.showSectionId = name
    },
    // 屏幕尺寸变化的监听函数
    screenAdapter(){
      this.screenWidth = document.documentElement.clientWidth;
    }
  }
}
</script>

<style scoped lang="less">
  body {
    height: 100vh;
    overflow: hidden;
  }

  .box {
    width: 100%;
    height: 100vh;
    position: relative;
  }

  .sidebar {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    color: #fff;
    width: 250px;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    background: rgba(75, 75, 75, 0.9);

    nav {
      margin: 0;
      display: flex;
      list-style: none;
      flex-direction: column;

      a {
        font: 16px/1.5 'Microsoft Yahei', 'PingFang SC', 'Hiragino Sans GB', sans-serif;
        list-style: none;
        box-sizing: border-box;
        text-decoration: none;
        color: #fff;
        padding: 1em;
        display: block;
        text-align: center;
        transition: background .3s, box-shadow .3s;
      }

      a:hover {
        background: rgba(60, 60, 60, 1);
      }
    }

    .avatar {
      width: 160px;
      height: 160px;
      margin: 1em auto;
      margin-top: 5em;
      transition: transform 0.5s;

      img {
        width: 100%;
        border-radius: 50%;
        /*transition: .6s;*/
      }

      img:hover {
        transform: rotate(360deg);
        transition: all 1.5s;
      }
    }
  }

  main {
    color: #353535;
    font: 16px/1.5 'Microsoft Yahei', 'PingFang SC', 'Hiragino Sans GB', sans-serif;
    box-sizing: border-box;
    margin-left: 250px;

    section {
      top: 50%;
      transform: translateY(-50%);
      position: relative;
      background-color: rgba(255, 255, 255, 0.8);
      border: 3px dashed rgba(0, 0, 0, 0.3);
      border-radius: 20px;
      padding: 20px;
      .title{
        display: flex;
        justify-content: flex-start;
        align-items: center;
        i{
          font-size: larger;
          margin-right: 10px;
        }
      }
      a{
        text-decoration: none;
      }
    }

    section:before {
      content: '';
      position: absolute;
      width: 0;
      height: 0;
      border-right: 20px solid rgba(0, 0, 0, 0.3);
      border-top: 20px solid transparent;
      border-bottom: 20px solid transparent;
      top: 50%;
      transform: translateY(-50%);
      left: -20px;
    }

    #info {
      .warp {
        .row {
          display: flex;
          justify-content: space-between;
          align-items: center;
        }
      }
    }

    #skills {
      .center-fixed {
        text-align: center;
        display: flex;
        flex-direction: column;

        .skills-icon {
          width: 100px;
          height: 100px;
          margin: 0 auto;
          background: #fff;
          border-radius: 100%;
          box-shadow: 0 5px rgba(0, 0, 0, 0.3);

          i {
            font-size: 50px;
            line-height: 100px;
            transition: all 1s;
          }

          .fa-html5 {
            content: "\f13b";
          }
        }

        .skills-icon:hover {
          i {
            transform: rotate(180deg);
            transition: all 1s;
          }
        }

      }

    }
    #works{
      .el-card /deep/ .el-card__body{
        padding: 0;
      }

      .works-item{
        margin-bottom: 20px;
        position: relative;
        .works-item-front,.works-item-back{
          background-color: white;
          text-align: center;
          border-radius: 5px;
          box-shadow: 0 0 5px 0 #ccc;
          border: 1px solid #5d5d5d;
          transition: all 1s;
          backface-visibility: hidden;
          width: 100%;
          height: 100%;
        }
        .works-item-front{
          .el-image{
            max-width: 100%;
            max-height: 180px;
            border-radius: 5px 5px 0 0;
          }
          p{
            margin-top: 2px;
            margin-bottom: 2px;
            color: #303133;
          }
        }
        .works-item-back{
          box-sizing: border-box;
          padding: 20px;
          position: absolute;
          top: 0;
          background: rgba(255, 255, 255, 0.8);
          transform: rotateY(180deg);
        }
      }
      .works-item:hover {
       .works-item-back{
          transform: rotateY(0deg);
        }
        .works-item-front{
          transform: rotateY(180deg);
        }
      }
    }

    #hobbys{
      .el-card /deep/ .el-card__body{
        padding: 0;
        height: 350px;
      }
      .hobby{
        opacity: 0.9;
        background-color: rgba(255,255,255,0.9);
        box-shadow: none;
        margin-bottom: 20px;
        border-radius: 20px;
        .el-image{
          width: 100%;
          max-height: 200px;
        }
        .tit{
          margin: 0 auto;
          line-height: 20px;
          text-align: center;
        }
        p{
          margin: 0;
          padding: 20px;
          font-size: medium;
        }
      }
    }
    #summary{
      ul{
        li{
          line-height: 50px;
          border-bottom: 2px dashed #63a35c;
          background-color: rgba(0,0,0,0.1);
          margin-bottom: 20px;
          border-radius: 9999px;
          list-style-type: circle;
        }
      }
    }
  }


  @media screen and (max-width: 768px){
    .sidebar{
      position: relative;
      top: 0;
      width: 100%;
      height: auto;
      display: flex;
      flex-direction: column;
      .avatar{
        margin: 1rem auto;
        width: 100px;
        height: 100px;
      }
      nav{
        background-color: rgba(255,255,255,0.1);
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
      }
    }
    main{
      margin: 0 auto;
      width: 100%;
      text-align: center;
      section{
        border-radius: 0;
        border: none;
      }
      #summary{
        ul{
          li{
            border-radius: 0;
            border-bottom: none;
            background-color: transparent;
            text-align: left;
          }
        }
      }
    }
  }

</style>