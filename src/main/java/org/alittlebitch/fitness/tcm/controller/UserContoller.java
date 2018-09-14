package org.alittlebitch.fitness.tcm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alittlebitch.fitness.tcm.service.UserService;
import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.alittlebitch.fitness.tcm.service.UserToken.check;

/**
 * @author ShawnShoper
 * @date 2018/8/23 10:40
 */
@RestController
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/login")
    private BaseResponse login(String username, String password) {
        return ResponseBuilder.custom().data(userService.login(username, password)).build();
    }

    @PostMapping("/changePwd")
    private BaseResponse changePwd(String username, String password, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        userService.changePwd(username, password);
        return ResponseBuilder.custom().build();
    }

    @PostMapping("/user")
    private BaseResponse addUser(String username, String password, String name, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        userService.addUser(username, password, name);
        return ResponseBuilder.custom().build();
    }

    @GetMapping("/users")
    private BaseResponse users(String name, int page, int pageSize, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        return ResponseBuilder.custom().data(userService.users(name, page, pageSize)).totalCount(userService.count(name)).pageSize(pageSize).currPage(page).build();
    }

    @GetMapping("/wx")
    private BaseResponse getWXUser(String resCode) throws IOException {
        if (StringUtils.isEmpty(resCode))
            throw new NullPointerException("resCode不能为空");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx80e4a55d9cbb6e95&secret=08c56f64ad5b27773ed326c178de168a&js_code=" + resCode + "&grant_type=authorization_code";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        if (!StringUtils.isEmpty(jsonNode.findValue("errcode")))
            throw new RuntimeException("获取数据失败");
        JsonNode openid = jsonNode.findValue("openid");
        return ResponseBuilder.custom().data(openid.asText()).build();
    }

}
