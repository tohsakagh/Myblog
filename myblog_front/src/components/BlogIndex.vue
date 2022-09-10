<template>
  <div>
    <el-container id="index" class="animate__animated animate__fadeInUp">
      <el-row :gutter="12">
        <el-col :xs="24" :sm="17">
          <el-card style="background-color: rgba(255,255,255,0.9)" class="left-item" >
            <div slot="header" class="total">
              <div class="title">
                <i v-if="selected" class="el-icon-back" @click="updateBlogList"></i>
                <span>{{ selectMethod }}</span>
              </div>
              <span>共 <span style="color: #3a8ee6; font-size: 20px">{{ totalcount }}</span> 篇</span>
            </div>
            <el-row type="flex" align="middle" style="flex-wrap: wrap; height: 220px" :gutter="20" v-for="blog in blogList"
                    :key="blog.id"
                    shadow="never" class="animate__animated animate__fadeInUp blog-content " s>
              <el-col class="img" :xs="24" :sm="8">
                <el-image  :src="blog.cover" ></el-image>
              </el-col>
              <el-col :xs="24" :sm="16">
                <div @click="getBlogInfo(blog.id)" style="user-select: none">
                  <div style="position: absolute;top: 10px">
                    <div v-if="blog.flag === '0'">
                      <el-tag>原创</el-tag>
                    </div>
                    <div v-else>
                      <el-tag type="danger">转载</el-tag>
                    </div>
                  </div>
                  <h3 style="position: absolute;top: 30px">{{ blog.title }}</h3>
                  <p class="description" style="position: absolute; top: 70px">{{ blog.description }}</p>
                    <div class="blog-date" style="position: absolute; bottom: 10px; left: 250px">
                      <i class="el-icon-date"></i>
                      <span>{{ blog.createTime | dataFormat }}</span>
                    </div>
                    <div class="blog-type" style="position: absolute; bottom: 10px; right: 30px">
                      <el-tag effect="plain">{{ blog.typeName }}</el-tag>
                    </div>
                </div>
              </el-col>
            </el-row>
          </el-card>
          <el-pagination
              :small="pagSmall"
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :page-size="queryInfo.pagesize"
              :current-page="queryInfo.pagenum"
              :layout="pagLayout"
              :total="totalcount">
          </el-pagination>
        </el-col>
        <el-col :xs="24" :sm="7">
          <el-card style="background-color: rgba(255,255,255,0.9)"
                   class="animate__animated animate__fadeInUp right-item">
            <div slot="header" class="attributes">
              <b>分类</b>
            </div>
            <ul>
              <li class="animate__animated animate__fadeInUp blog-type" v-for="type in typeList"
                  :key="type.id"
                  @click="selectType(type.id)"
                  :class="type.id === typeId? 'activeType':''">
                <div style="display: flex;align-items: center">
                  <el-image :src="type.picUrl"
                            style="width: 28px;height: 28px; border-radius: 50%; margin-right: 10px"></el-image>
                  {{ type.name }}
                </div>
                <div>{{ type.blogCount }}</div>
              </li>
            </ul>
            <div class="more" @click="dealType">
              <i v-if="moreType" class="el-icon-arrow-down"></i>
              <i v-else class="el-icon-arrow-up"></i>
            </div>
          </el-card>
          <el-card style="background-color: rgba(255,255,255,0.9)"
                   class="animate__animated animate__fadeInUp right-item">
            <div slot="header" class="attributes">
              <b>标签</b>
            </div>
            <div class="tags">
              <div class="animate__animated animate__fadeInUp tag-item" v-for="tag in tagList"
                   :key="tag.id"
                   @click="selectTag(tag.id)"
                   :class="tag.id === tagId? 'activeTag':''">
                <div class="sjx-outer">
                  <div class="sjx-inner"></div>
                </div>
                <div class="tag">
                  {{ tag.name }}
                  {{ tag.blogCount }}
                </div>
              </div>
            </div>
            <div class="more" @click="dealTag">
              <i v-if="moreTag" class="el-icon-arrow-down"></i>
              <i v-else class="el-icon-arrow-up"></i>
            </div>
          </el-card>
          <el-card style="background-color: rgba(255,255,255,0.9)"
                   class="animate__animated animate__fadeInUp right-item">
            <div slot="header" class="attributes">
              <span>最新推荐</span>
            </div>
            <div class="animate__animated animate__fadeInUp recommend-blog l-text" v-for="blog in recommendList"
                 :key="blog.id"
                 @click="getBlogInfo(blog.id)">
              <a>{{ blog.title }}</a>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-container>
  </div>
</template>

