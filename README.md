# MMS
(消息中间件服务)Message Middleware Service

消息中间件平台
1 总体功能设计描述，参考doc/消息中间件平台.png文件
2 概述：

主要功能描述：
1.1) 服务内部消息消息通信，短信发送
1.2) 提供消息服务器broker、主题、消息以及短信的管理
设计实现：
2.1) 对外开发API接口，提供主题管理、消息发送、短信发送接口
2.2) 利用Redis的PUB/SUB功能解耦消息与消息中间件服务器broker之间关系，提高扩展性
2.3) 封装与消息服务器的连接，服务内部消息发送者，对外封装消息服务器的连接API
3 服务部署介绍
3.1 message-service-admin提供了消息服务管理api，可单独部署，也可以集成到message-service-web工程
3.2 message-service-server提供对外的api服务(核心服务)。消息发送、主题创建、短信发送等对外服务
3.3 message-service-producer内部消息转发服务(核心服务)。封装了与消息中间件服务器的连接，以及将消息发送的消息中间件中
3.4 message-service-web 提供了WEB管理平台以及监控平台
3.5 message-service-consumer 消息中间件客户端的封装，消费者直接引用该jar，集成BaseKafkaConsumerServer实现业务逻辑
3.6 message-service-demo 消费端demo参考工程

4 客户端连接使用说明
当前consumer的封装了通过topic到api服务自动获取服务器信息，使用的feign实现，需要保证两者服务在同一注册中心。 (后续扩展成HTTP方式，脱离注册中心的实现)
