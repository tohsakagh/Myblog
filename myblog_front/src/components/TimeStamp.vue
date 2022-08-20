<template>
  <el-container>
    <el-timeline class="animate__animated animate__fadeInLeft">
      <el-timeline-item :color="de.color" v-for="de in list" :key="de.id" :timestamp="de.createTime| dataFormat5" placement="top">
          <P :style="{color: de.color}" @click="getEssayInfo(essay.id)">{{de.task}}</P>
      </el-timeline-item>
    </el-timeline>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      list: [],
    }
  },
  created() {
    this.getDevelopmentList()
  },
  methods: {
    async getDevelopmentList() {
      const {data: res} = await this.$blog.get(`/front/development/getList`)
      this.list = res.data
    },

  }
}
</script>

<style scoped>

</style>