const HttpStatuCodes = {
  // 成功响应码200
  CODE_200: 200,
  // 错误响应码400
  CODE_400: 400,
  // 无TOKEN权限响应码401
  CODE_401: 401
}
/* 使用代理限制属性只读,让外部不能修改响应码属性值 */
let httpStatuCodes = new Proxy(HttpStatuCodes, {
  get (target, key) {
    return target[key]
  },
  set (target, key, value) {
    console.log('状态码是枚举不允许修改，不能对[' + key + ']赋值。')
    return false
  }
})
export default httpStatuCodes
