package org.xiao.message.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.xiao.message.constant.Constants;

@FeignClient(value = Constants.MESSAGE_SERVICE_NAME)
@RequestMapping("/api/message")
public interface MessageFeedbackFeign {
	@PostMapping(path="feedback")
	String feedbackMessage(@RequestBody FeedbackMessageDto messageDto);
}
