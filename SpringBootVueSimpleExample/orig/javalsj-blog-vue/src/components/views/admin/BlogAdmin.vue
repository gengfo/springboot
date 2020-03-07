<template>
    <div class="blog-admin">
      <router-view/>
      <BackTop :height="10" :bottom="100" :right="50">
        <div class="blog-admin-back-top">
          <Icon type="chevron-up" color="black"></Icon>
        </div>
      </BackTop>
    </div>
</template>

<script>
export default {
  name: 'BlogAdmin'
}
</script>

<style scoped>
.blog-admin {
  margin: 0 auto;
  padding: 0px;
  width: 100%;
  height: 100%;
}
.blog-admin-back-top {
  width: 54px;
  height: 54px;
  background-color: cadetblue;
  text-align: center;
  border-radius: 10px;
}
</style>
