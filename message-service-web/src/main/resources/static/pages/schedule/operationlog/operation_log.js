/*
  *  名称：调度日志
  *  路径：schedule/operationlog/operation_log.js
  *  开发人：李秀敏
  *  日期：2018/11/8
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#schedule-operationlog-operation_log',
        data: function () {
            return {
                tableAptHeight:'',
                searchForm:{},
                taskList:[],
                page:new app.Pager(),
                columns: [
                    {title: '任务名称', key: 'name', align: 'center'},
                    {title: '调度url', key: 'url', align: 'center'},
                    {title: '执行结果', key: 'result', align: 'center'},
                    {title: '失败原因', key: 'failureMsg', align: 'center',tooltip: true},
                    {title: '调度开始时间', key: 'dispatchStartTime', align: 'center'},
                    {title: '调度结束时间', key: 'dispatchEndTime', align: 'center'},
                    {title: '执行时长', key: 'executeTime', align: 'center'},
                ],
            }
        },
        activated(){
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function(){
                app.acptTableHeight(this,hh);
            });
        },
        methods: {
            onChangeHeight(hh){
                app.acptTableHeight(this,hh);
            },
            changePage(p){
                this.searchOperLogById(p);
            },
            changePageSize(s){
                this.searchOperLogById(null,s);
            },
            change(v){
                this.$emit('on-change',v);
            },
            queryEmptyTask(query){
                if ( App.isEmpty(query) ){
                    this.queryTask();
                }
            },
            queryTask() {

                var that = this;
                var $select = that.$refs.select;
                var word = $select.query;
                $select.loading = true;
                var curFlag = ++this.clearTimeFlag;
                //控制查询不要一个劲的去查，这样会很伤服务器
                setTimeout(function () {
                    if (curFlag < that.clearTimeFlag)
                        return;
                    that.clearTimeFlag = 0;
                    that.searchForm.name= $.trim(word);
                    that.searchForm.pageNum=1;
                    that.searchForm.pageSize=10;
                    App.request('/schedule/taskmanager/task_manager/list/page').setData(that.searchForm).post()
                        .callSuccess(function (res) {
                                that.taskList=res.data.content;
                        }).hideLoad().callComplete(function () {
                        $select.loading = false;
                    });
                }, 500);
            },
            searchOperLogById(num,size){
                var that=this;
                that.searchForm.taskId=that.searchForm.id;
                that.searchForm.pageSize=size||that.page.size;
                that.searchForm.pageNum=num||that.page.pageNum;

                App.request('/schedule/operation/log/operationLogList').setData(that.searchForm).post()
                    .callSuccess(function (res) {
                        that.page=new app.Pager(res.data);
                    });
            }
        },
        mounted(){
        }
    });

})(App);