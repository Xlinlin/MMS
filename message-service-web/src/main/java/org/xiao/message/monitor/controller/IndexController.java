package org.xiao.message.monitor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nietao
 * Date 2018/8/23
 */
@Controller
public class IndexController {

    @Value("${message.service.web.page.index}")
    private String indexPage;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest req, HttpServletResponse resp)
    {
        return indexPage;
    }


    @RequestMapping(value = "/index")
    public String index2(HttpServletRequest req, HttpServletResponse resp)
    {
        return indexPage;
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest req, HttpServletResponse resp)
    {
        return "login/login";
    }

}
