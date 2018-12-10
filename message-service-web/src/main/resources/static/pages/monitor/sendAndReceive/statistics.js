/*
  *  名称：收发统计
  *  路径：monitor/sendAndReceive/statistics.js
  *  开发人：杨婷婷
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-sendAndReceive-statistics',activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });
        },

        data:function(){
            return {
                tableAptHeight: '',
                searchForm:{},
            }
        },
        methods: {
            onChangeHeight(hh) {
                app.acptTableHeight(this, hh);
            },
        },
        mounted(){

        }
    });

})(App);