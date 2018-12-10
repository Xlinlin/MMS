/* 注册全局属性 */
Vue.component('admin-home',{
    template: '#admin-home',
    data :function() {
        return {
            time:new Date(),
            options4: {
                disabledDate (date) {
                    return date && date.valueOf() > Date.now();
                }
            },
            topicTime:'',
            statisticsDate:'',
            optionsTime: {
                disabledDate(date) {
                    return date && date.valueOf() > Date.now();
                }
            },
            week:[],
            inforCardData: [
                { title: '当天总数', icon: 'md-text', color: '#19be6b' },
                { title: '未消费总数', icon: 'md-text', color: '#ff9900' },
                { title: '异常总数', icon: 'md-text', color: '#ed3f14' },
                { title: '死信总数', icon: 'md-text', color: '#ED0312' },
                { title: '当天总数', color: '#19be6b' },
                { title: '待回执总数', color: '#ed3f14' },
                { title: '失败总数', color: '#ED0312' }
            ],
            inforData:undefined,
            barData: {},
            topicData:[
            ],
            topicNum:undefined,
        }
    },
    methods: {
        setChart(){
            var that =this;
            App.request('/home/home/realTimeMonitor').hideLoad().handError(function () {}).post().callSuccess(function(res){
                that.inforCardData[0].count=res.data.currentCount;
                that.inforCardData[1].count=res.data.unConsumerCount;
                that.inforCardData[2].count=res.data.abnormalCount;
                that.inforCardData[3].count=res.data.deadCount;
                that.inforCardData[4].count=res.data.currentSmsCount;
                that.inforCardData[5].count=res.data.unReceiptSmsCount;
                that.inforCardData[6].count=res.data.failSmsCount;
                that.inforData=that.inforCardData;
           })
        },
        changeTime(){
            var that =this;
            that.week=[];
            App.request('/home/home/messageWeekSend',{
                date:this.time}).post().callSuccess(function(res){
                for(var i=0;i<res.data.length;i++){
                    that.week.push(res.data[i].sendSum)
                }
                that.barData.Sun=that.week[0];
                that.barData.Mon=that.week[1];
                that.barData.Tue=that.week[2];
                that.barData.Wed=that.week[3];
                that.barData.Thu=that.week[4];
                that.barData.Fri=that.week[5];
                that.barData.Sat=that.week[6];
                that.$refs.bar.loadData();
            })
        },
        changeTime1(){
            var that =this;
            if(that.topicTime==''){
                var date = new Date();
                date.setTime(date.getTime() - 24*60*60*1000);
                that.topicTime=this.format(date)
            }else{
                that.topicTime=this.format(that.topicTime);
            }
            that.topicData=[];
            App.request('/home/home/listTopicSendStatistics',{
                date:this.topicTime}).post().callSuccess(function(res){
                    if(res.data.length>0){
                        for(var i=0;i<res.data.length;i++){
                            that.topicData.push({name:res.data[i].topic,value:res.data[i].sendSum,statisticsDate:res.data[0].statisticsDate+"topic发送统计"});
                        }
                    }else{
                        that.statisticsDate=that.format(that.topicTime)+"topic发送统计";
                    }

            })
        },
        format(time){
            var date = new Date(time);
            let dateMonth=(date.getMonth()+1).toString().length==1?'0'+(date.getMonth()+1):(date.getMonth()+1)
            let dateDate =date.getDate().toString().length==1?'0'+date.getDate():date.getDate()
            return date.getFullYear() + '-' + dateMonth + '-' + dateDate;
        },
        clickJump(info){
        console.log(info)
            if(info.title=="异常总数"){
                // App.openModule("query_message", '消息查询', 'monitor/message/query_message.html');
                window.location.hash='monitor/message/query_message.html'
            }else if(info.title=="死信总数"){
                // App.openModule("dead_letter_queue", '消息查询', 'monitor/queue/dead_letter_queue.html');
                window.location.hash='monitor/queue/dead_letter_queue.html'
            }else if(info.title=="失败总数"){
                // App.openModule("sms_mgt", '消息查询', 'monitor/sms/sms_mgt.html');
                window.location.hash='monitor/sms/sms_mgt.html'
            }
        }
    },
    /* 组件创建完成事件  */
    created :function(){
        console.log('组件创建完成事件');

    },
    /* 模板编译挂载完成事件 类似小程序onload */
    mounted :function(){
        console.log('模板编译挂载完成事件');
        this. setChart()
        setInterval( this.setChart,5000)
        this.changeTime()
        this.changeTime1();
    },
    /* 组件更新完成事件 */
    updated:function(){
        console.log('组件更新完成事件');
    },
    /*  组件被激活 类似小程序onshow */
    activated :function(){
        console.log('组件被激活');
    },
    /*  组件未被激活 类似小程序ondestroy */
    deactivated :function() {
        console.log('组件未激活');
    }
});