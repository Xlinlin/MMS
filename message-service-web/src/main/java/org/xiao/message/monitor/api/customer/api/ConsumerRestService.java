package org.xiao.message.monitor.api.customer.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.customer.dto.ConsumerDto;
import org.xiao.message.monitor.api.customer.query.ConsumerQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/consumer")
public interface ConsumerRestService
{
    @RequestMapping(value = "/save")
    Boolean saveConsumer(@RequestBody ConsumerDto consumerDto);

    @RequestMapping(value = "/pageConsumer")
    MongoPageImp<ConsumerDto> pageConsumer(@RequestBody ConsumerQuery consumerQuery);

    @RequestMapping(value = "/delete/{id}")
    Boolean delete(@PathVariable("id") String id);
}
