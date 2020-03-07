<template>
    <div class="blog-html">
        <div v-html="html">
        </div>
    </div>
</template>

<script>
export default {
  name: 'BlogHtml',
  props: {
    // html连接地址
    htmlUrl: {
      required: true,
      type: String,
      default: 'http://www.baidu.com/'
    }
  },
  data () {
    return {
      // 正在加载html
      htmlLoading: false,
      // html文档内容
      html: ''
    }
  },
  watch: {
    htmlUrl: function () {
      this.html = this.loadHtmlByUrl(this.htmlUrl)
    }
  },
  methods: {
    loadHtmlByUrl (htmlUrl) {
      if (htmlUrl !== '' && htmlUrl.length > 0) {
        this.htmlLoading = true
        let param = {
          accept: 'text/html, text/plain'
        }
        this.$axios
          .get(htmlUrl, param)
          .then(successResponse => {
            this.htmlLoading = false
            this.html = successResponse.data
          })
          .catch(failResponse => {
            this.htmlLoading = false
            this.html = '<p>加载页面失败</p>'
          })
      }
    }
  }
}
</script>

<style scoped>
.blog-html {
  width: 100%;
  height: 100%;
  padding: 0px;
  margin: 0px;
}
</style>
