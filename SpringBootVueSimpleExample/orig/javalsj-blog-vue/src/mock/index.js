import mockjs from 'mockjs'

//使用mockjs模拟数据
Mock.mock('/api/admin/menuitems', (req, res) => {
  return {
    data: data_menuitems
  }
})

var data_menuitems = [{
    path: '/home',
    name: '首页'
  },
  {
    name: '系统组件',
    child: [{
        name: '介绍',
        path: '/components'
      },
      {
        name: '功能类',
        child: [{
            path: '/components/permission',
            name: '详细鉴权'
          },
          {
            path: '/components/pageTable',
            name: '表格分页'
          }
        ]
      }
    ]
  }
]
