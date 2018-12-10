package org.xiao.message.monitor.api.customer.dto;

import lombok.Data;

@Data
public class ConsumerDto {
    private String consumerId;
    private String consumerCode;
    private String topic;
    private String desc;
    private String createTime;//开始时间
    private Integer online = 0;
}
