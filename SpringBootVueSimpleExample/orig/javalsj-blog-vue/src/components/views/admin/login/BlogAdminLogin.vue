<template>
  <div class="blog-admin-login animated fadeInDown">
    <blog-particles class="blog-admin-login-particles"></blog-particles>
    <Row type="flex" :gutter="16" justify="center" align="middle"  class="code-Row-bg blog-admin-login-center">
      <Col order="1" :xs="0" :sm="0" :md="12" :lg="13" class="blog-admin-login-center-leftcol" >
      </Col>
      <Col order="2" :xs="24" :sm="24" :md="12" :lg="11" class="blog-admin-login-center-rightcol">
          <blog-login-register :is-admin="true" class="woah blazingStar"></blog-login-register>
      </Col>
    </Row>
  </div>
</template>

<script>
import BlogParticles from '@/components/common/BlogParticles.vue'
import BlogLoginRegister, { BUS_BLOG_LOGIN_REGISTER_MSG_NAME } from '@/components/common/BlogLoginRegister.vue'
import HttpStatuCodes from '@/util/modules/http-status-code-util.js'

export default {
  name: 'BlogAdminLogin',
  components: { BlogParticles, BlogLoginRegister },
  created () {
    // 监听登录组件是否成功
    this.$bus.on(BUS_BLOG_LOGIN_REGISTER_MSG_NAME, (msg) => {
      this.handleLoginRegister(msg)
    })
  },
  beforeDestroy () {
    this.$bus.off(BUS_BLOG_LOGIN_REGISTER_MSG_NAME)
  },
  methods: {
    handleLoginRegister (msg) {
      let isSuccessResponse = msg.formLoginInfo.isSuccessResponse
      let responseData = msg.formLoginInfo.responseData
      if (!isSuccessResponse) {
        this.$Message.error((msg.formLoginInfo.isRegister === true ? '注册' : '登录') + '失败：' + responseData.message + '。')
        return
      }
      if (responseData.code === HttpStatuCodes.CODE_200) {
        if (msg.formLoginInfo.isRegister) {
          this.$Message.info('注册成功。')
        } else {
          this.$router.replace({ path: '/admin/index' })
          this.$Message.info('登陆成功。')
        }
        return
      }
      // 失败后将错误消息弹出
      this.$Message.error(responseData.message)
    }
  }

}
</script>

<style lang='less' scoped>
// @import '~@/assets/styles/admin/login/blog-admin-login.less';
.blog-admin-login {
  margin: 0px auto;
  padding: 0px;
  width: 100%;
  height: 100%;
  /* 加载背景图 */
  background-image: url('~@/assets/images/admin/login/blog-admin-login-background-06.jpg');
  /* 让背景图基于容器大小伸缩 */
  background-size: cover;
  /* 背景图垂直、水平均居中 */
  background-position: center center;
  /* 背景图不平铺 */
  background-repeat: no-repeat;
}
.blog-admin-login-particles {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
}
.blog-admin-login-center {
  margin: 0px auto;
  position: relative;
  top: 20%;
}
.blog-admin-login-center-leftcol {
  margin: 0px auto;
  padding: 5px;
}
.blog-admin-login-center-rightcol {
  position: relative;
  margin: 0px auto;
  padding: 0px;
  width: 450px;
  z-index: 1;
}
</style>
