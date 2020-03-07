<template>
  <div>
    <Row type="flex" :gutter="16" justify="center" align="middle" class="code-Row-bg">
      <!-- 小屏样式 -->
      <Row type="flex" :gutter="16" justify="center" align="middle" class="code-Row-bg">
        <Col :xs="24" :sm="24" :md="0" :lg="0">
          <!-- 博客头像功能菜单 -->
          <Row>博客头像功能菜单</Row>
          <!-- 全文搜索框 -->
          <Row>全文搜索框</Row>
          <!-- 博文内容列表，赞赏功能 -->
          <Row>博文内容列表，赞赏功能</Row>
          <!-- 博文上下编文章标题 -->
          <Row>博文上下编文章标题</Row>
          <!-- 博文评论区 -->
          <Row>博文评论区</Row>
        </Col>
      </Row>

      <!-- 大屏样式 -->
      <Row type="flex" :gutter="16" justify="center" align="middle" class="code-Row-bg">
        <Col :xs="0" :sm="0" :md="4" :lg="4">
          <!-- 全文搜索框 -->
          <Row>全文搜索框</Row>
          <!-- 博客头像功能菜单 -->
          <Row>博客头像功能菜单</Row>
        </Col>
        <Col :xs="0" :sm="0" :md="20" :lg="20">
          <!-- 博文内容列表，赞赏功能 -->
          <Row>博文内容列表，赞赏功能</Row>
          <!-- 博文上下编文章标题 -->
          <Row>博文上下编文章标题</Row>
          <!-- 博文评论区 -->
          <Row>博文评论区</Row>
        </Col>
      </Row>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'BlogIndex',
  data () {
    return {
    }
  }
}
</script>
