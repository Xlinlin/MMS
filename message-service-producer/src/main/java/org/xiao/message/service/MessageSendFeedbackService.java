package org.xiao.message.service;

/**
 * [简要描述]: 消息发送反馈
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/8 09:33
 * @since JDK 1.8
 */
public interface MessageSendFeedbackService
{
    /**
     * [简要描述]:消息发送日志反馈<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题
     * @param messageId : 消息ID
     * @param errorMassage : 错误消息
     * llxiao  2018/11/8 - 9:34
     **/
    void feedbackSendTraceLog(String topic, String messageId, String errorMassage);

    /**
     * [简要描述]:反馈主题和服务器之间的异常关系<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题
     * @param errorMsg : 异常消息
     * llxiao  2018/11/5 - 10:28
     **/
    void feedbackTopicServer(String topic, String errorMsg);

    /**
     * [简要描述]:消息发送状态反馈<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @param isError : 是否错误
     * llxiao  2018/11/8 - 9:38
     **/
    void feedbackSendMessageStatus(String messageId, boolean isError);
}
