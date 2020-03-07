<template>
  <div style="position: relative">
    <div class="verify-img-out" v-show="showImage"
        :style="{'position': 'relative',
                'height': parseInt(setSize.imgHeight) + vSpace + 'px'
                }">
      <div class="verify-img-panel"
          :style="{'width': setSize.imgWidth,
                  'height': setSize.imgHeight,
                  'background-size': setSize.imgWidth + ' ' + setSize.imgHeight,
                  'margin-bottom': vSpace + 'px'}">
        <div class="verify-refresh" @click="refresh" v-show="showRefresh">
          <Icon size="20" color="#ffffff" type="ios-refresh-outline"></Icon>
        </div>
        <canvas :width="setSize.imgWidth" :height="setSize.imgHeight" ref="canvas" @click="bindingClick ? canvasClick($event) : undefined"></canvas>
        <div v-for="(tempPoint, index) in tempPoints" :key="index" class="point-area"
            :style="{'background-color':'#1abd6c',
                    color:'#fff',
                    width:'30px',
                    height:'30px',
                    'text-align':'center',
                    'line-height':'30px',
                    'border-radius': '50%',
                    position:'absolute',
                    top:parseInt(tempPoint.y-10) + 'px',
                    left:parseInt(tempPoint.x-10) + 'px'
              }">
            {{index + 1}}
        </div>
      </div>
    </div>
    <div class="verify-bar-area"
        :style="{'width': this.barSize.width,
                'height': this.barSize.height,
                'color': this.barAreaColor,
                'border-color': this.barAreaBorderColor,
                'line-height':this.barSize.height}">
        <span class="verify-msg" :style="{'color': this.msgColor}">{{text}}</span>
    </div>
  </div>
</template>

