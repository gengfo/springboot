<template>
  <div>
    <slot name="content">
      <transition leave-active-class="animated bounceOutLeft">
        <div v-show="isCloseFooter ? false : true" class="blog-footer">
          <Modal v-model="propModal.value" :width="propModal.width" :height="propModal.height" @on-cancel="closeModal">
            <blog-login-register :is-register="propBlogLoginRegister.isRegister"></blog-login-register>
            <p slot="footer" :style="{display: 'none'}"></p>
          </Modal>
          <Row type="flex" justify="center" align="middle" class="code-row-bg">
            <Col :xs="0" :sm="16" :md="16" :lg="16">
              <Row>加入Javalsj博客系统，共同成长！</Row>
              <Row>Copyright 2018 - {{currentyear}} www.javalsj.com 版权所有</Row>
            </Col>
            <Col :xs="24" :sm="8" :md="8" :lg="8">
              <Row>
                <Button type="primary" shape="circle" size="large" @click="login">登录</Button>
                <Button type="primary" shape="circle" size="large" @click="register">注册</Button>
                <Button type="ghost" shape="circle" icon="close-circled" size="large" @click="closeFooter"></Button>
              </Row>
            </Col>
          </Row>
        </div>
      </transition>
    </slot>
  </div>
</template>

<script>
import BlogLoginRegister from '@/components/common/BlogLoginRegister.vue'
export default {
  name: 'BlogFooter',
  components: { BlogLoginRegister },
  data () {
    return {
      currentyear: 2018,
      // 登录注册组件属性
      propBlogLoginRegister: {
        isRegister: undefined
      },
      // 对话框组件属性
      propModal: {
        value: false,
        width: 450,
        height: 700
      },
      // 关闭
      isCloseFooter: false
    }
  },
  created () {
    // 获取系统当前时间
    var date = new Date()
    this.$set(this, 'currentyear', date.getFullYear())
  },
  methods: {
    login () {
      this.$set(this.propBlogLoginRegister, 'isRegister', false)
      this.$set(this.propModal, 'value', true)
    },
    register () {
      this.$set(this.propBlogLoginRegister, 'isRegister', true)
      this.$set(this.propModal, 'value', true)
    },
    closeFooter () {
      this.$set(this, 'isCloseFooter', true)
    },
    closeModal () {
      this.$set(this.propBlogLoginRegister, 'isRegister', undefined)
    }
  }
}
</script>

<style lang="less" scoped>
.blog-footer {
  display: block;
  bottom: 0px;
  width: 100%;
  padding: 0px;
  margin: 0 auto;
  background: rgba(220, 229, 239, 0.9);
  line-height: 1.5;
  font-size: 16px;
  color: #535886;
  opacity: 0.8;
}
</style>
