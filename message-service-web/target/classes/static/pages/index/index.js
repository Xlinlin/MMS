/**
 * 系统架构
 * @author zhdong
 * @date 2018/8/26
 */
/*********右侧菜单列表组件*********/
App.register({
    props:['menu'],
    template: '#side-menu-item'
});
/*********折叠后的菜单下拉组件*********/
App.register({
    template: '#collapsed-menu',
    props:{
        parentItem:{
            type:Object,
            default:function(){
                return {};
            }
        },
        children : {
            type:Array,
            default:function(){
                return [];
            }
        },
        hideTitle:{
            type:Boolean,
            default:function(){
                return true;
            }
        },
        subMenuCls:{
            type:String,
            default:function(){
                return 'drop-menu-a';
            }
        },
        posIndex:{
            type:Number,
            default:function(){
                return 100;
            }
        },
        rootIconSize:16,
        iconSize: 16,
        textColor:16
    },
    data :function() {
        return {
            placement: 'right-end'
        }
    },
    computed: {
        textColor:function() {
            return '#fff';
        }
    },
    methods:{
        handleClick :function(name) {
            this.$emit('click', name)
        },
        handleMousemove :function(event, children) {
            const pageY = event.pageY;
            const height = children.length * 38;
            const isOverflow = pageY + height < window.innerHeight;
            this.placement = isOverflow ? 'right-start' : 'right-end';
        }
    }
});

/*********折叠后的菜单菜单头*********/
App.register({
    props:{
        "list": Array,
        collapsed: {
            type: Boolean
        },
        theme: {
            type: String,
            default: 'dark'
        },
        rootIconSize: {
            type: Number,
            default: 22
        },
        iconSize: {
            type: Number,
            default: 16
        }
    },
    template: '#side-menu-head',
    computed: {
        textColor:function() {
            return this.theme === 'dark' ? '#fff' : '#495060'
        }
    },
    methods:{
        handleSelect :function(name) {
            this.$emit('click', name)
        }
    }
});

/*********导航列表组件*********/
App.register({
    template:'#tags-nav',
    data :function() {
        return {
            tagBodyLeft: 0,
            rightOffset: 45,
            outerPadding: 4
        }
    },
    name: 'TagsNav',
    props: ['list','value'],
    methods: {
        handleMouseScroll :function(e) {
            let type = e.type;
            let delta = 0;
            if (type === 'DOMMouseScroll' || type === 'mousewheel') {
                delta = (e.wheelDelta) ? e.wheelDelta : -(e.detail || 0) * 40
            }
            this.handleScroll(delta)
        },
        handleScroll :function(offset) {
            const outerWidth = this.$refs.scrollOuter.offsetWidth;
            const bodyWidth = this.$refs.scrollBody.offsetWidth;
            if (offset > 0) {
                this.tagBodyLeft = Math.min(0, this.tagBodyLeft + offset)
            } else {
                if (outerWidth < bodyWidth) {
                    if (this.tagBodyLeft < -(bodyWidth - outerWidth)) {
                        this.tagBodyLeft = this.tagBodyLeft
                    } else {
                        this.tagBodyLeft = Math.max(this.tagBodyLeft + offset, outerWidth - bodyWidth)
                    }
                } else {
                    this.tagBodyLeft = 0
                }
            }
        },
        handleClose :function(e,menuId) {
            this.$emit('on-close', 'single',menuId);
        },
        handleClick :function(item) {
            this.$emit('input', item)
        },
        handleTagsOption :function(type){
            this.$emit('on-close', type);
        },
        moveToHome:function(){
            this.tagBodyLeft = 0;
        },
        moveToView :function(tag) {
            let outerWidth = $(this.$refs.scrollOuter).outerWidth();
            let bodyWidth = $(this.$refs.scrollBody).outerWidth();
            var tagPos = $(tag).position();
            var tagleft = $(tag).position().left + $(tag).outerWidth() + 10;
            var visableLeft = tagleft + this.tagBodyLeft;
            var marginLeft = outerWidth-tagleft;
            //判断是否在可视范围内
            if ( visableLeft < outerWidth ){
                //不移动
            }else{
                if ( marginLeft < 0 ){
                    this.tagBodyLeft = marginLeft;
                }else{
                    this.tagBodyLeft = 0;
                }
            }
        }
    }
});


