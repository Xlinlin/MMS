/*
  *  名称：资源报表
  *  路径：monitor/resource/resource_report.js
  *  开发人：逯思瑶
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-resource-resource_report',
        activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });
        },
        data:function(){
            return {
                tableAptHeight: '',
                formItem: {

                },
                topicList:[],
                collectionCycleList:[
                    {
                        value: 10,
                        label: '10分钟'
                    },
                    {
                        value: 30,
                        label: '30分钟'
                    },
                    {
                        value: 60,
                        label: '60分钟'
                    }
                ],
                tablColumns: [
                    {
                        title: '序号',
                        type: 'index',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: 'Topic',
                        key: 'topic',
                        value: 'topic',
                        width: 150
                    },
                    {
                        title: 'Message ID',
                        key: 'messgeId',
                        value: 'messgeId',
                        width: 150
                    },

                    {
                        title: 'Customer ID',
                        key: 'customerId',
                        value: 'customerId',
                        width: 300
                    },
                    {
                        title: 'Tag',
                        key: 'tag',
                        value: 'tag',
                        width: 150
                    },
                    {
                        title: 'key',
                        key: 'key',
                        value: 'key',
                        width: 200
                    },
                    {
                        title: '存储时间',
                        key: 'date',
                        value: 'date',
                        width: 150
                    },

                ],
                page: new app.Page(),
                ruleValidate: {
                    memberLevel: [
                        {required: true, message: '请输入/选择会员卡等级'},

                    ],
                    cardLevelName: [
                        {required: true, message: '请输入会员卡等级名称', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            onChangeHeight(hh) {
                app.acptTableHeight(this, hh);
            },
            reset() {
                this.formItem = {};
            },
            changePage(p) {
                this.page.pageNum = p;
                this.toQuery();
            },
            changePageSize(s) {
                this.page.pageSize = s;
                this.toQuery();
            },
            toQuery() {

            },
            selectDate(rang){
                this.formItem.startTime = this.formItem.dateRange[0].format('yyyy-MM-dd hh:mm:ss');
                this.formItem.endTime = this.formItem.dateRange[1].format('yyyy-MM-dd hh:mm:ss');
            },
        },
        mounted(){

        }
    });

})(App);