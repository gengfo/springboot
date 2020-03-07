<template>
  <div v-show="isCloseHeader ? false : true">
    <slot name="content">
      <transition leave-active-class="animated bounceOutLeft">
        <div class="blog-header">
          <Row type="flex" justify="center" align="middle" class="code-row-bg">
            <Col span="23">
              <marquee>
                <span style="font-size: 16px; color: red;">系统消息：</span>
                <span style="font-size: 14px;">{{ noticeMsg }}</span>
              </marquee>
            </Col>
            <Col span="1">
              <Button type="ghost" shape="circle" icon="close-circled" size="large" @click="closeHeader"></Button>
            </Col>
          </Row>
        </div>
      </transition>
    </slot>
  </div>
</template>

<script>
const noticeMsgs = [
  '世界快末日了，有件事情我一直瞒着你，其实我是奥特曼。',
  '黑夜给了我一双黑色的眼睛，可我却用它来翻白眼。',
  '水能载舟，亦能煮粥。',
  '开电脑，还是关电脑，这是一个问题。',
  '我记得小时侯家里很穷。住在农村一遇到下雨房子就漏水，我还清楚的记得尤其是门头哪里漏的厉害。水都漏在了门锁上。导致了门锁经常生锈，于是我就跪在地上求着锁说，你别绣了，求你别绣了，求你别秀了，在秀门就锁不住了。'
]
export default {
  name: 'BlogHeader',
  data () {
    return {
      noticeMsg: '',
      isCloseHeader: false,
      interval: {}
    }
  },
  created () {
    let randomNoticeIndex = Math.floor(Math.random() * noticeMsgs.length)
    this.noticeMsg = noticeMsgs[randomNoticeIndex]
    this.interval = setInterval(() => {
      let randomNoticeIndex = Math.floor(Math.random() * noticeMsgs.length)
      this.noticeMsg = noticeMsgs[randomNoticeIndex]
    }, 20000)
  },
  methods: {
    closeHeader () {
      this.$set(this, 'isCloseHeader', true)
      this.$emit('close', this)
    }
  },
  beforeDestroy () {
    clearInterval(this.interval)
  }
}
</script>

<style scoped>
.blog-header{
  display: block;
  top: 0px;
  width: 100%;
  padding: 0px;
  margin: 0 auto;
  background: rgba(220, 229, 239, 0.9);
  line-height: 1.5;
  font-size: 16px;
  color: #535886;
  opacity: 0.8;
  text-align: center;
}
</style>
