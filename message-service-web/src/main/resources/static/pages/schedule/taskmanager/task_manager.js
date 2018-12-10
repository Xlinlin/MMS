/*
  *  名称：任务管理
  *  路径：schedule/taskmanager/task_manager.js
  *  开发人：聂涛
  *  日期：2018/11/2
*/
/* 使用闭包，对变量作用域隔离*/
(function (app) {

    app.register({
        template: '#schedule-taskmanager-task_manager',
        activated() {
            var hh = this.$refs.custLayout.returnHeadHeight();
            this.$nextTick(function () {
                app.acptTableHeight(this, hh);
            });
            this.queryModel()
        },
        data: function () {
            return {
                tableAptHeight: '',
                queryData:{},
                params:'',
                page:'',
                deleteModal:false,
                deleteData:'',
                executeModal:false,
                cityList:[],
                status:[
                    {
                        value: '0',
                        label: '停止'
                    },
                    {
                        value: '1',
                        label: '运行'
                    }
                ],
                moduleList:[],
                addForm:{},
                updateForm:'',
                updateModel:false,
                addModel:false,
                searchForm:{},
                autoStart:[
                    {value:0,label:'否'},{value:1,label:'是'}
                ],
                columns: [

                    {
                        title: '任务名称',
                        width:210,
                        key: 'name',
                        align: 'center'
                    },
                    {
                        title: '任务描述',
                        width:300,
                        key: 'description',
                        align: 'center'
                    },
                    {
                        title: '任务执行URL',
                        width:300,
                        key: 'url',
                        align: 'center'
                    },
                    {
                        title: '模块',
                        width:150,
                        key: 'modelName',
                        align: 'center',

                    },
                    {
                        title: 'Cron',
                        width:130,
                        key: 'cronExp',
                        align: 'center'
                    },
                    {
                        title: '创建人',
                        width:100,
                        key: 'creator',
                        align: 'center'
                    },
                    {
                        title: '自动启动',
                        width:100,
                        align: 'center',
                         render: (h, params) => {return h('div', params.row.autoStart==0?'否':params.row.autoStart==1?'是':'')}
                    },
                    {
                        title: '创建时间',
                        width:180,
                        key: 'createTime',
                        align: 'center'
                    },
                    {
                        title: '更新人',
                        key: 'updater',
                        width:80,
                        align: 'center'
                    },
                    {
                        title: '更新时间',
                        key: 'updateTime',
                        width:180,
                        align: 'center'
                    },
                    {
                        title: '状态',
                        width:100,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', params.row.status==0?'停止':params.row.status==1?'运行':'删除')
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        fixed: 'right',
                        align: 'center',
                        width: 160,
                        render: (h, params) => {
                            return h( 'div', this.$refs.table.$scopedSlots.actions(params))
                        }

                    }
                ],
                tableList:'',
                //校验
                ruleValidate:{
                    name:[
                        {required:true,message:'请输入名称'}
                    ],
                    description:[
                        {required:true,message:'请输入描述信息'}
                    ],
                    url:[
                        {required:true,message:'请输入任务执行URL'}
                    ],
                    module:[
                        {required:true,message:'请输入模块'}
                    ],
                    autoStart:[
                        {required:true,message:'请选择自动启动'}
                    ],
                    cronExp:[
                        {required:true,message:'请输入cron表达式'}
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
                app.request('/schedule/taskmanager/task_manager/list/page').setData(this.queryData).post().callSuccess(function(res) {
                    debugger;
                    res.data.content.forEach(items=>{
                        that.moduleList.forEach(item=>{
                            if(item.modelCode==items.module){
                                items.modelName=item.message
                            }
                                })
                    });

                    that.page = new app.Pager(res.data);
                });
            },
            addButton(){
                this.addForm={};
                this. addModel=true;
            },
            addSave(){
                var that =this;
                debugger;
                this.$refs.addForm.validate((valid) => {
                    if(valid){
                        app.request('/schedule/taskmanager/task_manager/save').setData(this.addForm).post().callSuccess(function(res) {
                            that.$Message.success('新增成功！');
                            that.addModel=false;
                            that.query();
                        });
                    }
                })

            },
            updateSave(){
                var that =this;
                debugger;
                this.$refs.updateForm.validate((valid) => {
                    if(valid){
                        app.request('/schedule/taskmanager/task_manager/update').setData(this.updateForm).post().callSuccess(function(res) {
                            that.$Message.success('修改成功！');
                            that.updateModel=false;
                            that.query();
                        });
                    }
                })
            },
            queryModel(){
                var that =this;
                app.request('/schedule/taskmanager/task_manager/get/all/model').callSuccess(function(res) {
                    that.moduleList=res.data;
                });
            },
            confirmDelete(){
                var that =this;
                app.request('/schedule/taskmanager/task_manager/delete/'+this.deleteData+'/admin').callSuccess(function(res) {
                    that.$Message.success('删除成功！');
                    that.deleteModal=false;
                    that.query();
                });
            },
            deleteButton(row){
                this.deleteModal=true;
                this.deleteData=row.id;
            },
            startAndDisable(params){
                var that =this;
                let ids=params.row.id;
                let updater=params.row.updater;
                let type =params.row.status==0?1:0;
                app.request('/schedule/taskmanager/task_manager/operate',{ids:ids,updater:updater,type:type}).post().callSuccess(function(res) {
                    that.$Message.success('启动/停止成功！');
                    that.query();
                });
            },
            execuseButton(params){
                this.params=params;
                this.executeModal=true;
            },
            confirmExecute(){
                var that =this;
                let ids=this.params.row.id;
                let updater=this.params.row.updater;
                app.request('/schedule/taskmanager/task_manager/run/now/'+ids+'/'+updater).callSuccess(function(res) {
                    that.$Message.success('执行成功！');
                });
            },
            updateButton(row){
                if(row.status!=0){
                    this.$Message.error('状态为停止时才能修改');
                }else{
                    debugger;
                    this.updateForm=row;
                    this.updateModel=true;
                }

            },

        },
        mounted(){
            this.$nextTick(function(){

                this.query();
            });
        }
    });

})(App);