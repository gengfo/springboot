<template>
    <div class="blog-home">
      <blog-particles class="blog-home-particles"></blog-particles>
      <div class="blog-home-header">
        <blog-header/>
      </div>
      <div class="blog-home-content">
        <router-view/>
      </div>
      <div class="blog-home-footer animated slideInLeft">
        <blog-footer/>
      </div>
    </div>
</template>

<script>
import BlogParticles from '@/components/common/BlogParticles.vue'
import BlogHeader from '@/components/views/home/header/BlogHeader.vue'
import BlogFooter from '@/components/views/home/footer/BlogFooter.vue'
export default {
  name: 'BlogHome',
  components: { BlogParticles, BlogHeader, BlogFooter }
}
</script>

<style scoped>
.blog-home {
  position: absolute;
  margin: 0 auto;
  padding: 0;
  width: 100%;
  height: 100%;
}
.blog-home-particles {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100%;
  z-index: -1;
}
.blog-home-header {
  margin: 0px auto;
  position: relative;
  top: 0px;
  width: 100%;
  height: 30px;
  background: transparent;
  opacity: 1;
  z-index: 1;
}
.blog-home-content {
  margin: 0px auto;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  background: transparent;
}
.blog-home-footer {
  margin: 0px auto;
  position: fixed;
  bottom: 0px;
  width: 100%;
  height: 50px;
  text-align: center;
  background: transparent;
  opacity: 1;
  z-index: 1;
}
</style>