<script>
function resetSize (vm) {
  // 图片的宽度、高度，移动条的宽度、高度
  let imgwidth, imgheight, barwidth, barheight
  let parentWidth = vm.$el.parentNode.offsetWidth || window.offsetWidth
  let parentHeight = vm.$el.parentNode.offsetHeight || window.offsetHeight
  if (vm.imgSize.width.indexOf('%') !== -1) {
    imgwidth = parseInt(this.imgSize.width) / 100 * parentWidth + 'px'
  } else {
    imgwidth = this.imgSize.width
  }
  if (vm.imgSize.height.indexOf('%') !== -1) {
    imgheight = parseInt(this.imgSize.height) / 100 * parentHeight + 'px'
  } else {
    imgheight = this.imgSize.height
  }
  if (vm.barSize.width.indexOf('%') !== -1) {
    barwidth = parseInt(this.barSize.width) / 100 * parentWidth + 'px'
  } else {
    barwidth = this.barSize.width
  }
  if (vm.barSize.height.indexOf('%') !== -1) {
    barheight = parseInt(this.barSize.height) / 100 * parentHeight + 'px'
  } else {
    barheight = this.barSize.height
  }
  return {
    imgWidth: imgwidth,
    imgHeight: imgheight,
    barWidth: barwidth,
    barHeight: barheight
  }
}
export default {
  name: 'BlogVerifyCode',
  props: {
    // 默认的验证码上的文字数量
    defaultNum: {
      type: Number,
      default: 4
    },
    // 验证字
    fontStr: {
      type: String,
      default: '天地玄黄宇宙洪荒日月盈昃辰宿列张寒来暑往秋收冬藏闰余成岁律吕调阳云腾致雨露结为霜金生丽水玉出昆冈剑号巨阙珠称夜光果珍李柰菜重芥姜海咸河淡鳞潜羽翔龙师火帝鸟官人皇始制文字乃服衣裳推位让国有虞陶唐吊民伐罪周发殷汤坐朝问道垂拱平章爱育黎首臣伏戎羌遐迩体率宾归王'
    },
    // 校对验证码的文字数量
    checkNum: {
      type: Number,
      default: 3
    },
    // 验证码和信息框间隔
    vSpace: {
      type: Number,
      default: 2
    },
    // 图片URL
    imgUrls: {
      type: Array,
      default () {
        return [
          '/static/images/verify/blog-verify-code-background-01.jpg',
          '/static/images/verify/blog-verify-code-background-02.jpg',
          '/static/images/verify/blog-verify-code-background-03.jpg',
          '/static/images/verify/blog-verify-code-background-04.jpg',
          '/static/images/verify/blog-verify-code-background-05.gif',
          '/static/images/verify/blog-verify-code-background-06.jpg'
        ]
      }
    },
    // 图片地址
    imgSize: {
      type: Object,
      default () {
        return {
          width: '100%',
          height: '150px'
        }
      }
    },
    // 信息框大小
    barSize: {
      type: Object,
      default () {
        return {
          width: '100%',
          height: '40px'
        }
      }
    }
  },
  data () {
    return {
      // 选中的坐标信息
      fontPos: [],
      // 用户点击的坐标
      checkPosArr: [],
      // 点击的记数
      num: 1,
      // 随机的背景图片
      imgRand: 0,
      setSize: {
        imgHeight: 0,
        imgWidth: 0,
        barHeight: 0,
        barWidth: 0
      },
      showImage: true,
      tempPoints: [],
      text: '',
      barAreaColor: undefined,
      barAreaBorderColor: undefined,
      msgColor: 'red',
      showRefresh: true,
      bindingClick: true
    }
  },
  computed: {
    resetSize () {
      return resetSize
    }
  },
  methods: {
    init () {
      this.$nextTick(() => {
        this.refresh()
        this.$emit('ready', this)
      })
    },
    canvasClick (e) {
      this.checkPosArr.push(this.getMousePos(this.$refs.canvas, e))
      if (this.num === this.checkNum) {
        this.num = this.createPoint(this.getMousePos(this.$refs.canvas, e))
        setTimeout(() => {
          let flag = this.comparePos(this.fontPos, this.checkPosArr)
          if (flag === false) {
            // 验证失败
            this.$emit('error', this)
            this.barAreaColor = '#d9534f'
            this.barAreaBorderColor = '#d9534f'
            this.text = '验证失败'
            this.msgColor = 'red'
            setTimeout(() => {
              this.refresh()
            }, 500)
          } else {
            // 验证成功
            this.barAreaColor = '#4cae4c'
            this.barAreaBorderColor = '#5cb85c'
            this.text = '验证成功'
            this.msgColor = 'green'
            this.showRefresh = false
            this.bindingClick = false
            this.$emit('success', this)
          }
        }, 500)
      }
      if (this.num < this.checkNum) {
        this.num = this.createPoint(this.getMousePos(this.$refs.canvas, e))
      }
    },
    // 绘制合成的图片
    drawImg: function (obj, img) {
      // 准备canvas环境
      let ctx = this.$refs.canvas.getContext('2d')
      // 绘制图片
      ctx.drawImage(img, 0, 0, parseInt(this.setSize.imgWidth), parseInt(this.setSize.imgHeight)
      )
      // 绘制水印
      const fontSizeArr = [
        'italic small-caps bold 20px microsoft yahei',
        'small-caps normal 25px arial',
        '34px microsoft yahei'
      ]
      // 验证字颜色
      const codeColor2 = [
        '#FF0033',
        '#006699',
        '#993366',
        '#FF9900',
        '#66CC66',
        '#FF33CC'
      ]
      // 不重复的汉字
      const avg = Math.floor(parseInt(this.setSize.imgWidth) / (parseInt(this.defaultNum) + 1))
      let fontChars = []
      let color2Num = Math.floor(Math.random() * 5)
      for (let i = 1; i <= this.defaultNum; i++) {
        fontChars[i - 1] = this.getChars(this.fontStr, fontChars)
        let tmpIndex = Math.floor(Math.random() * 3)
        ctx.font = fontSizeArr[tmpIndex]
        ctx.fillStyle = codeColor2[color2Num]
        let tmpY
        if (Math.floor(Math.random() * 2) === 1) {
          tmpY = Math.floor(parseInt(this.setSize.imgHeight) / 2) + tmpIndex * 20 + 20
        } else {
          tmpY = Math.floor(parseInt(this.setSize.imgHeight) / 2) - tmpIndex * 20
        }
        ctx.fillText(fontChars[i - 1], avg * i, tmpY)
        this.fontPos[i - 1] = { char: fontChars[i - 1], x: avg * i, y: tmpY }
      }
      for (let i = 0; i < this.defaultNum - this.checkNum; i++) {
        this.shuffle(this.fontPos).pop()
      }
      let msgStr = ''
      for (let i = 0; i < this.fontPos.length; i++) {
        msgStr += this.fontPos[i].char + ','
      }
      this.text = '请顺序点击【' + msgStr.substring(0, msgStr.length - 1) + '】'
      return this.fontPos
    },
    // 获取坐标
    getMousePos: function (obj, e) {
      let x = e.offsetX - 5
      let y = e.offsetY - 5
      return { x, y }
    },
    // 递归去重
    getChars: function (fontStr, fontChars) {
      let rand = parseInt(Math.floor(Math.random() * fontStr.length))
      if (rand > 0) {
        rand = rand - 1
      }
      let randChar = fontStr.charAt(rand)
      if (fontChars.indexOf(randChar) === -1) {
        return randChar
      } else {
        return this.getChars(fontStr, fontChars)
      }
    },
    // 洗牌数组
    shuffle: function (arr) {
      let m = arr.length
      let i
      let tmpF
      while (m) {
        i = (Math.random() * m--) >>> 0
        tmpF = arr[m]
        arr[m] = arr[i]
        arr[i] = tmpF
      }
      return arr
    },
    // 创建坐标点
    createPoint: function (pos) {
      this.tempPoints.push(Object.assign({}, pos))
      return ++this.num
    },
    comparePos: function (fontPos, checkPosArr) {
      let flag = true
      for (let i = 0; i < fontPos.length; i++) {
        if (!(parseInt(checkPosArr[i].x) + 40 > fontPos[i].x &&
            parseInt(checkPosArr[i].x) - 40 < fontPos[i].x &&
            parseInt(checkPosArr[i].y) + 40 > fontPos[i].y &&
            parseInt(checkPosArr[i].y) - 40 < fontPos[i].y
        )
        ) {
          flag = false
          break
        }
      }
      return flag
    },
    refresh () {
      // 加载页面
      this.fontPos.splice(0, this.fontPos.length)
      this.checkPosArr.splice(0, this.checkPosArr.length)
      this.num = 1
      this.imgRand = Math.floor(Math.random() * this.imgUrls.length)
      this.setSize = this.resetSize(this)
      this.tempPoints.splice(0, this.tempPoints.length)
      this.barAreaColor = '#000'
      this.barAreaBorderColor = '#ddd'
      this.msgColor = 'red'
      this.bindingClick = true
      this.fontPos.splice(0, this.fontPos.length)
      this.checkPosArr.splice(0, this.checkPosArr.length)
      this.num = 1
      this.imgRand = Math.floor(Math.random() * this.imgUrls.length)
      // 随机的背景图片
      let img = new Image()
      img.src = this.imgUrls[this.imgRand]
      // 加载完成开始绘制
      let _this = this
      img.onload = function (e) {
        _this.$nextTick(() => {
          _this.fontPos = _this.drawImg(_this.$el, this)
        })
      }
      this.text = '验证失败'
      this.showRefresh = true
    },
    getVerifyCode () {
      // 获取已选中的验证码字符
      let verifyCode = ''
      let size = this.fontPos.length
      for (let i = 0; i < size; i++) {
        verifyCode += this.fontPos[i].char
        if (i < size - 1) {
          verifyCode += ','
        }
      }
      return verifyCode
    }
  },
  watch: {
    // type变化则全面刷新
    type: {
      immediate: true,
      handler () {
        this.init()
      }
    }
  },
  mounted () {
    // 禁止拖拽
    this.$el.onselectstart = function () {
      return false
    }
  }
}
</script>

<style lang="less" scoped>
/*滑动验证码*/
.verify-bar-area {
    position: relative;
    background: #FFFFFF;
    text-align: center;
    box-sizing: content-box;
    border: 1px solid #ddd;
    border-radius: 4px;
}
.verify-img-panel {
  margin: 0;
  -webkit-box-sizing: content-box;
  -moz-box-sizing: content-box;
  box-sizing: content-box;
  border: 1px solid #ddd;
  border-radius: 3px;
  position: relative;
}
.verify-img-panel .verify-refresh {
  width: 25px;
  height: 25px;
  text-align: center;
  padding: 5px;
  cursor: pointer;
  position: absolute;
  top: 0;
  right: 0;
  z-index: 2;
}
.verify-img-panel .icon-refresh {
  font-size: 20px;
  color: #fff;
}
.verify-img-panel .verify-gap {
  background-color: #fff;
  position: relative;
  z-index: 2;
  border: 1px solid #fff;
}
.iconfont {
  font-family: "iconfont" !important;
  font-size: 16px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
