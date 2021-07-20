package com.hongkun.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/checkDatabase")
public class CheckDatabaseController {

    /**
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @GetMapping("/check.do")
    public void check(HttpServletRequest request, HttpServletResponse response) {

    }

}