/*********导航下拉框菜单组件*********/
App.register({
    methods:{
        handleTagsOption :function(type){
            this.$emit('on-close', type);
        }
    },
    template: '#nav-drop-down'
});

/*********自定义树组件************/
App.register({
    template: '#common-tree',
    components:{
        'common-tree-node':  {
            template: '#common-tree-node',
            props:{
                item:Array,
                selectNode:Object,
                onSelect:Function
            },
            methods:{
                expendAndCloseNode:function () {
                    this.item.expended = !this.item.expended;
                    this.$forceUpdate();
                },
                nodeIcon:function(){
                    return this.item.expended ? 'md-remove' : 'md-add';
                },
                handlerSelectNode:function (item) {
                    this.onSelect(item);
                }
            }
        }
    },
    props:{
        data:Array,
        onSelect:Function
    },
    data:function(){
        return {
            selectNode:{}
        };
    },
    methods:{
        nodeIcon:function(item) {
            return item.expended ? 'md-remove' : 'md-add';
        },
        expendAndCloseNode:function (item) {
            item.expended = !item.expended;
        },
        handlerSelectNode:function (item) {
            this.selectNode = item;
            //选择钩子
            this.onSelect && this.onSelect(item);
        }
    }
});
/*********自定义树组件:end************/

/*********自定义布局组件:begin************/
App.register({
    template: '#cust-layout',
    data(){
        return {
            showQuery:true,
            zheIcon:'ios-arrow-down'
        };
    },
    props:["on-change-height",'hide-query','hide-header'],
    methods:{
        zheQilai(){
            if ( this.showQuery ){
                this.showQuery = false;
                this.zheIcon = 'ios-arrow-forward';
                //调用钩子
                this.onChangeHeight && this.onChangeHeight(this.headHeight());
            }else{
                this.showQuery = true;
                this.zheIcon = 'ios-arrow-down';
                //调用钩子
                this.onChangeHeight && this.onChangeHeight(this.returnHeadHeight());
            }
        },
        returnHeadHeight(){
            if ( this.hideQuery ) return  this.headHeight();

            var qh = $(this.$refs.query).outerHeight();
            return qh+this.headHeight();
        },
        headHeight(){
            if ( this.hideHeader ) return 6;
            var header = $(this.$refs.header).parent();
            return header.outerHeight()+6;
        }
    }
});
/*********自定义布局组件:end************/

/*********删除组件组件组件:begin************/
App.register({
    template: '#common-delete-modal',
    props:{
        value:{
            default:''
        },
        title:{
            default:'是否确认删除'
        },
        onOk :{
            type:Function
        },
        onCancel :{
            type:Function
        }
    },
    methods:{
        onCancel1(){
            this.value=false;
            this.$emit('input',this.value);
            this.onCancel && this.onCancel('cancel');
        },
        onOk1(){
            this.value=false;
            this.$emit('input',this.value);
            this.onOk && this.onOk('ok');
        }
    }
});
/*********删除组件组件组件:end************/

