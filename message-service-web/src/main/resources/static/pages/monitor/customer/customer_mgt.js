/*
  *  名称：消费者管理
  *  路径：monitor/groupId/groupId_mgt.js
  *  开发人：#{dev}
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {
    const customerStatusMap={'0':'离线','1':'在线'};
    app.register({
        template: '#monitor-customer-customer_mgt',

        data:function(){
            return {
                tableAptHeight: '',
                searchForm:{},
                deleteModal:false,
                deleteProps:'',
                topicList:[],
                page:new app.Pager(),
                tableList:[],
                topic:'',
                columns:[
                    { title: 'Consumer ID',align: 'center', key: 'consumerId'},
                    { title: 'Tiopic',align: 'center', key: 'topic'},
                    { title: '创建时间',align: 'center', key: 'createTime'},
                    { title: '在线状态',align: 'center', key: 'online',
                        render:(h,params) => {
                            return h('div',customerStatusMap[params.row.online]);
                        }
                    },
                    {
                        title: '操作', key: 'operation',width:100, align: 'center', fixed: 'right',
                        render:(h, params) => {return h( 'div', this.$refs.table.$scopedSlots.action(params)  )},
                    }
                ]
            }
        },
        methods: {
            onChangeHeight(hh) {
                app.acptTableHeight(this, hh);
            },
            onPageChange_pageSize(){

            },onChange_page(){

            },
            //查询topic
            queryMessage(){
                var that = this;
                app.request('/api/topic/pageTopic').setData({
                    pageNum:1,
                    pageSize:2147483646}).post().callSuccess(function(res){
                    var data = res.data.content;
                    that.topicList=[];
                    data.forEach(function(item){
                        let topic={};
                        topic.value =item.topic;
                        topic.label =item.topic;
                        that.topicList.push(topic);
                    })
                })
            },
            query(){
                var that=this;
                app.request("/api/consumer/pageConsumer").setData(this.searchForm).post().callSuccess(
                    function (res) {
                        console.info(res.data);
                        that.page=new app.Pager(res.data);

                        // that.searchForm={};
                        // that.data=res.data;
                    })
            },
            changePage(p){
                this.page.pageNum = p;
                this.query();
            },
            changePageSize(s){
                this.page.pageSize =s;
                this.query();
            },
            //删除
            toDelete(props){
                this.deleteModal=true;
                this.deleteProps=props;

            },
            confirmDelete(){
                var that=this;

                app.request("/api/consumer/delete/"+ this.deleteProps.consumerId).callSuccess(
                    function (res) {
                        if(res.data==true){
                            App.success("删除成功")
                        }else{
                            App.error("删除失败")
                        }
                        that.query();
                    })
            }
        },
        activated :function(){
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function(){
                app.acptTableHeight(this, hh);
                this.topic=app.getData("customerTopic");
                this.searchForm={};
                if (this.topic!=undefined&&this.topic!='') {
                    this.searchForm.topic=this.topic;
                    this.query();
                }



            });

        },
        mounted(){
            this.$nextTick(function(){
                this.queryMessage();
            });
        }

    });

})(App);