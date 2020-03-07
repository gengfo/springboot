<template>
  <div class="blog-admin">
    <Row type="flex" justify="end" align="middle" class="code-row-bg blog-admin-header">
      <Col span="2" offset="1">
        <Icon type="navicon-round" size="24"></Icon>
      </Col>
      <Col span="20" offset="1">
        <Row type="flex" justify="end" align="middle">
          <Badge dot>
            <Icon type="ios-bell-outline" size="26"></Icon>
          </Badge>
          <Avatar size="large" src="/static/logo-min.jpg"/>
        </Row>
      </Col>
    </Row>
    <Row type="flex" justify="start" align="top" class="code-row-bg blog-admin-center">
      <Col span="4" :style="{padding: '5px', height: '100%'}">
        <Menu :style="{width: '100%', height: '100%'}" :open-names="['1', '2']" active-name="1-1">
          <Submenu name="1">
            <template slot="title">
              <Icon type="ios-paper"></Icon>
              内容管理
            </template>
            <MenuItem name="1-1">文章管理</MenuItem>
            <MenuItem name="1-2">评论管理</MenuItem>
            <MenuItem name="1-3">举报管理</MenuItem>
          </Submenu>
          <Submenu name="2">
            <template slot="title">
              <Icon type="ios-people"></Icon>
              用户管理
            </template>
            <MenuItem name="2-1">新增用户</MenuItem>
            <MenuItem name="2-2">活跃用户</MenuItem>
          </Submenu>
        </Menu>
      </Col>
      <Col span="20" :style="{padding: '5px', height: '100%'}">
      <Row type="flex" align="middle" class="code-row-bg blog-admin-center-tag">
        <Tag v-for="tagItem in tagItems" :key="tagItem" :name="tagItem" closable color="blue" checkable @on-close="closeTag(tagItem)">标签{{ tagItem + 1 }}</Tag>
      </Row>
      <Row type="flex" align="middle" class="code-row-bg blog-admin-center-breadcrumb">
        <Breadcrumb>
          <BreadcrumbItem>系统管理</BreadcrumbItem>
          <BreadcrumbItem>单据定义</BreadcrumbItem>
          <BreadcrumbItem>资产卡片</BreadcrumbItem>
        </Breadcrumb>
      </Row>
      <Row type="flex" justify="center" align="middle" class="code-row-bg blog-admin-center-content">
        cccccc
      </Row>
      </Col>
    </Row>
    <Row type="flex" justify="center" align="middle" class="code-row-bg blog-admin-footer">
      <Row>加入Javalsj博客系统，共同成长！</Row>
      <Row>Copyright 2018 - {{currentyear}} www.javalsj.com 版权所有</Row>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'BlogAdminIndex',
  data () {
    return {
      currentyear: 2018,
      tagItems: ['11', '22', '33'],
      currentTabComponent: ''
    }
  },
  created () {
    // 获取系统当前时间
    var date = new Date()
    this.$set(this, 'currentyear', date.getFullYear())
  },
  mounted () {},
  methods: {}
}
</script>

<style scoped>
.blog-admin {
  position: relative;
  margin: 0px;
  padding: 0px;
  background: #f5f7f9;
  border-radius: 4px;
  width: 100%;
  height: 100%;
}
.blog-admin-header {
  background-color: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  height: 60px;
}
.blog-admin-center {
  padding-top: 5px;
  width: 100%;
}
.blog-admin-center-sider {
  position: absolute;
  width: 100%;
}
.blog-admin-center-breadcrumb {
  background: #fff;
  width: 100%;
  height: 30px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}
.blog-admin-center-content {
  display: block;
  padding: 10px;
  width: 100%;
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}
.blog-admin-footer {
  position: fixed;
  bottom: 0px;
  width: 100%;
  height: 60px;
  text-align: center;
  line-height: 1.5;
  font-size: 18px;
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}
</style>
