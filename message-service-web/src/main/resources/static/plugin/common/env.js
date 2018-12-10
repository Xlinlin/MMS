/**
 *  环境信息
 *  @author zhdong
 *  @date 2018/8/26
 */
const contextPath = "/";//上下文根
const LOCAL_STORAGE_TABBTN_KEY = "tabBtnStorage";//导航存储key
const pageContextPath = contextPath+"pages/";//页面路径
const OSSAccessDomain= "oss-cn-shenzhen.aliyuncs.com";

//浏览器检查，是否支持es6表达式
try {
    eval('var a1 = { func(){ }, func2: () => { } };');
    [].findIndex(function(){})
} catch (e) {
    window.location.href=contextPath+"pages/support/support.html";
}

//调试模式
const DEBUGGER = true;
//首页配置
var HOME_NAV_TAB_OBJ = {
    id: "#1",
    menuId: "#1",
    name: "首页",
    url: "#home/home.html",
    iconCls: "ios-home-outline",
    componentId:"admin-home"
};
var MenuDevData = [{
    "children": [{
        "iconCls": null,
        "id": 20001,
        "menuId": "20000-20001",
        "name": "Topic管理",
        "parentId": 20000,
        "url": "#monitor/topic/topic_mgt.html"
    },
    //     {
    //     "iconCls": null,
    //     "id": 20002,
    //     "menuId": "20000-20002",
    //     "name": "生产者管理",
    //     "parentId": 20000,
    //     "url": "#monitor/producer/producer_mgt.html"
    // },
        {
        "iconCls": null,
        "id": 20003,
        "menuId": "20000-20003",
        "name": "消费者管理",
        "parentId": 20000,
        "url": "#monitor/customer/customer_mgt.html"
    }, {
        "iconCls": null,
        "id": 20004,
        "menuId": "20000-20004",
        "name": "消息查询",
        "parentId": 20000,
        "url": "#monitor/message/query_message.html"
    }, {
        "iconCls": null,
        "id": 20005,
        "menuId": "20000-20005",
        "name": "死信队列",
        "parentId": 20000,
        "url": "#monitor/queue/dead_letter_queue.html"
    },
    //     {
    //     "iconCls": null,
    //     "id": 20006,
    //     "menuId": "20000-20006",
    //     "name": "资源报表",
    //     "parentId": 20000,
    //     "url": "#monitor/resource/resource_report.html"
    // },
    //     {
    //     "iconCls": null,
    //     "id": 20007,
    //     "menuId": "20000-20007",
    //     "name": "监控报警",
    //     "parentId": 20000,
    //     "url": "#monitor/monitoring/monitoring_alarm.html"
    // },
        {
        "iconCls": null,
        "id": 20008,
        "menuId": "20000-20008",
        "name": "服务器组管理",
        "parentId": 20000,
        "url": "",
        "children":[{
            "iconCls": null,
            "id": 20009,
            "menuId": "20000-20008-20009",
            "name": "服务组管理",
            "parentId": 20008,
            "url": "#monitor/groupId/groupId_mgt.html"},
            {
                "iconCls": null,
                "id":20010 ,
                "menuId": "20000-20008-20010",
                "name": "服务管理",
                "parentId": 20008,
                "url": "#monitor/broker/broker_mgt.html"
            },
            {
                "iconCls": null,
                "id":20011 ,
                "menuId": "20000-20008-20011",
                "name": "topic关联",
                "parentId": 20008,
                "url": "#monitor/topicRelevance/topic_rel.html"
            }
        ]},
        // {
        //     "iconCls": null,
        //     "id": 20012,
        //     "menuId": "20000-20012",
        //     "name": "收发统计",
        //     "parentId": 20000,
        //     "url": "#monitor/sendAndReceive/statistics.html"
        // },
        {
            "iconCls": null,
            "id": 20013,
            "menuId": "20000-20013",
            "name": "短信管理",
            "parentId": 20000,
            "url": "#monitor/sms/sms_mgt.html"
        },

        ],
    "iconCls": null,
    "id": 20000,
    "menuId": "20000",
    "name": "消息队列MQ",
    "parentId": 0,
    "url": ""
},
    {
        "children": [{
            "iconCls": null,
            "id": 30001,
            "menuId": "30000-30001",
            "name": "任务管理",
            "parentId": 30000,
            "url": "#schedule/taskmanager/task_manager.html"
        },
            {
                "iconCls": null,
                "id": 30002,
                "menuId": "30000-30002",
                "name": "调度日志",
                "parentId": 30000,
                "url": "#schedule/operationlog/operation_log.html"
            }],
        "iconCls": null,
        "id": 30000,
        "menuId": "30000",
        "name": "任务调度中心",
        "parentId": 0,
        "url": ""
    }
];
