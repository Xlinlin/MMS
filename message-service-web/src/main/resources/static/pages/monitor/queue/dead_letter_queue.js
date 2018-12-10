/*
  *  名称：死信队列
  *  路径：monitor-queue-dead_letter_queue.js
  *  开发人：逯思瑶
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {
    const messageStatusMap={'0':'已接收','1':'已发送','2':'已消费','3':'消息异常','4':'死信'};
    app.register({
        template: '#monitor-queue-dead_letter_queue',
        activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });
        },
        data: function () {
            return {
                onDetails:false,
                tableAptHeight: '',
                formItem: {
                    topic:'',
                    startTime:'',
                    endTime:'',
                  //  dateRange:[]
                },
                topicList:[],
                detailItem:{},
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
                        key: 'messageId',
                        value: 'messageId',
                        width: 300
                    },
                    {
                        title: 'key',
                        key: 'key',
                        value: 'key',
                        width: 300
                    },
                    {
                        title: '状态',
                        key: 'status',
                        render:(h,params) => {
                            return h('div',messageStatusMap[params.row.status]);
                        },
                        width: 100
                    },
                    {
                        title: '创建时间',
                        key: 'createTime',
                        value: 'createTime',
                        width: 220
                    },
                    {
                        title: '操作',
                        key: 'handle',
                        width: 160,
                        align: 'center',
                        render: this.renderOperat
                    }

                ],
                page: new app.Page()

            }
        },
        methods: {
            renderOperat(h, params) {
                return h('div', [
                    h('Button', {
                        props: {
                            size: 'small',
                        },
                        on: {
                            click: () => {
                                this.send(params.row);
                            }
                        }

                    }, '发送'),
                    h('Button', {
                        props: {
                            size: 'small',
                        },
                        on: {
                            click: () => {
                                this.details(params)
                            }
                        }

                    }, '详情')
                ]);
            },
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
            // selectDate(rang){
            //     this.formItem.startTime = new Date(this.formItem.dateRange[0]);
            //     this.formItem.endTime = new Date(this.formItem.dateRange[1]);
            // },
            toQuery() {
                var that = this;
                app.request('/monitor/message/dead_queue/findPage',{
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    topic:this.formItem.topic,
                    startTime:this.formItem.startTime,
                    endTime:this.formItem.endTime,
                    status:4
                }).callSuccess(function (res) {
                    var data=res.data;
                    that.page = new app.Page(data);
                })
            },
            details(index){
                this.detailItem=index.row;
                this.detailItem.statuss = messageStatusMap[index.row.status];
                debugger;
                this.onDetails=true
            },
            //查询topic
            queryMessage(){
                var that = this;
                app.request('/api/topic/pageTopic').setData({
                    pageNum:1,
                    pageSize:2147483646
                }).post().callSuccess(function(res){
                    var data = res.data.content;
                    data.forEach(function(item){
                        let topic={};
                        topic.value =item.topic;
                        topic.label =item.topic;
                        that.topicList.push(topic);
                    })
                })
            },
        },
        mounted: function () {
            this.$nextTick(function(){
                //查询区域列表
                var that = this;
                that.queryMessage();
                that.toQuery();
            });
        }
    });

})(App);