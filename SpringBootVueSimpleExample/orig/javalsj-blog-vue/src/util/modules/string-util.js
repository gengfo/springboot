const StringUtil = {
  isEmpty: function (input) {
    return input === null || input === undefined || input === ''
  },
  isNotEmpty: function (input) {
    return !this.isEmpty(input)
  },
  defaultIfEmpty: function (input, defaultStr) {
    return this.isEmpty(input) ? defaultStr : input
  },
  // 字符串反转
  reverse: function (input) {
    if (this.isBlank(input)) {
      return input
    }
    return input.split('').reverse().join('')
  }
}

export default StringUtil
