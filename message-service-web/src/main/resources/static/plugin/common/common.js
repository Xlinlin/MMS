/**
 * App应用对象
 * @author zhdong
 * @date 2018/8/26
 */
var App = (function ($) {

    const localPrefix = "LOCAL_DATA_";
    //声明app
    var app = {
        MainVueApp: null,
        currentLoading: null
    };
    var localData = {};

    /*************** 设置本地缓存 ****************/
    app.putData = function(key,data){
        localData[key] = data;
        try{
            window.localStorage.setItem(localPrefix+key,JSON.stringify(data));
        }catch (e) { console.log("存储异常："+e) }
    };
    app.getData = function(key){
        var data = localData[key];
        if ( !data ){
            try {
                data = window.localStorage.getItem(localPrefix + key);
                if (data != null && data != undefined) {
                    return JSON.parse(data);
                }
            }catch (e) { console.log("存储异常："+e) }
        }
        return data;
    };
    /*************** 设置本地缓存 ****************/

    app.removeData = function(key){
        delete localData[key];
        window.localStorage.removeItem(localPrefix + key);
    };

    /* 关闭当前页面 */
    app.closeCurrentTagNav = function(){
       this.MainVueApp.closeCurrentTagNav();
    };

    app.isEmpty = function(obj){
        return obj == null || obj == undefined || obj == "";
    };

    /* 分页对象 PageInfo返回封装对象*/
    app.Pager = function (p) {
        p = p || {};
        this.content = p.content || [];
        this.totalPages = p.totalPages || 1;
        this.size = p.size || 10;
        this.prePage = p.prePage || 0;
        this.totalElements = p.totalElements || 0;
    };

    /* 分页对象 PageImpl返回封装对象*/
    app.Page = function (p) {
        p = p || {};
        this.list = p.content || [];
        this.pageNum = p.pageNum || 1;
        this.pageSize = p.pageSize || 10;
        this.pages = p.totalPages || 1;
        this.total = p.totalElements || 0;
    };
    

    /* 获取oss访问地址 */
    app.getOssAccessUrl = function (buck, key) {
        return "http://" + buck + "." + OSSAccessDomain + "/" + key;
    };

    app.getPath = function(url){
        url = contextPath + url;
        if (url.substring(0, 2) == "//") {
            url = url.substring(1);
        }
        return url;
    };

    app.requestCache = new Map();

    //获取app高度
    app.height = function () {
        return $(App.MainVueApp.$el).height();
    };

    //获取模块高度
    app.moduleHeight = function () {
        var layoutH = $(App.MainVueApp.$refs.moduleWrapper.$el).height();
        var tagH = $(App.MainVueApp.$refs.tagsNav.$el).height();
        return layoutH - tagH;
    };

    //计算高度属性，需要传递hh头高度才能计算得出，默认为0
    var computedHeight = function (hh) {
        var hh = hh || 0;
        let modulePadding = 18 * 2;//模块内边距
        let rang = 5;//误差
        let pgH = 32 + modulePadding + rang;
        var minH = 100;
        var contentH = app.moduleHeight() - hh - pgH;
        return contentH < minH ? minH : contentH;
    };

    /*
    * moduleComp，必须组件中直接传递this
    * headH  头高度默认0，如果为数组则自动填充多个属性的高度格式
    * [
    *   {prop:'tableAptHeight',hh:'100'}
    * ]
    * */
    app.acptTableHeight = function (moduleComp, headH) {
        var work_ = function () {
            if ($.isArray(headH)) {
                $.each(headH, function () {
                    if (!(this.prop in moduleComp)) {
                        console.error('你的组建要定义' + this.prop + '属性');
                        //跳出循环
                        return false
                    }
                    moduleComp[this.prop] = computedHeight(this.hh || 0);
                })
            } else {
                moduleComp.tableAptHeight = computedHeight(headH || 0);
            }
        };

        work_();

        $(window).off('resize').on('resize', function () {
            work_()
        });
    };

    //注册模块
    app.register = function (compent) {
        if (!compent.template) {
            app.error('组件template为空')
        } else {
            return Vue.component(compent.template.substring(1), compent);
        }
    };

    //设置主App应用
    app.setMainApp = function (vue) {
        this.MainVueApp = vue;
    };

    //显示loadding蒙层
    app.showLoadding = function (message, timeout, ajax) {
        try {
            this.currentLoading && this.currentLoading();
            this.currentLoading = this.MainVueApp.$Message.loading({
                content: message || '加载中...',
                duration: timeout || 60,
                closable: ajax ? true : false,
                onClose: function () {
                    //结束请求
                    ajax && ajax.abort && ajax.abort();
                }
            })
        } catch (e) {
            console.log('显示loading错误' + e)
        }
    };
    //隐藏蒙层
    app.hideLoading = function () {
        try {
            this.currentLoading && this.currentLoading();
        } catch (e) {
            console.log('隐藏loading错误' + e)
        }
    };

    //弹出提示toast
    app.info = function (msg, timeout, closable) {
        this.MainVueApp.$Message.info({
            content: msg,
            duration: timeout || 3,
            closable: closable || true
        });
    };

    //弹出成功toast
    app.success = function (msg, timeout, closable) {
        this.MainVueApp.$Message.success({
            content: msg,
            duration: timeout || 3,
            closable: closable || true
        });
    };

    //弹出警告toast
    app.warning = function (msg, timeout, closable) {
        this.MainVueApp.$Message.warning({
            content: msg,
            duration: timeout || 3,
            closable: closable || true
        });
    };

    //弹出错误toast
    app.error = function (msg, timeout, closable) {
        this.MainVueApp.$Message.error({
            content: msg,
            duration: timeout || 3,
            closable: closable || true
        });
    };

    //debug
    app.debug = function (e) {
        if (DEBUGGER) {
            console.log(e);
        }
    };


    app.request = function (options, data) {

        //默认ajax请求
        var defOpt = {
            type: "get",
            dataType: "json",
            showLoad: true,
            cached: false,
            modal: null,
            button: null,
            setUrl: function (url) {
                this.url = contextPath + url;
                if (this.url.substring(0, 2) == "//") {
                    this.url = this.url.substring(1);
                }
                return this;
            },
            setModal: function (modal) {
                this.modal = modal;
                return this;
            },
            setType: function (type) {
                this.type = type;
                return this;
            },
            setButton(btn) {
                this.button = btn;
                return this;
            },
            post: function () {
                this.type = "post";
                return this;
            },
            useCache: function () {
                this.cached = true;
                return this;
            },
            setData: function (d) {
                this.contentType = 'application/json;charset=utf-8';
                this.data = JSON.stringify(d);
                return this;
            },
            hideLoad: function () {
                this.showLoad = false;
                return this;
            },
            beforeSend: function (xhr) {
                if (this.button && typeof this.button === 'object') {
                    this.button.loading = true;
                } else {
                    var app_ = this.modal || app;
                    this.showLoad && app_.showLoadding(null, null, xhr);
                }
                this.beforeCallback && this.beforeCallback();
            },
            complete: function () {
                if (this.button && typeof this.button === 'object') {
                    this.button.loading = false;
                } else {
                    var app_ = this.modal || app;
                    this.showLoad && app_.hideLoading();
                }
                this.completeCallback && this.completeCallback();
            },
            success: function (res) {
                if (this.successCallback2) {
                    return this.successCallback2(res);
                }
                if (res.success) {
                    //如果激活了缓存，则保存缓存
                    if ((!this.type || this.type.toLowerCase() == 'get') && this.cached) {
                        app.requestCache.set(this.url,$.extend(true,{},res))
                    }
                    this.successCallback && this.successCallback(res);
                } else {
                    if (this.errorCallback2) {
                        return this.errorCallback2(res);
                    }
                    res.message && app.error(res.message);
                    this.errorCallback && this.errorCallback(res);
                }
            },
            error: function (xhr) {

                if (xhr.status == 401) {
                    var dataJson = xhr.responseJSON || JSON.parse(xhr.responseText);
                    if (dataJson && dataJson.code == 10060) {
                        app.error('登录超时，请重新登录!');
                        app.MainVueApp.callLogin(this);
                        return;
                    }
                }

                if (this.errorCallback2) {
                    return this.errorCallback2(xhr.responseJSON, xhr);
                }
                if (xhr.status == 401) {
                    app.error('您没有浏览这个页面的权限');
                }
                if (xhr.status == 404) {
                    app.error('您访问的页面不存在');
                }
                else if (xhr.status == 500) {
                    var dataJson = xhr.responseJSON || JSON.parse(xhr.responseText);
                    if (dataJson.message) {
                        app.error(dataJson.message);
                    } else {
                        app.error('页面内部错误，请联系管理员');
                    }
                }
                else if (xhr.status == 405) {
                    app.error('请求方法错误');
                }else{
                    app.error('无法访问服务器，请检查网络');
                }
                this.errorCallback && this.errorCallback(xhr.responseJSON, xhr);
            },
            callBefore: function (callback) {
                this.beforeCallback = callback;
                return this;
            },
            callComplete: function (callback) {
                this.completeCallback = callback;
                return this;
            },
            callError: function (callback) {
                this.errorCallback = callback;
                return this;
            },
            callSuccess: function (callback) {
                this.successCallback = callback;
                return this;
            },
            handError: function (callback) {
                this.errorCallback2 = callback;
                return this;
            },
            handSuccess: function (callback) {
                this.successCallback2 = callback;
                return this;
            }
        };
        var finalOpt;
        //第一个参数是string，直接引用默认属性，否则为对象类型，直接复制对象
        if (typeof options === 'string') {
            finalOpt = defOpt.setUrl(options);
            finalOpt.data = data;
        } else {
            finalOpt = $.extend(defOpt, options);
            finalOpt.url = contextPath + finalOpt.url;
            if (data) finalOpt.data = data;
            if (finalOpt.url.substring(0, 2) == "//") {
                finalOpt.url = finalOpt.url.substring(1);
            }
        }
        //延时调用，方便调用对象方法追加属性
        setTimeout(function () {
            //判断是否从缓存中获取
            if (finalOpt.cached) {
                var ps = finalOpt.data ? $.param(finalOpt.data) : "";
                var key = finalOpt.url.indexOf('?') == -1 && ps ? finalOpt.url + "?" + ps :finalOpt.url + ps;
                var exitsCache = app.requestCache.get(key);
                if (exitsCache) {
                    finalOpt.successCallback && finalOpt.successCallback($.extend(true,{},exitsCache));
                    return;
                }
            }
            $.ajax(finalOpt)
        }, 100);

        return finalOpt;
    };

    /**
     *
     * @param subMenuFlag 子模块标识 ,直接写html的名称吧
     * @param url 模块url
     * @param name 模块名称
     */
    app.openModule = function (moudelFlag, name, url) {
        var mainApp = this.MainVueApp;
        var tabNav = new NavObject(mainApp.activeName + "@" + moudelFlag, "#" + url, name);
        mainApp.addSubPageTag(tabNav);
    };

    return app;
})(jQuery);

//导航对象
function NavObject(menuId, url, name, iconCls) {
    this.menuId = menuId;
    this.name = name;
    this.url = url;
    this.iconCls = iconCls;
}

function JS_UUID() {
    var uuid = new Date().getTime();
    var rdn = Math.floor(Math.random() * 1000000);
    return uuid.toString(36) + "-" + rdn.toString(36);
}