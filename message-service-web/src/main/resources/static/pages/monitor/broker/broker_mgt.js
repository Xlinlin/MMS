/*
  *  名称：Broker资源管理
  *  路径：monitor/broker/sms_mgt.js
  *  开发人：聂涛
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-broker-broker_mgt',
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
                findGroupId:'',
                groupData:{pageSize:1000},
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
                        title: '服务组Id',
                        key: 'serverGroupId',
                        align: 'center'
                    },
                    {
                        title: '服务Ip',
                        width:150,
                        key: 'serverIp',
                        align: 'center'
                    },
                    {
                        title: '端口',
                        width:100,
                        key: 'port',
                        align: 'center'
                    },
                    {
                        title: '创建时间',
                        key: 'createTime',
                        width:180,
                        align: 'center'
                    },
                    {
                        title: '描述',
                        key: 'desc',
                        align: 'center'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        width: 130,
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.updateForm='';
                                            this.updateForm=params.row;
                                            this.updateModel=true;
                                        }
                                    }
                                }, '修改'),
                                h('Button', {
                                    props: {
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.deleteData=params.row.serverIp;
                                            this.deleteModal=true;
                                        }
                                    }
                                }, '删除')
                            ]);
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
                    app.request('/monitor/broker/broker_mgt/findPage').setData(this.queryData).post().callSuccess(function(res) {
                        that.page = new app.Pager(res.data);
                    });
            },
            addButton(){
                this.addForm={};
                this. addModel=true;
            },
            addSave(){
                var that =this;
                this.$refs.addForm.validate((valid) => {
                    if(valid){
                        app.request('/monitor/broker/broker_mgt/save').setData(this.addForm).post().callSuccess(function(res) {
                            that.$Message.success('新增成功！');
                            that.addModel=false;
                            that.query();
                        });
                    }
                })

            },
            updateSave(){
                var that =this;

                this.updateForm.serverIp=this.updateForm.serverIp+'.';
                this.$refs.updateForm.validate((valid) => {
                    if(valid){
                        app.request('/monitor/broker/broker_mgt/modify/'+that.updateForm.serverIp).setData(this.updateForm).post().callSuccess(function(res) {
                            that.$Message.success('修改成功！');
                            that.updateModel=false;
                            that.query();
                        });
                    }
                })
            },
            findButton(){
                this.row=undefined;
                this.query();
            },
            confirmDelete(){

                var that =this;
                let url ='/monitor/broker/broker_mgt/delete/'+this.deleteData+'.';
                app.request(url).post().callSuccess(function(res) {
                    that.$Message.success('删除成功！');
                    that.deleteModal=false;
                    that.query();
                });
            },
            queryServerIp(){
                this.query();
            },
            queryGroup(){
                var that =this;

                app.request('/monitor/groupId/groupId_mgt/findPage').setData(this.groupData).post().callSuccess(function(res) {

                    that.findGroupId=res.data.content;
                });
            }
        },
        mounted(){
            this.queryGroup();
        }
    });

})(App);