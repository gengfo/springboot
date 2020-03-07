<template>
  <div class="blog-login-class">
    <!-- 登录页面 -->
    <Row>
      <p class="blog-login-header-text-class" v-text="formLoginInfo.isRegister ? '注册' : '登录'"></p>
    </Row>
    <Row>
      <Form ref="formLoginInfo" :model="formLoginInfo" :rules="formLoginInfoRule">
        <!-- 用户名 -->
        <FormItem prop="username" label="用户名：">
          <Input type="text" v-model="formLoginInfo.username" size="large" clearable placeholder="请输入用户名">
              <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <!-- 密码 -->
        <FormItem prop="password" label="密码：">
          <Input type="password" v-model="formLoginInfo.password" size="large" clearable placeholder="请输入密码">
              <Icon type="ios-locked-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <!-- 确认密码（注册时显示） -->
        <FormItem prop="password2" label="确认密码：" v-if="formLoginInfo.isRegister">
          <Input type="password" v-model="formLoginInfo.password2" size="large" clearable placeholder="请输入确认密码">
              <Icon type="ios-locked-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <!-- 验证码 -->
        <FormItem prop="verify" label="验证码：" v-if="formLoginInfo.showVerify">
          <Row>
            <i-switch v-model="formLoginInfo.isVerifySuccess" v-show="false"></i-switch>
          </Row>
          <Row justify="space-between" class="code-row-bg">
            <Col span="24">
              <blog-verify-code ref="verify" @success="verifyCheck('success')" @error="verifyCheck('error')"></blog-verify-code>
            </Col>
          </Row>
        </FormItem>
        <FormItem>
          <Row type="flex" justify="space-between" class="code-row-bg">
            <Col span="10">
              <Button type="primary" :loading="formLoginInfo.submitLoading" @click="submitFormLoginInfo()">
                <span v-if="!formLoginInfo.submitLoading" v-text="formLoginInfo.isRegister===true ? '注册' : '登录'"></span>
                <span v-else v-text="formLoginInfo.isRegister===true ? '注册中...' : '登录中...'"></span>
              </Button>
            </Col>
            <Col span="5">
              <Button type="primary" icon="ios-refresh-outline" shape="circle" @click="resetFormLoginInfo()">重置</Button>
            </Col>
          </Row>
        </FormItem>
        <FormItem>
          <Row type="flex" justify="end" class="code-row-bg">
              <Button type="ghost" size="small" @click='formLoginInfo.isRegister=!formLoginInfo.isRegister' v-text="formLoginInfo.isRegister === true ? '前去登录→ ' : '前去注册→ '"></Button>
          </Row>
        </FormItem>
      </Form>
    </Row>
  </div>
</template>

