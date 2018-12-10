/*
  *  名称：Broker资源管理
  *  路径：monitor/sms/sms_mgt.js
  *  开发人：聂涛
  *  日期：2018/11/7
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {
    const statusMap={'1':'等待回执','2':'发送失败','3':'发送成功'};
    app.register({
        template: '#monitor-sms-sms_mgt',
        activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });

                this.query();
        },
        data:function(){
            return {
                tableAptHeight: '',
                queryData:{},
                page:'',
                sendData:{},
                sendModal:false,
                findGroupId:'',
                groupData:{},
                statusList:[ {
                    value: 1,
                    label: '等待回执'
                },
                    {
                        value: 2,
                        label: '发送失败'
                    },
                    {
                        value: 3,
                        label: '发送成功'
                    }],
                addForm:{},
                updateForm:'',
                deleteModal:false,
                updateModel:false,
                addModel:false,
                searchForm:{},
                columns: [
                    {
                        title: '序号',
                        width: 80,
                        type: 'index',
                        align: 'center'
                    },
                    {
                        title: '电话',
                        width:150,
                        key: 'phoneNum',
                        align: 'center'
                    },
                    {
                        title: '短信内容',

                        key: 'content',
                        align: 'center'
                    },
                    {
                        title: '发送时间',
                        width:200,
                        key: 'sendDate',
                        align: 'center'
                    },
                    {
                        title: '拓展字段',
                        key: 'outId',
                        width:180,
                        align: 'center'
                    },
                    {
                        title: '发送状态',
                        width:100,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', statusMap[params.row.sendStatus])
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        width: 130,
                        render: (h, params) => {
                            if(params.row.sendStatus==2){
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                debugger
                                                this.sendData={};
                                                this.sendData.phoneNum=params.row.phoneNum;
                                                this.sendModal=true;
                                            }
                                        }
                                    }, '发送')
                                ]);
                            }else{
                                return h('div','暂无操作')
                            }

                        }
                    }
                ],
                tableList:'',
                //校验
                ruleValidate:{
                    serverIp:[
                        {required:true,message:'请输入名称'}
                    ],
                    port:[
                        {required:true,message:'请输入名称'}
                    ],
                    desc:[
                        {required:true,message:'请输入描述信息'}
                    ],
                    serverGroupId:[
                        { required: true, message: '请选择GroupId', trigger: 'change' }
                    ]
                },
            }
        },
        methods: {
            onChangeHeight(hh) {
                app.acptTableHeight(this, hh);
            },
            onPageChange_pageSize(pageSize){
                this.queryData.pageSize= pageSize;
                this.query();
            },onChange_page(page){
                this.queryData.pageNum=page;
                this.query();
            },
            query(){
                var that =this;
                    app.request('/monitor/sms/sms_mgt/pageSms').setData(this.queryData).post().callSuccess(function(res) {
                        that.page = new app.Pager(res.data);
                    });
            },
            addButton(){
                this.addForm={};
                this. addModel=true;
            },
            confirmSend(){
                var that =this;
                app.request('/monitor/sms/sms_mgt/send').setData(this.sendData).post().callSuccess(function(res) {
                    that.$Message.success('发送成功');
                });
            },
            queryServerIp(){
                this.query();
            },
            reset(){
                this.queryData={};
                this.query();
            }
        },
        mounted(){

        }
    });

})(App);