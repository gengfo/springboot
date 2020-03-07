<template>
    <transition leave-active-class="animated bounceOutLeft">
        <Modal v-model="showAlertModalValue" :width="widthValue" :height="heightValue" @on-cancel="closeModal">
          {{ messageValue }}
        </Modal>
    </transition>
</template>

<script>
// 弹出提示框Bus消息事件
export const BUS_BLOG_TEMPLATE_WINDOW_MSG_NAME = 'BUS_BLOG_TEMPLATE_WINDOW_MSG'
export default {
  name: 'BlogTemplateWindow',
  props: {
    // 是否弹出提示框
    showAlertModal: {
      type: Boolean,
      default: false
    },
    width: {
      type: Number,
      default: 600
    },
    height: {
      type: Number,
      default: 400
    },
    message: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      showAlertModalValue: false,
      widthValue: 600,
      heightValue: 400,
      messageValue: ''
    }
  },
  created () {
    // 监听弹出提示框消息
    this.$bus.on(BUS_BLOG_TEMPLATE_WINDOW_MSG_NAME, windowInfo => {
      let {
        showAlertModal,
        width,
        height,
        message
      } = windowInfo
      this.openWindow(showAlertModal, width, height, message)
    })
  },
  beforeDestroy () {
    this.$bus.off(BUS_BLOG_TEMPLATE_WINDOW_MSG_NAME)
  },
  methods: {
    openWindow (showAlertModal, width, height, message) {
      this.showAlertModalValue = showAlertModal
      this.widthValue = width
      this.heightValue = height
      this.messageValue = message
    }
  },
  watch: {
    showAlertModal: function () {
      this.showAlertModalValue = this.showAlertModal
    },
    width: function () {
      this.widthValue = this.width
    },
    height: function () {
      this.heightValue = this.height
    },
    message: function () {
      this.messageValue = this.message
    }
  }
}
</script>

<style>

</style>