<script>
import BlogVerifyCode from './BlogVerifyCode.vue'
// 组件事件消息
export const BUS_BLOG_LOGIN_REGISTER_MSG_NAME = 'BLOG_LOGIN_REGISTER_MSG'
export default {
  name: 'BlogLoginRegister',
  components: { BlogVerifyCode },
  props: {
    // 是否为注册页面
    isRegister: {
      type: Boolean,
      default: undefined
    },
    // 是否为登录管理平台
    isAdmin: {
      type: Boolean,
      default: false
    }
  },
  data () {
    // 表单验证码自定义校验器
    const verifyValidator = (rule, value, callback) => {
      if (!this.formLoginInfo.showVerify || this.formLoginInfo.isVerifySuccess === true) {
        callback()
      } else {
        callback(new Error('请按提示点击验证码。'))
      }
    }
    // 表单密码自定义校验器
    const passwordValidator = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请填写密码。'))
        return
      }
      if (value.length < 6) {
        callback(new Error('密码不可少于6位。'))
        return
      }
      callback()
    }
    // 表单确认密码（注册）自定义校验器
    const password2Validator = (rule, value, callback) => {
      if (!this.formLoginInfo.isRegister) {
        callback()
        return
      }
      // 注册时校验确认密码
      if (value === '') {
        callback(new Error('请填写确认密码。'))
        return
      }
      if (value.length < 6) {
        callback(new Error('确认密码不可少于6位。'))
        return
      }
      if (this.formLoginInfo.password !== value) {
        callback(new Error('确认密码和密码须保持一致。'))
        return
      }
      callback()
    }
    return {
      formLoginInfo: {
        username: '',
        password: '',
        password2: '',
        // 已选验证码字符
        verifyCode: '',
        // 登录注册校验错误次数，控制密码输错3次后每一次输入都需要弹出请输入验证码
        loginRegisterFailCount: 0,
        // 是否显示验证码
        showVerify: false,
        // 验证码是否通过
        isVerifySuccess: false,
        // 是否注册界面，初始值从props中获取
        isRegister: undefined,
        // 是否显示登录注册中
        submitLoading: false,
        // 服务器端校验结果
        isSuccessResponse: false,
        responseData: {}
      },
      // 表单信息合法性验证
      formLoginInfoRule: {
        username: [
          { required: true, message: '请填写用户名。', trigger: 'blur' },
          { type: 'string', max: 24, message: '用戶名不能多于24位。', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: passwordValidator, trigger: 'blur' }
        ],
        password2: [
          { required: true, validator: password2Validator, trigger: 'blur' }
        ],
        verify: [
          { required: true, validator: verifyValidator }
        ]
      }
    }
  },
  methods: {
    /* 提交表单 */
    submitFormLoginInfo () {
      this.$refs.formLoginInfo.validate((valid) => {
        if (!valid) {
          // 表单校验失败提示
          this.$Message.error((this.formLoginInfo.isRegister === true ? '注册' : '登录') + '失败：表单数据未通过校验，请检查。')
          this.$set(this.formLoginInfo, 'loginRegisterFailCount', this.formLoginInfo.loginRegisterFailCount + 1)
          this.refreshVerify()
          return
        }
        // 表单校验成功后，登陆按钮显示登陆中状态
        this.$set(this.formLoginInfo, 'submitLoading', true)
        let apiUrl = ''
        if (this.formLoginInfo.isRegister) {
          // 提交注册表单到后台服务
          apiUrl = 'api/register'
        } else {
          // 提交登录表单到后台服务
          apiUrl = 'api/login'
        }

        let paramsLoginRegisterInfoVo = {
          isAdmin: this.isAdmin,
          username: this.formLoginInfo.username,
          password: this.formLoginInfo.password,
          password2: this.formLoginInfo.password2,
          verifyCode: this.formLoginInfo.verifyCode
        }
        this.$axios
          .post(apiUrl, paramsLoginRegisterInfoVo)
          .then(successResponse => {
            this.submitSuccess(successResponse)
          })
          .catch(failResponse => {
            this.submitFail(failResponse)
          })
      })
    },
    /* 重置表单 */
    resetFormLoginInfo () {
      this.$refs.formLoginInfo.resetFields()
      // 刷新验证码
      this.refreshVerify()
    },
    /* 验证码结果响应 */
    verifyCheck (info) {
      if (info === 'success') {
        // 验证码通过
        this.$set(this.formLoginInfo, 'isVerifySuccess', true)
        this.$set(this.formLoginInfo, 'verifyCode', this.$refs.verify.getVerifyCode())
      } else {
        // 验证不通过刷新验证码
        this.$set(this.formLoginInfo, 'isVerifySuccess', false)
      }
      this.$refs.formLoginInfo.validateField('verify')
    },
    /* 刷新验证码 */
    refreshVerify () {
      if (!this.formLoginInfo.showVerify) {
        return
      }
      // 清除历史验证标记
      this.$set(this.formLoginInfo, 'isVerifySuccess', false)
      // 刷新验证码
      this.$refs.verify.refresh()
    },
    /* 登录、注册成功 */
    submitSuccess (successResponse) {
      // 表单对象记录响应信息
      this.$set(this.formLoginInfo, 'responseData', successResponse)
      this.$set(this.formLoginInfo, 'isSuccessResponse', true)
      // 登录成功后设置token信息
      this.$store.commit('setToken', successResponse.token)
      if (!this.isRegister) {
        // 若为登录成功则获取该用户的有权限的路由
      }
      this.$set(this.formLoginInfo, 'submitLoading', false)
      // 成功后将消息传递出去
      this.$bus.emit(BUS_BLOG_LOGIN_REGISTER_MSG_NAME, this)
    },
    /* 登录、注册失败 */
    submitFail (failResponse) {
      this.$set(this.formLoginInfo, 'loginRegisterFailCount', this.formLoginInfo.loginRegisterFailCount + 1)
      this.$set(this.formLoginInfo, 'isSuccessResponse', false)
      this.$set(this.formLoginInfo, 'responseData', failResponse)
      // 刷新验证码
      this.refreshVerify()
      // 失败后将消息传递出去
      this.$bus.emit(BUS_BLOG_LOGIN_REGISTER_MSG_NAME, this)
      this.$set(this.formLoginInfo, 'submitLoading', false)
    }
  },
  watch: {
    isRegister: function () {
      // 响应父级是否注册界面属性信息
      this.formLoginInfo.isRegister = this.isRegister
    },
    'formLoginInfo.loginRegisterFailCount': function () {
      // 显示验证码，根据登录注册次数判定是否显示验证码（控制密码输错3次后每一次输入都需要弹出请输入验证码）, 注册不控制验证码
      this.formLoginInfo.showVerify = this.formLoginInfo.loginRegisterFailCount >= 3
    }
  }
}
</script>

<style lang="less" scoped>
.blog-login-class {
  font-family: Arial,Helvetica,sans-serif;
  font-weight: bold;
  font-size: 100%;
  padding: 10px;
  margin: 0px;
  /* 背景透明 */
  /* background: transparent; */
  background-color:rgba(255,255,255,0.3);
  opacity: 3;
  border-radius: 20px;
  border-bottom-width: 2px;
  // box-shadow: 0px 0px 10px rgba(0,0,0,0.3), 0px 0px 10px rgba(0,0,0,0.3) inset;
}
.blog-login-header-text-class {
  color:#535886;
  font-size: 30px;
  font-family: Arial,Helvetica,sans-serif;
  font-weight: bold;
  text-align:center;
}
</style>