/*********提交框组件组件:begin************/
App.register({
    template: '#common-submit-modal',
    props:{
        value:{
            default:false
        },
        title:{
            default:'弹出框'
        },
        submit:{
            type:Function
        },
        cancel:{
            type:Function
        },
        submitName:{
            default:"提交"
        },
        cancelName:{
            default:"取消"
        },
        width:{
            default:520
        },
        styles:{
            default:{}
        }
    },
    methods:{
        close(){
            this.value=false;
            this.$emit('input',this.value)
        },
        showLoadding(){
            this.$refs.submit.loading = true;
        },
        hideLoading(){
            this.$refs.submit.loading = false;
        },
        modelSubmit(){
            if ( this.submit ){
                this.submit(this);
            }else{
                this.close();
            }
        },
        modelCancel(){
            this.close();
            this.cancel && this.cancel();
        }
    }
});
/*********提交框组件组件:end************/
/*********拖拽组件 begin*********/
function DragVo(parent){
    this.dropFromIndex = -1;
    this.dropToIndex = -1;
    this.showDrag = false;
    this.parent = parent;
    this.dragCell = function(h,params,attr){
        var that = this;
        return h('drag-cell',{
            props:{
                params:params,
                attr:attr,
                dataList:that.parent.dataList,
                dragVo:that
            }
        });
    }
}
App.register({
    template:"#drag-cell",
    props:{
        params:{
            type:Object,
            default:{}
        },
        dragVo:{
            type:DragVo
        },
        attr:""
    },
    data(){
        return {
            dropFromIndex:-1,
            dropToIndex:-1,
            showDrag:false
        }
    },
    computed:{
        dragStyle() {
            if ( this.params.index == this.dragVo.dropToIndex ){
                return {
                    border:"1px dotted",
                    opacity:"0.5"
                }
            }
        }
    },
    methods:{
        dragStart(e,index){
            this.dragVo.dropFromIndex = index;
        },
        dragEnter(e,index){
            if ( this.dragVo.dropFromIndex == index)
                return;
            this.dragVo.dropToIndex = index;
        },
        dragLeave(index){
            if ( this.dragVo.dropFromIndex == index)
                return;
            this.dragVo.dropToIndex = -1;
        },
        dragEnd(e){
            var that = this;
            var fromIndex = that.dragVo.dropFromIndex;
            var toIndex = that.dragVo.dropToIndex;
            var dataList = that.dragVo.parent.dataList;
            //进行将from插入到to前面
            var newList = [];
            var from = dataList[fromIndex];
            var to = dataList[toIndex];

            //判断是否插前还插后，如果是form<to，那么就插后，否则插前，等于则不动
            if ( fromIndex == toIndex || toIndex== -1 || fromIndex == -1 ){
                return;
            }
            var isBefore =   fromIndex > toIndex;

            $.each(dataList,function(i){
                if ( i == fromIndex){
                    //不添加
                }
                else if ( i == toIndex ){
                    //到to这里添加from和to
                    isBefore ? newList.push(from) : newList.push(to);
                    isBefore ? newList.push(to) : newList.push(from);
                }else{
                    newList.push(this);
                }
            });
            that.dragVo.parent.dataList = newList;
            that.dragVo.dropFromIndex = -1;
            that.dragVo.dropToIndex = -1;
        },
        showMove(e){
            this.dragVo.showDrag = true;
        },
        hideMove(){
            this.dragVo.showDrag = false;
        }
    }
});
/*********拖拽组件 end*********/
/*********店铺组件 begin*********/
App.register({
    template: "#shop",
    props:{
        value:{
            type:String,
            default:""
        },
        size:{
            type:String,
        },
    },
    data(){
        return {
            list:[]
        }
    },
    size:{
        type:String,
    },
    watch:{
        value(v){
            this.$emit('input',this.value)
        }
    },
    mounted(){
        this.load();
    },
    methods:{
        change(v){
            this.$emit('on-change',v);
        },
        load(callback){
            var that =this;
            App.request('/api/mpc/mall_store_mgt/mallStoreList')
                .hideLoad()
                .useCache()
                .callSuccess(function(res){
                    that.list = res.data;
                    callback && callback();
                }).handError(function(){});
        }
    }
});
/*********店铺组件 end*********/

/*********栏目组件 begin*********/
App.register({
    template: "#catalog",
    props:{
        value:{
            type:String,
            default:""
        },
        size:{
            type:String,
        },
        shopCode:{
            type:String,
            default:"-1000"
        }
    },
    data(){
        return {
            list:[]
        }
    },
    watch:{
        shopCode(){
            this.load()
        }
    },
    mounted(){
        this.load()
    },
    methods:{
        change(v){
            this.$emit('on-change',v);
            this.$emit('input',v)
        },
        load(callback){
            var that =this;
            App.request({
                url:'/api/mpc/shopdec/catalog/list',
                data:{
                    status:0,
                    shopId:this.shopCode || "-1000"
                },
                async:false
            }).hideLoad().useCache().callSuccess(function(res){
                that.list = res.data;
                callback && callback()
            }).handError(function(){}); //不显示错误
        },
        setShop(shopCode){
            this.shopCode = shopCode;
        }
    }
});
/*********店铺组件 end*********/

