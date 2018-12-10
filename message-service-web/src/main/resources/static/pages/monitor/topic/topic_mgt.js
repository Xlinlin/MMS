/*
  *  名称：Topic管理
  *  路径：monitor/topic/topic_mgt.js
  *  开发人：李秀敏
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-topic-topic_mgt',
        data:function(){
            return {
                searchForm: {},
                addTopicForm:{},
                addCustomerForm:{},
                tableAptHeight:'',
                delData:{},
                page:new app.Pager(),
                addTopic:false,
                addCustomerModal:false,
                delModel:false,
                search:true,
                statusList:[
                    {value:'0',label:'未使用'},
                    {value:'1',label:'使用中'},
                    {value:'2',label:'已停用'},
                ],
                columns: [
                    {title: 'Topic', key: 'topic', align: 'center'},
                    {title: '状态', key: 'status', align: 'center',render:(h,param) => { return  h('span', param.row.status == 2 ? '已停用' : (param.row.status == 1 ?'使用中':'未使用')); }},
                    {title: '创建时间', key: 'createTime', align: 'center'},
                    {title: '描述', key: 'desc', align: 'center'},
                     {
                    title: '操作', key: 'operation',width:240, align: 'center', fixed: 'right',
                    render:(h, params) => {return h( 'div', this.$refs.table.$scopedSlots.action(params)  )}
                    }
        ],
            ruleValidate:{
                topic:[{ required: true, message: '请输入Topic',trigger: 'blur'}],consumerId:[{ required: true, message: '请输入Consumer ID',trigger: 'blur'}]
            },
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
                this.query(p);
            },
            changePageSize(s){
                this.query(null,s);
            },
            format(time){
                var date = new Date(time);
                let dateMonth=(date.getMonth()+1).toString().length==1?'0'+(date.getMonth()+1):(date.getMonth()+1)
                let dateDate =date.getDate().toString().length==1?'0'+date.getDate():date.getDate()
                return date.getFullYear() + '-' + dateMonth + '-' + dateDate;
            },
            query(num,size){
                var that=this;
                that.searchForm.pageNum=num||that.page.pageNum;
                that.searchForm.pageSize=size||that.page.size;
                if(!App.isEmpty(that.searchForm.stopTime)){
                    that.searchForm.stopTime=this.format(that.searchForm.stopTime);
                }
                if(!App.isEmpty(that.searchForm.startTime)){
                    that.searchForm.startTime=this.format(that.searchForm.startTime);
                }
                app.request('/api/topic/pageTopic').setData(that.searchForm).post().callSuccess(function(res){
                    that.page=new app.Pager(res.data);
                })
            },
            submitTopic(modal) {
                this.$refs.topicForm.validate((valid) => {
                    if (valid) {
                        var that = this;
                        app.request('/api/topic/save').setData(that.addTopicForm).post().callSuccess(function (res) {
                            if (res.data == null&&res.code==200) {
                                that.$Message.success("创建成功")
                            } else {
                                that.$Message.error("创建失败")
                            }
                            modal.close();
                            that.addTopicForm={};
                            that.query();
                        });
                    }
                })
            },
            updateStatusById(props){
                var that=this;
                var data = $.extend(true,{},props);
                var status=data.status==1?0:1;
                app.request('/api/topic/enable',{topicId:data.id,status:status}).post().callSuccess(function(res){
                    if (res.data == null&&res.code==200) {
                        if(status==1){
                            that.$Message.success("启用成功")
                        }else if(status==0){
                            that.$Message.success("禁用成功")
                        }
                    } else {
                        that.$Message.error("操作失败")
                    }
                    that.query(that.searchForm.pageNum,that.searchForm.pageSize);
                })

            },
            addCustomer(props){
                var that=this;
                var selData = props ? [props] : this.$refs.table.getSelection();
                selData.forEach(function(t){
                    that.addCustomerForm=t;
                });
                that.addCustomerModal=true;
            },
            submitCustomer(modal) {
                this.$refs.customerForm.validate((valid) => {
                    if (valid) {
                        var that = this;
                        app.request('/api/consumer/save').setData(that.addCustomerForm).post().callSuccess(function (res) {
                            modal.close();
                            app.putData('customerTopic',that.addCustomerForm.topic);
                            app.openModule("customer_mgt", '消费者管理', 'monitor/customer/customer_mgt.html');
                            that.addCustomerForm={};
                        });
                    }
                })
            },
            editCancel(){
                var that = this;
                setTimeout(function(){
                    that.addTopicForm = {};
                    that.addCustomerForm={};
                },500)
            },
            checkById(props){
                var that=this;
                var selData = props ? [props] : this.$refs.table.getSelection();
                selData.forEach(function(t){
                    that.msgSearchForm=t;
                });
                app.putData('customerTopic',that.msgSearchForm.topic);
                app.openModule("customer_mgt", '消费者管理', 'monitor/customer/customer_mgt.html');
            }
        },
        mounted(){
            this.$nextTick(function(){
                this.query();
            });
        }
    });

})(App);