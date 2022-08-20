<template>
  <div>
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow="none" class="welcome">
          <h1 class="tit">
            欢迎来到孤桦的个人博客
            <div class="border"></div>
          </h1>

          <h2 class="intro">{{ intro }}</h2>

        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  data() {
    return {
      intro: '',
      value: new Date(),
      timer: null,
      start: false,
    }
  },
  mounted() {
    let str = '这是我的个人博客、会分享关于编程，开发以及其他方面的一些内容，希望可以对您有所帮助...';
    let idx = 0;
    let that = this
    let timer = setTimeout(function fn() {
      // console.log(this.intro)
      that.intro = that.intro + str.substring(idx, idx + 1)
      idx++
      if (idx > str.length) {
        that.intro = ''
        idx = 0
      }
      setTimeout(fn, 200)
    }, 2000)

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
  },
}
</script>

<style scoped lang="less">

body {
  width: 100%;
}


.welcome {
  background-color: rgba(0, 0, 0, 0.1);
  background-size: cover;
  border: none;
  height: 90%;
  position: relative;

  .border {
    width: 812px;
    height: 112px;
    position: absolute;
    top: -6px;
    left: -6px;
    border: 3px solid white;
    box-sizing: border-box;
    animation: clipMe 5s linear infinite;
  }

  .tit {
    box-sizing: border-box;
    position: relative;
    width: 800px;
    height: 100px;
    line-height: 100px;
    box-shadow: inset 0 0 0 1px white;
    margin: 80px auto 40px;
    color: white;
    text-align: center;
    font-size: 50px;
    font-weight: normal;
    letter-spacing: 10px;
  }

  .intro {
    letter-spacing: 5px;
    line-height: 50px;
    width: 80%;
    margin: 0 auto;
    text-align: center;
    font-weight: normal;
    color: white;
  }

  .down {
    animation: bounce 2s infinite;
    animation-duration: 3s;
    font-size: 25px;
    position: absolute;
    bottom: 5px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 2px solid #fff;
  }

  .down:hover {
    animation: none;
    cursor: pointer;
    box-shadow: 0 0 20px 0 white;
    transition: all .2s;
  }
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
