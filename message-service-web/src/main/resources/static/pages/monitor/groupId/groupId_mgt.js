/*
  *  名称：消息查询
  *  路径：monitor/groupId/groupId_mgt.js
  *  开发人：聂涛
  *  日期：2018/10/29
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#monitor-groupId-groupId_mgt',
        activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });
        },
        data: function () {
            return {
                tableAptHeight: '',
                queryData:{},
                page:'',
                addForm:{},
                updateForm:'',
                deleteData:'',
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
                        title: '服务组ID',
                        width: 350,
                        key: 'id',
                        align: 'center'
                    },
                    {
                        title: '组名称',
                        key: 'groupName',
                        width: 150,
                        align: 'center'
                    },
                    {
                        title: '描述',
                        width: 200,
                        key: 'desc',
                        align: 'center'
                    },
                    {
                        title: '创建时间',
                        width: 200,
                        key: 'createTime',
                        align: 'center'
                    },
                    {
                        title: '更新时间',
                        width: 200,
                        key: 'updateTime',
                        align: 'center'
                    },
                    {
                        title: '当前使用数量',
                        key: 'currentUseCount',
                        width: 130,
                        align: 'center'
                    },
                    {
                        title: '操作',
                        key: 'action', fixed: 'right',
                        align: 'center',
                        width: 140,
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

                                            this.deleteData=params.row.id;
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
                    groupName:[
                        {required:true,message:'请输入名称'}
                    ],
                    desc:[
                        {required:true,message:'请输入描述信息'}
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

                app.request('/monitor/groupId/groupId_mgt/findPage').setData(this.queryData).post().callSuccess(function(res) {

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
                      app.request('/monitor/groupId/groupId_mgt/create/group').setData(this.addForm).post().callSuccess(function(res) {
                          that.$Message.success('新增成功！');
                          that.addModel=false;
                          that.query();
                      });
                  }
                })

            },
            updateSave(){
                var that =this;

                this.$refs.updateForm.validate((valid) => {
                    if(valid){
                        app.request('/monitor/groupId/groupId_mgt/modify/group/'+that.updateForm.id).setData(this.updateForm).post().callSuccess(function(res) {
                            that.$Message.success('修改成功！');
                            that.updateModel=false;
                            that.query();
                        });
                    }
                })
            },
            confirmDelete(){
                var that =this;
                app.request('/monitor/groupId/groupId_mgt/delete/'+this.deleteData).setData(this.updateForm).post().callSuccess(function(res) {
                    that.$Message.success('删除成功！');
                    that.deleteModal=false;
                    that.query();
                });
            }

        },
        mounted(){
            this.query();
        }
    });

})(App);