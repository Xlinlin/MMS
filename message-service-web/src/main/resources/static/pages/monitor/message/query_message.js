/*
  *  名称：消息查询
  *  路径：monitor/message/query_message.js
  *  开发人：杨婷婷
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {
    const messageStatusMap={'0':'已接收','1':'已发送','2':'已消费','3':'消息异常','4':'死信'};
    app.register({
        template: '#monitor-message-query_message',
        data:function(){
            return {
                tableAptHeight: '',
                searchForm:{},
                topicList:[],
                time:'',
                toContent:false,
                message:'',
                onDetails:false,
                onSend:false,
                pmgressbar:false,
                messageId:'',
                detailItem:{},
                topic:'',
                position:5,
                errorMsg0:'',
                errorMsg1:'',
                errorMsg2:'',
                errorMsg3:'',
                errorMsg4:'',
                page:new app.Pager(),
                statusList:[
                    {value:'0',label:'已接收'},
                    {value:'1',label:'已发送'},
                    {value:'2',label:'已消费'},
                    {value:'3',label:'消息异常'}
                ],
                columns:[
                    { title: 'Message ID',align: 'center', key: 'messageId'},
                    { title: '消息内容',align: 'center', key: 'message',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        size: 'small',
                                    },
                                    on: {
                                        click: () => {
                                            this.message=params.row.message;
                                            this.toContent=true;
                                        }
                                    }

                                }, '查看内容'),

                            ])
                        }
                    },
                    { title: '消息状态',align: 'center', key: 'status',
                        render:(h,params) => {
                            return h('div',messageStatusMap[params.row.status]);
                        }
                    },
                    { title: '主题',align: 'center', key: 'topic'},
                    { title: 'key',align: 'center', key: 'key'},
                    { title: '发送次数',align: 'center', key: 'sendCount'},
                    {
                        title: '操作',
                        key: '操作',
                        align: 'center',
                        fixed: 'right',
                        width: 150,
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        size: 'small',
                                    },
                                    style: {
                                        marginRight: '5px',
                                        display:(params.row.status==2)?"none":"inline-block",
                                    },
                                    on: {
                                        click: () => {
                                            // this.send(params.row);
                                            this.messageId=params.row.messageId;
                                            this.onSend=true;
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
                        }
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
            //详情
            details(index){
                this.detailItem=index.row;
                this.detailItem.statuss = messageStatusMap[index.row.status];
                this.onDetails=true
            },
            //查询topic
            queryMessage(){
                var that = this;
                that.topicList=[];
                app.request('/api/topic/pageTopic').setData({
                    pageNum:1,
                    pageSize:2147483646}).post().callSuccess(function(res){
                    var data = res.data.content;
                    data.forEach(function(item){
                        let topic={};
                        topic.value =item.topic;
                        topic.label =item.topic;
                        that.topicList.push(topic);
                    })
                })
            },
            tabClick(idx){
                this.searchForm.topic=undefined;
                this.page={};
            },
            query(num,size){
                var that=this;
                if(that.searchForm.startTime==''&&that.searchForm.endTime==''&&
                that.searchForm.topic==undefined&& that.searchForm.messageId==undefined&&
                    that.searchForm.key==undefined&&that.searchForm.status==undefined ){
                    App.error("请选择或输入查询条件");
                    return;
                }
                if(that.searchForm.topic==undefined&&(that.searchForm.startTime!=''||that.searchForm.endTime!=''||
                    that.searchForm.status!=undefined||that.searchForm.key!=undefined)){
                    App.error("选择时间，状态，key时必须先选择topic");
                    return;
                }
                that.searchForm.pageNum=that.page.pageNum;
                that.searchForm.pageSize=that.page.pageSize;

                if(!App.isEmpty(that.searchForm.startTime)){
                    that.searchForm.startTime=this.format(that.searchForm.startTime);
                }
                if(!App.isEmpty(that.searchForm.endTime)){
                    that.searchForm.endTime=this.format(that.searchForm.endTime);
                }
                app.request("/monitor/message/query_message/findPage").setData(this.searchForm).post().callSuccess(
                    function (res) {
                        console.info(res.data);
                        that.page=new app.Pager(res.data);
                    })
            },
            format(time){
                var date = new Date(time);
                let dateMonth=(date.getMonth()+1).toString().length==1?'0'+(date.getMonth()+1):(date.getMonth()+1)
                let dateDate =date.getDate().toString().length==1?'0'+date.getDate():date.getDate()
                let dateHours =date.getHours().toString().length==1?'0'+date.getHours():date.getHours()
                let dateMinutes=date.getMinutes().toString().length==1?'0'+date.getMinutes():date.getMinutes()
                let dateSeconds=date.getSeconds().toString().length==1?'0'+date.getSeconds():date.getSeconds()
                return date.getFullYear() + '-' + dateMonth + '-' + dateDate + ' ' + dateHours + ':' + dateMinutes + ':' + dateSeconds;
            },
            changePage(p){
                this.page.pageNum = p;
                this.query();
            },
            changePageSize(s){
                this.page.pageSize =s;
                this.query();
            },
            onOk1(){
                var that=this;
                app.request("/monitor/message/query_message/send",{messageId:this.messageId}).post().callSuccess(
                    function (res) {
                        that.$Message.success('发送成功!');
                        that.onSend=false;

                        // that.query();

                    })
            },
            onCancel1(){
                this.onSend=false;
            },
            toPmgress(){
                var that=this;
                debugger
                //'d8c77626-99ef-4d04-86fd-444f4ccdebe1'
                app.request("/monitor/message/query_message/trail",{messageId:that.detailItem.messageId}).post().callSuccess(
                    function (res) {
                        console.info(res.data)
                        for(var i=0;i<res.data.length;i++){

                            if(i==res.data.length-1){
                                if(i==0){
                                    that.errorMsg0=res.data[i].errorMsg+'\n'+res.data[i].receiveTime;
                                }else if(i==1){
                                    that.errorMsg0=res.data[i-1].receiveTime;
                                    that.errorMsg1=res.data[i].errorMsg+'\n'+res.data[i].sendTime;}
                                else if(i==2){
                                    that.errorMsg0=res.data[i-2].receiveTime;
                                    that.errorMsg1=res.data[i-1].sendTime;
                                    that.errorMsg2=res.data[i].errorMsg+'\n'+res.data[i].receiveTime;}
                                else if(i==3){
                                    that.errorMsg0=res.data[i-3].receiveTime;
                                    that.errorMsg1=res.data[i-2].sendTime;
                                    that.errorMsg2=res.data[i-1].receiveTime;
                                    that.errorMsg3=res.data[i].errorMsg+'\n'+res.data[i].sendTime;}
                                else if(i==4){
                                    that.errorMsg0=res.data[i-4].receiveTime;
                                    that.errorMsg1=res.data[i-3].sendTime;
                                    that.errorMsg2=res.data[i-2].receiveTime
                                    that.errorMsg3=res.data[i-1].sendTime;
                                    that.errorMsg4=res.data[i].errorMsg+'\n'+res.data[i].consumerTime;}
                            }
                        }
                        that.position= res.data.length-1
                    })

                that.pmgressbar=true;
            }
        },
        activated :function(){
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function(){
                app.acptTableHeight(this, hh);
                // this.topic=app.getData("messageTopic");
                // this.searchForm={};
                // if (this.topic!=undefined&&this.topic!='') {
                //     this.searchForm.topic=this.topic;
                //     this.query();
                // }



            });

        },
        mounted(){
            this.$nextTick(function(){
                this.queryMessage();
            });
        }
    });

})(App);