<script>
import { marked }from 'marked';
export default {
  data() {
    return {
      totalcount: 100,
      queryInfo: {
        pagenum: 1,
        pagesize: 5
      },
      intro: '',
      blogList: [],
      typeList: [],
      tagList: [],
      recommendList: [],
      selectMethod: '全部博客',
      typeId: -1,
      tagId: -1,
      selected: false,
      moreType: true,
      moreTag: true,
      value: new Date(),
      timer: null,
      start: false,
    }
  },
  computed: {
    pagSmall() {
      return this.screenWidth <= 768;
    },
    // 计算分页栏样式
    pagLayout() {
      if (this.screenWidth < 768) {
        return 'prev, pager, next'
      } else {
        return 'total, prev, pager, next, jumper'
      }
    }
  },
  created() {
    window.addEventListener('resize', this.screenAdapter)
  },
  mounted() {
    this.getTypeList()
    this.getBlogList()
    this.getTagList()
    this.getRecommendList()
  },
  methods: {
    // 开始进入主页
    startRead() {
      this.$nextTick(() => {
        document.getElementById('index').scrollIntoView({
          behavior: 'smooth',
          block: 'start',
          // inline: 'nearest'
        });
      })
    },
    compare(property) {
      return function (a, b) {
        let value1 = a[property].length;
        let value2 = b[property].length;
        return value2 - value1;
      }
    },
    // 获取推荐博客列表
    async getRecommendList() {
      const {data: res} = await this.$blog.get('/front/blog/getRecommendBlogList')
      // console.log(res)
      this.recommendList = res.data
      this.total = res.total
    },
    // 获取博客类型列表
    async getTypeList() {
      const {data: res} = await this.$blog.get('/front/type/getTypeList')
      // console.log(res)
      this.typeList = res.data
    },
    // 获取博客标签列表
    async getTagList() {
      const {data: res} = await this.$blog.get('/front/tag/getTagList')
      // console.log(res)
      this.tagList = res.data
    },
    // 获取博客列表
    async getBlogList() {
      const {data: res} = await this.$blog.get(`/front/blog/getBlogList/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
      this.blogList = res.data.content
      this.totalcount = res.data.totalElements
    },
    // 跳转到博客详情页
    getBlogInfo(blogId) {
      // console.log(blogId.id)
      this.$router.push({path: '/blogInfo', query: {id: blogId}});
    },
    // 修改当前页码
    handleCurrentChange(newSize) {
      this.queryInfo.pagenum = newSize
      this.getBlogList()
    },
    // 修改当前页大小
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
    },
    // 按分类筛选博客
    async selectType(id) {
      this.tagId = -1
      this.typeId = id
      const {data: res} = await this.$blog.get(`/front/type/${this.typeId}/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
      // console.log(res)
      this.blogList = res.data.content
      this.totalcount = res.data.totalElements
      this.selectMethod = '分类: ' + this.typeList.find(item => item.id === this.typeId).name
      this.selected = true
    },
    // 按标签筛选博客
    async selectTag(id) {
      this.typeId = -1
      this.tagId = id
      const {data: res} = await this.$blog.get(`/front/tag/${this.tagId}/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
      this.blogList = res.data.content
      this.totalcount = res.data.totalElements
      this.selectMethod = '标签: ' + this.tagList.find(item => item.id === this.tagId).name
      this.selected = true
    },
    // 更新博客列表
    updateBlogList() {
      this.selected = false
      this.typeId = -1
      this.tagId = -1
      this.selectMethod = '全部博客'
      this.getBlogList()
    },
    // 得到所有的分类
    async getFullTypeList() {
      const {data: res} = await this.$blog.get('/front/type/getAllType')
      this.typeList = res.data
    },
    // 得到所有的标签
    async getFullTagList() {
      const {data: res} = await this.$blog.get('/front/tag/getAllTag')
      // console.log(res.data)
      this.tagList = res.data
    },
    async dealType() {
      if (this.moreType) {
        await this.getFullTypeList()
      } else {
        await this.getTypeList()
      }
      this.moreType = !this.moreType
    },
    async dealTag() {
      if (this.moreTag) {
        await this.getFullTagList()
      } else {
        await this.getTagList()
      }
      this.moreTag = !this.moreTag
    },

  },
}
</script>

<style scoped lang="less">

body {
  width: 100%;
}


@keyframes clipMe {

  0%,
  100% {
    clip: rect(0px, 806px, 6px, 0px);
  }

  25% {
    clip: rect(0px, 6px, 112px, 0px);
  }

  50% {
    clip: rect(112px, 812px, 112px, 0px);
  }

  75% {
    clip: rect(0px, 812px, 112px, 806px);
  }
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translate(-50%, 0);
  }
  40% {
    transform: translate(-50%, -30px);
  }
  60% {
    transform: translate(-50%, -15px);
  }
}


ul {
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 0;
  border-radius: 5px;
}

.el-pagination {
  padding-bottom: 20px;
}


.el-card /deep/ .el-card__body {
  padding: 0;
}


.right-item {
  margin-bottom: 20px;

  li:first-child {
    border-top: 1px solid rgba(179, 216, 255, 0.5);
  }

  li {
    border-bottom: 1px solid rgba(179, 216, 255, 0.5);
  }

  .more {
    text-align: center;
    color: #3a8ee6;
    padding: 8px;
  }

  .more:hover {
    cursor: pointer;
    color: #3a8ee6;
  }

  .blog-type:hover, .activeType {
    background-color: rgba(58, 142, 230, 0.3);
    cursor: pointer;
  }

  .tags {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    margin: 15px 13px 0;
    border-bottom: 1px solid rgba(179, 216, 255, 0.5);

  }

  .tag-item {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-left: 5px;
    margin-right: 5px;
    margin-bottom: 10px;
    box-sizing: border-box;

    .tag {
      background-color: #ecf5ff;
      box-sizing: border-box;
      display: inline-block;
      height: 22px;
      padding: 0 10px;
      line-height: 22px;
      font-size: 10px;
      color: #409eff;
      border-radius: 4px;
      white-space: nowrap;
      border: 1px solid #409eff;
      transition: .2s;
    }

    .sjx-outer {
      width: 0;
      height: 0;
      border-top: 6px solid transparent;
      border-bottom: 6px solid transparent;
      border-right: 6px solid #409eff;
      position: relative;
      transition: .2s;
    }

    .sjx-inner {
      border-top: 6px solid transparent;
      border-bottom: 6px solid transparent;
      border-right: 6px solid #ecf5ff;
      top: -6px;
      left: 1px;
      position: absolute;
      transition: .2s;
    }
  }


  .tag-item:hover, .activeTag {
    box-sizing: border-box;

    .tag {
      color: white;
      background-color: #409eff;
      cursor: pointer;
    }

    .sjx-inner {
      border-right: 6px solid #409eff;
    }
  }

}


.blog-type {
  display: flex;
  justify-content: space-between;
  align-items: center;
  line-height: 40px;
}

.recommend-blog {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  padding-left: 20px;
  padding-right: 20px;

  a {
    border-bottom: 1px solid rgba(34, 36, 38, .15);
    line-height: 40px;
    display: block;
    text-decoration: none;
    color: black;
  }

  a:hover {
    color: #3a8ee6;
  }
}

.total {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: larger;
  font-weight: bold;

  .title {
    display: flex;
    align-items: center;

    .el-icon-back {
      font-weight: bolder;
      color: #3a8ee6;
      margin-right: 10px;
    }

    .el-icon-back:hover {
      cursor: pointer;
    }
  }
}

.blog-content:hover {
  /*border-left: 5px solid #3a8ee6;*/
  /*border-right: 5px solid #3a8ee6;*/
  background-color: rgba(58, 142, 230, 0.3);
  cursor: pointer;

  .el-tag {
    color: white;
    background-color: #3a8ee6;
  }
}

.blog-content {
  padding: 10px;
  height: auto;
  border-bottom: 1px solid rgb(199, 163, 92);
  /*border-bottom: 1px solid rgba(34, 36, 38, .15);*/
  transition: .3s;


  .el-image {
    border-radius: 5px;
    box-sizing: border-box;
    flex-shrink: 0;
  }

  .blog-info {
    display: flex;
    align-items: center;
    color: rgba(0, 0, 0, .4);
    font-size: 10px;

    .user-info {
      display: flex;
      justify-content: space-around;
      align-items: center;
      margin-right: 15px;

      .el-avatar {
        margin-right: 5px;
        width: 22px;
        height: 22px;
      }

      .header {
        text-decoration: none;
        color: #3a8ee6;
        font-weight: bold;
      }
    }

    .blog-date {
      margin-right: 15px;
    }

    .blog-type {
      margin-left: auto;
    }
  }
}

.description {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  font: 300 1em/1.8 PingFang SC, Lantinghei SC, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif;
}

@media screen and (max-width: 768px) {
  .blog-date {
    display: none;
  }

  .welcome {
    width: 100%;

    .border {
      display: none;
    }

    .tit {
      font-size: 2rem;
      width: 100%;
      line-height: 50px;
      letter-spacing: 2px;
      height: auto;
    }

    .intro {
      font-size: 1rem;
      line-height: 30px;
    }
  }

  .el-pagination {
    width: 100%;
  }

}

</style>
