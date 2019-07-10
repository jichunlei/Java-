package com.mall.Concurrency.threadlocal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/10 08:32
 * @Description:
 */
@Controller
@RequestMapping("/threadlocal")
public class ThreadLocalController {

    @RequestMapping("/test")
    @ResponseBody
    public Long test() {
        return RequestHolder.getId();
    }

}
