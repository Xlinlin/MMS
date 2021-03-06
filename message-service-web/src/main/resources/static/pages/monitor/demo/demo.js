/*
  *  名称：demo
  *  路径：monitor/demo/demo.js
  *  开发人：聂涛
  *  日期：2018/11/12
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-demo-demo',
        data:function(){
            return {
                inforCardData: [
                    { title: '新增用户', icon: 'md-person-add', count: 803, color: '#2d8cf0' },
                    { title: '累计点击', icon: 'md-locate', count: 23432, color: '#19be6b' },
                    { title: '新增问答', icon: 'md-help-circle', count: 142, color: '#ff9900' },
                    { title: '分享统计', icon: 'md-share', count: 657, color: '#ed3f14' },
                    { title: '新增互动', icon: 'md-chatbubbles', count: 12, color: '#E46CBB' },
                    { title: '新增页面', icon: 'md-map', count: 14, color: '#9A66E4' },
                    { title: '新增页面', icon: 'md-map', count: 14, color: '#9A66E4' }
                ],
                pieData: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ],
                barData: {
                    Mon: 13253,
                    Tue: 34235,
                    Wed: 26321,
                    Thu: 12340,
                    Fri: 24643,
                    Sat: 1322,
                    Sun: 1324
                }
            }
        },
        methods: {

        },
        mounted(){

        }
    });

})(App);