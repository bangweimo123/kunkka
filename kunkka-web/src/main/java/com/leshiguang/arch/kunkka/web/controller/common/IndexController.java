package com.leshiguang.arch.kunkka.web.controller.common;

import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author bangwei.mo
 * @Date 2020-03-20 11:43
 * @Modify
 */
@RestController
public class IndexController {

    @GetMapping(value = "/")
    public void index(HttpServletResponse res) {
        try {
            res.sendRedirect("/kunkka");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/echo")
    @ResponseBody
    public ResultData echo(@RequestParam(required = false) String requestId) {
        return ResultDataBuilder.success(requestId);
    }
}
