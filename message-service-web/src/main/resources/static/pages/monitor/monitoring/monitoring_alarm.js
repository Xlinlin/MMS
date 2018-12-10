/*
  *  名称：监控报警
  *  路径：monitor/monitoring/monitoring_alarm.js
  *  开发人：李秀敏
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-monitoring-monitoring_alarm',
        data:function(){
            return {
                addMonitor:false,
                page:new app.Pager(),
                tableAptHeight:'',
                consumerList:[],
                topicList:[],
                addMonitorForm:{},
                columns: [
                    {title: 'Consumer ID', key: 'consumerId', align: 'center'},
                    {title: 'Topic', key: 'topic', align: 'center'},
                    {title: '权限', key: 'authority', align: 'center'},
                    {title: '堆积量阈值', key: 'desc', align: 'center'},
                    {title: '状态', key: 'status', align: 'center'},
                    {title: '联系人', key: 'status', align: 'center'},
                    {
                     title: '操作', key: 'operation',width:200, align: 'center', fixed: 'right',
                     render:(h, params) => {return h( 'div', this.$refs.table.$scopedSlots.action(params)  )}
                    }
                ],
               ruleValidate:[],
            }
        },
        methods: {
            onChangeHeight(hh){
                app.acptTableHeight(this,hh+30);
            },
            changePage(p){
                this.query(p);
            },
            changePageSize(s){
                this.query(null,s);
            },
            submitMonitor(modal){

            },
            editCancel(){

            }
        },
        activated(){
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function(){
                app.acptTableHeight(this,hh+30);
            });
        },
        mounted(){

        }
    });

})(App);