/*********柱状图组件 begin*********/
App.register({
    template: "#bar",
    props:{
        value: Object,
        text: String,
        subtext: String
    },
    mounted(){
        this.$nextTick(() => {
            let xAxisData = Object.keys(this.value)
            let seriesData = Object.values(this.value)
            let option = {
                title: {
                    text: this.text,
                    subtext: this.subtext,
                    x: 'center'
                },
                xAxis: {
                    type: 'category',
                    data: xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: seriesData,
                    type: 'bar'
                }]
            }
            let dom = echarts.init(this.$refs.dom1, 'light')
            dom.setOption(option)
        })
    },
    methods:{
        loadData(){
            let xAxisData = Object.keys(this.value)
            let seriesData = Object.values(this.value)
            let option = {
                title: {
                    text: this.text,
                    subtext: this.subtext,
                    x: 'center'
                },
                xAxis: {
                    type: 'category',
                    data: xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: seriesData,
                    type: 'bar'
                }]
            }
            let dom = echarts.init(this.$refs.dom1, 'light')
            dom.setOption(option)
        }
    }
});
/*********柱状图组件 end*********/

/*********饼状图组件 begin*********/
App.register({
    template: "#pie",
    props: {
        value: Array,
        text: String,
        subtext: String
    },
    mounted(){
        this.$nextTick(() => {
            let legend = this.value.map(_ => _.name)
            let option = {
                title: {
                    text: this.text,
                    subtext: this.subtext,
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: legend
                },
                series: [
                    {
                        type: 'pie',
                        radius: '55%',
                        center: ['60%', '60%'],
                        data: this.value,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            }
            let dom = echarts.init(this.$refs.dom2, 'light')
            dom.setOption(option)
        })
    },
});
/*********饼状图组件 end*********/

/*********数量统计组件 begin*********/
App.register({
    template: "#InforCard",
    props: {
        left: {
            type: Number,
            default: 30
        },
        color: {
            type: String,
            default: '#2d8cf0'
        },
        icon: {
            type: String,
            default: ''
        },
        iconSize: {
            type: Number,
            default: 20
        },
        shadow: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        leftWidth () {
            return `${this.left}%`
        },
        rightWidth () {
            return `${100 - this.left}%`
        }
    }
});
/*********数量统计组件 end*********/

/*********icon组件 begin*********/
App.register({
    template: "#CommonIcon",
    props: {
        type: {
            type: String,
            required: true
        },
        color: String,
        size: Number
    },
    computed: {
        iconType () {
            return this.type.indexOf('_') === 0 ? 'Icons' : 'Icon'
        },
        iconName () {
            return this.iconType === 'Icons' ? this.getCustomIconName(this.type) : this.type
        },
        iconSize () {
            return this.size || (this.iconType === 'Icons' ? 12 : undefined)
        },
        iconColor () {
            return this.color || ''
        }
    },
    methods: {
        getCustomIconName (iconName) {
            return iconName.slice(1)
        }
    }
});
/*********icon组件 end*********/

/*********icons组件 begin*********/
App.register({
    template: "#Icons",
    props: {
        type: {
            type: String,
            required: true
        },
        color: {
            type: String,
            default: '#5c6b77'
        },
        size: {
            type: Number,
            default: 16
        }
    },
    computed: {
        styles () {
            return {
                fontSize: `${this.size}px`,
                color: this.color
            }
        }
    }
});
/*********icons组件 end*********/

/*********CountTo组件 begin*********/
App.register({
    template: "#CountTo",
    props: {
        init: {
            type: Number,
            default: 0
        },
        /**
         * @description 起始值，即动画开始前显示的数值
         */
        startVal: {
            type: Number,
            default: 0
        },
        /**
         * @description 结束值，即动画结束后显示的数值
         */
        end: {
            type: Number,
            required: true
        },
        /**
         * @description 保留几位小数
         */
        decimals: {
            type: Number,
            default: 0
        },
        /**
         * @description 分隔整数和小数的符号，默认是小数点
         */
        decimal: {
            type: String,
            default: '.'
        },
        /**
         * @description 动画持续的时间，单位是秒
         */
        duration: {
            type: Number,
            default: 2
        },
        /**
         * @description 动画延迟开始的时间，单位是秒
         */
        delay: {
            type: Number,
            default: 0
        },
        /**
         * @description 是否禁用easing动画效果
         */
        uneasing: {
            type: Boolean,
            default: false
        },
        /**
         * @description 是否使用分组，分组后每三位会用一个符号分隔
         */
        usegroup: {
            type: Boolean,
            default: false
        },
        /**
         * @description 用于分组(usegroup)的符号
         */
        separator: {
            type: String,
            default: ','
        },
        /**
         * @description 是否简化显示，设为true后会使用unit单位来做相关省略
         */
        simplify: {
            type: Boolean,
            default: false
        },
        /**
         * @description 自定义单位，如[3, 'K+'], [6, 'M+']即大于3位数小于6位数的用k+来做省略
         *              1000即显示为1K+
         */
        unit: {
            type: Array,
            default () {
                return [[3, 'K+'], [6, 'M+'], [9, 'B+']]
            }
        },
        countClass: {
            type: String,
            default: ''
        },
        unitClass: {
            type: String,
            default: ''
        }
    },
    data () {
        return {
            counter: null,
            unitText: ''
        }
    },
    computed: {
        counterId () {
            return `count_to_${this._uid}`
        }
    },
    methods: {
        getHandleVal (val, len) {
            return {
                endVal: parseInt(val / Math.pow(10, this.unit[len - 1][0])),
                unitText: this.unit[len - 1][1]
            }
        },
        transformValue (val) {
            let len = this.unit.length
            let res = {
                endVal: 0,
                unitText: ''
            }
            if (val < Math.pow(10, this.unit[0][0])) res.endVal = val
            else {
                for (let i = 1; i < len; i++) {
                    if (val >= Math.pow(10, this.unit[i - 1][0]) && val < Math.pow(10, this.unit[i][0])) res = this.getHandleVal(val, i)
                }
            }
            if (val > Math.pow(10, this.unit[len - 1][0])) res = this.getHandleVal(val, len)
            return res
        },
        getValue (val) {
            let res = 0
            if (this.simplify) {
                let { endVal, unitText } = this.transformValue(val)
                this.unitText = unitText
                res = endVal
            } else {
                res = val
            }
            return res
        }
    },
    mounted () {
        this.$nextTick(() => {
            let endVal = this.getValue(this.end)
            this.counter = new CountUp(this.counterId, this.startVal, endVal, this.decimals, this.duration, {
                useEasing: !this.uneasing,
                useGrouping: this.useGroup,
                separator: this.separator,
                decimal: this.decimal
            })
            setTimeout(() => {
                if (!this.counter.error) this.counter.start()
            }, this.delay)
        })
    },
    watch: {
        end (newVal) {
            let endVal = this.getValue(newVal)
            this.counter.update(endVal)
        }
    }
});
/*********CountTo组件 end*********/

/*********图片预览组件 begin*********/
App.register({
    template: "#img-preview",
    props:{
        url:{
            type:String,
            default:""
        },
        placement:{
            type:String,default:"right"
        }
    }
});
/*********店铺组件 end*********/

App.setMainApp(new Vue({
        el: '#app',
        data:{
            cacheList:[],
            zoom:1,
            isCollapsed: false,//右侧菜单关闭属性，双向绑定
            activeName: '',//当前激活的menuId
            openedNames:Array,//当前菜单打开的列表
            mappingObject:Object,//菜单id与url的映射关系
            currentMenus:[],//当前菜单的从父到子的集合
            homeData:{},
            tagNavDataList:[],//导航列表，默认为首页
            menuList:[],//菜单列表
            systemList:MenuDevData,//系统列表
            systemActiveId:'',//当前激活的系统下标
            showBackTop:false,//是否显示backtop
            componentId:'',//当前模块区域展示的组件id
            showLoginModel:false,//显示登录modal
            loginFormData:{},//TODO 登录数据
            loginFormRule:{//TODO 登录规则
                loginName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码至少6位', trigger: 'blur' }
                ]
            },
            currentRequestObj:undefined //当前请求对象
        },
        //应用创建
        created:function(){
            this.loadTagNavCache();
            var collspsed = window.localStorage.getItem("MenuIsCollspsed") || false;
            this.isCollapsed = collspsed === 'false' ?  false : collspsed;
            var currentSystemIdex = window.localStorage.getItem("currentSystemIdex")||0;
            this.systemActiveId = currentSystemIdex;
            this.cacheList = this.getComponentList();
            $('body').css('zoom',window.screen.width / 1600);
        },
        //应用挂载完成
        mounted:function(){
            var that = this;
            this.$nextTick(function(){
                var pos = $('#bodyWrapper').position();
                that.loaddingPos = {
                    left:(pos.left+2)+"px",
                    top:pos.top+"px",
                    width: ( $(document).width()-pos.left ) + "px",
                    height:( $(document).height()-pos.top ) + "px"
                };
                that.initData()
            })
            //请求菜单数据
            //App.request('/api/user/menutree?loginName=1810').callSuccess(this.initData);
        },
        //计算属性
        computed: {
            //导航列表
            tagNavList :function() {
                return this.tagNavDataList;
            }
        },
        //监听属性
        watch: {
            //监听关闭菜单
            isCollapsed:function(value){
                window.localStorage.setItem("MenuIsCollspsed",value);
            },
            //监听系统选择
            systemActiveId:function(idx){
                this.menuList = this.systemList[idx].children;
                if ( !("menuId" in this.findMenuByMenuId(this.activeName)) ){
                    this.activeName = '';
                }
                try {
                    window.localStorage.setItem("currentSystemIdex",idx)
                }catch (e) {
                    console.log("缓存currentSystemIdex错误:"+e)
                }

            },
            //监听菜单选择
            activeName :function(name) {
                //选中系统
                var that = this;
                var reallyActiveName = name.split('@')[0];
                var sysId = reallyActiveName.split('-')[0];
                $.each(this.systemList,function(idx){
                    if ( sysId == this.id){
                        that.menuList = that.systemList[idx].children;
                        that.systemActiveId = idx;
                        return false;
                    }
                });

                //设置面包屑
                this.currentMenus = this.findMenuOpenNames(reallyActiveName);
                var openNames = [];
                $.each(this.currentMenus,function(){
                    openNames.push(this.menuId)
                });
                this.openedNames = openNames;

                this.$nextTick(function(){
                    this.$refs.menu.updateActiveName();
                    this.$refs.menu.updateOpened();
                });

                var url_ = this.mappingObject[name];

                //菜单中没有就到导航里面找
                if ( !url_ ){
                    //从tabnav里面找
                    $.each(that.tagNavDataList,function(){
                        if (this.menuId == name ){
                            url_ = this.url;
                        }
                    });
                }

                if ( url_ ){
                    if ( url_.indexOf('#') == 0 ){
                        window.location.hash=url_;
                        this.addPageTag(name,url_.substring(1));
                    }
                    else{
                        window.open(url_);
                    }
                }

            }
        },
        //方法
        methods: {
            //初始化
            initData:function(res){
                var that = this;
                var mpObj = {};
                //建立menuId与url的映射
                that.idUrlMapping(that.systemList,mpObj);
                //添加首页映射
                mpObj[that.homeData.menuId] = that.homeData.url;
                that.mappingObject = mpObj;
                $.history.init(function(hash) {
                    var currentHash = that.mappingObject[that.activeName];
                    currentHash = currentHash ? currentHash.substring(1) : "";
                    if (hash != "" && hash == currentHash){
                        return;
                    }

                    var findMenuId;
                    //默认首页
                    if ( !hash || "#"+hash == that.homeData.url ){
                        hash = that.homeData.url.substring(1);
                        findMenuId = that.homeData.menuId;
                    }else{
                        $.each(that.mappingObject,function(r){
                            if (this == "#"+hash ){
                                findMenuId = r;
                            }
                        });
                        //从tabnav里面找
                        $.each(that.tagNavDataList,function(){
                            if (this.url == "#"+hash ){
                                findMenuId = this.menuId;
                            }
                        });
                    }
                    //如果找到菜单，没找到菜单打开临时页面
                    if ( findMenuId ){
                        that.activeName = findMenuId;
                    }else{
                        that.loadModule(hash,new NavObject("#2",hash));
                    }
                });
            },
            getComponentList:function(){
                var array = [];
                $.each(this.tagNavDataList,function(){
                    if ( this.componentId ){
                        array.push(this.componentId);
                    }
                });
                return array;
            },
            //打开系统
            openSystem:function(name){
                this.systemActiveId = name;
            },
            //打开菜单页面
            openPage :function(name){
                this.activeName = name;
            },
            //添加页面导航
            addPageTag :function(menuId,url_){
                if ( typeof menuId !== 'string' ){
                    return [];
                }

                var exists = false;
                var menuObj;
                $.each(this.tagNavDataList,function(){
                    if ( this.menuId == menuId ){
                        exists = true;
                        menuObj = this;
                        //跳出循环
                        return false;
                    }
                });
                menuObj = menuObj || this.findMenuByMenuId(menuId);

                //设置标题
                document.title = menuObj.name;
                if ( !exists ){
                    this.tagNavDataList.push(menuObj);
                    this.putTagNavCache();
                }
                //自动移动到可视范围
                this.$nextTick(() => {
                    var tagsNav = this.$refs.tagsNav;
                    var refsTag = tagsNav.$refs.tagsPageOpened;
                    refsTag.forEach((item, index) => {
                        if (menuId === item.name) {
                            let tag = refsTag[index].$el;
                            tagsNav.moveToView(tag);
                            setTimeout(function(){
                                tagsNav.moveToView(tag)
                            },100)
                        }
                    })
                });
                this.loadModule(url_,menuObj);
            },
            //添加页面子模块导航
            addSubPageTag :function(tabNav){
                var exists = false;
                var that = this;
                var menuIndex = -1;
                $.each(this.tagNavDataList,function(idx){
                    if ( this.menuId == tabNav.menuId ){
                        exists = true;
                        //跳出循环
                        return false;
                    }
                    //当前菜单的下标
                    if ( that.activeName == this.menuId){
                        menuIndex = idx;
                    }
                });
                //插入到当前菜单tag的后面
                if ( !exists ){
                    var arySize = this.tagNavDataList.length;
                    //如果是最后的位置，直接追加
                    if ( menuIndex == arySize - 1){
                        this.tagNavDataList.push(tabNav);
                    }else{
                        let startList = this.tagNavDataList.slice(0,menuIndex+1);
                        let endList = this.tagNavDataList.slice(menuIndex+1,arySize);
                        let newAry = startList.concat(tabNav).concat(endList);
                        this.tagNavDataList = newAry;
                    }
                    this.putTagNavCache();
                }
                this.activeName = tabNav.menuId;
            },
            //设置本地导航缓存
            putTagNavCache :function(){
                try{
                    //设置页面按钮缓存
                    if ( JSON && JSON.stringify ){
                        window.localStorage.setItem(LOCAL_STORAGE_TABBTN_KEY,JSON.stringify(this.tagNavDataList));
                    }
                }catch (e) {
                    console.log('设置页面按钮缓存异常',e);
                }
            },
            //加载本地导航缓存
            loadTagNavCache :function(){
                try{
                    //加载页面按钮缓存
                    var tabBtnStorage = window.localStorage.getItem(LOCAL_STORAGE_TABBTN_KEY);
                    var tabBtnData = JSON.parse(tabBtnStorage);
                    if ( $.isArray(tabBtnData) && tabBtnData.length > 0){
                        this.homeData = tabBtnData[0];
                        if ( this.homeData.menuId == '#1'){
                            this.tagNavDataList = tabBtnData;
                        }else{
                            this.homeData = $.extend({},HOME_NAV_TAB_OBJ);
                            this.tagNavDataList = [this.homeData];
                        }
                    }else{
                        this.homeData = $.extend({},HOME_NAV_TAB_OBJ);
                        this.tagNavDataList = [this.homeData];
                    }
                }catch (e) {
                    this.homeData = $.extend({},HOME_NAV_TAB_OBJ);
                    this.tagNavDataList = [this.homeData];
                    console.log('加载页面按钮缓存异常',e);
                }
            },
            //加载模块页面
            loadModule :function(url_,menuObj){
                var that = this;
                //判断组件是否存在
                if ( menuObj && menuObj.componentId ){
                    var comp = Vue.component(menuObj.componentId);
                    if ( comp ){
                        that.cacheList.push(menuObj.componentId);//组件缓存
                        that.$nextTick(function(){
                            that.componentId = comp;
                        });
                        return;
                    }
                }

                App.request({
                    dataType:"text",
                    url:pageContextPath + url_,
                    success:function(res){
                        var tmp = $('<div></div>').html(res).appendTo('#componentRegister');
                        var template = $('script[type="text/x-template"]:eq(0)', tmp);
                        if (template[0]) {
                            var compId = template.attr('id');
                            that.cacheList.push(compId);//组件缓存
                            menuObj.componentId = compId;
                            that.$nextTick(function(){
                                that.componentId = compId;
                                //5s后移除减轻页面压力
                                setTimeout(function(){
                                    template.remove();
                                },5000)
                            })
                        }
                    }
                });
            },
            //建立菜单与url的映射
            idUrlMapping:function(children,obj){
                if ( children && children.length > 0 ){
                    for (var i_ =0,d_;d_= children[i_++];){
                        obj[d_.menuId]=d_.url;
                        this.idUrlMapping(d_.children,obj)
                    }
                }
            },
            //根据菜单id找到菜单
            findMenuByMenuId :function(menuId){
                if ( typeof menuId !== 'string' ){
                    return [];
                }
                if ( menuId == this.homeData.menuId){
                    return this.homeData;
                }

                var that = this;
                var ids = menuId.split('-');
                var findData = { children:that.menuList };
                //递归查找menu
                $.each(ids,function(){
                    var val = this;
                    $.each(findData.children,function(){
                        if ( this.id == val){
                            findData = this;
                        }
                    });
                });

                return findData;
            },
            //根据菜单id找到父子菜单集合
            findMenuOpenNames:function(menuId){
                if ( typeof menuId !== 'string' ){
                    return [];
                }
                var that = this;
                var ids = menuId != "-1" ? menuId.split('-') : ["-1"];
                var findData = { children:that.menuList };
                var buff = [];
                //递归查找menu
                $.each(ids,function(){
                    var val = this;
                    $.each(findData.children,function(){
                        if ( this.id == val){
                            buff.push(this);
                            findData = this;
                        }
                    });
                });
                return buff;
            },
            //针对菜单列表，获取激活菜单
            getActiveName:function(name){
                return name.split('@')[0];
            },
            //点击导航激活菜单
            handleClick :function(item){
                this.activeName = item.menuId;
            },
            closeCurrentTagNav :function(item){
                if ( !this.activeName || this.activeName == this.homeData.menuId ){
                    return;
                }

                this.handleTagsOption('single',this.activeName);
            },
            //关闭导航操作
            handleTagsOption:function(type,menuId){
                var that = this;
                if ( type == 'single' ){
                    var find = -1;
                    $.each(this.tagNavDataList,function(i_){
                        if ( this.menuId == menuId){
                            //that.tagNavDataList.remove(i_);
                            find = i_;
                            return false;
                        }
                    });
                    if ( find != -1){
                        Vue.delete(that.tagNavDataList,find);
                        //激活下一个页签，前一个找不到，就找下一个，再找不到就找首页
                        var menu = that.tagNavDataList[find-1] || that.tagNavDataList[find] || that.tagNavDataList[0];
                        if ( menu ){
                            that.activeName = menu.menuId;
                        }
                    }
                }else if ( type == 'close-all' ){
                    for(var ii=this.tagNavDataList.length-1,menu;menu=this.tagNavDataList[ii];ii--){
                        if ( menu.menuId != '#1' ){
                            Vue.delete(that.tagNavDataList,ii);
                        }
                    }
                    this.activeName = "#1";
                    var tagsNav = this.$refs.tagsNav;
                    var refsTag = tagsNav.$refs.tagsPageOpened;
                    let tag = refsTag[0].$el;
                    tagsNav.moveToHome()
                }else if ( type == 'close-others' ) {
                    //关闭其他
                    var tmpNav;
                    var newAry = [];
                    for(var ii=0,menu;menu=this.tagNavDataList[ii++];){
                        if ( menu.menuId == that.activeName || menu.menuId == '#1' ){
                            //Vue.delete(that.tagNavDataList,ii);
                            newAry.push(menu);
                        }
                    }
                    this.tagNavDataList = newAry;
                    var tagsNav = this.$refs.tagsNav;
                    var refsTag = tagsNav.$refs.tagsPageOpened;
                    let tag = refsTag[0].$el;
                    tagsNav.moveToHome()
                }
                this.cacheList = this.getComponentList();
                this.putTagNavCache();
            },
            //滚动显示backTop
            handScroll(e){
                var scTop = e.srcElement.scrollTop;
                if ( scTop > 0 ){
                    this.showBackTop = true;
                }else{
                    this.showBackTop = false;
                }
            },
            //点击backTop滚动条返回顶端
            scrollTop(){
                this.$refs.moduleWrapper.$el.scrollTop = 0;
            },
            //唤起登录窗口
            callLogin(ajaxObj){
                this.currentRequestObj = ajaxObj;
                this.showLoginModel=true;
            },
            //提交登录
            login(){
                var that = this;
                App.request('/api/user/login',this.loginFormData).post().callSuccess(function(){
                    App.request(that.currentRequestObj);
                    that.showLoginModel = false;
                });
            },
            logout(){
                App.request('/api/user/logout').callSuccess(function(){
                    window.location.href = contextPath
                });
            }
        }

    })
);