package org.alittlebitch.fitness.tcm.controller;

import org.alittlebitch.fitness.tcm.service.UserService;
import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServerRequest;

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

    @PostMapping("/login")
    private Mono<BaseResponse> login(String username, String password, HttpServerRequest req) throws IllegalAccessException {
        check(req);
        return Mono.justOrEmpty(ResponseBuilder.custom().data(userService.login(username, password)).build());
    }

    @PostMapping("/changePwd")
    private Mono<BaseResponse> changePwd(String username, String password, HttpServerRequest req) throws IllegalAccessException {
        check(req);
        userService.changePwd(username, password);
        return Mono.justOrEmpty(ResponseBuilder.custom().build());
    }

    @PostMapping("/add")
    private Mono<BaseResponse> addUser(String username, String password, String name, HttpServerRequest req) throws IllegalAccessException {
        check(req);
        userService.addUser(username, password, name);
        return Mono.justOrEmpty(ResponseBuilder.custom().build());
    }

    @GetMapping("/users")
    private Mono<BaseResponse> users(String userName, int page, int pageSize, HttpServerRequest req) throws IllegalAccessException {
        check(req);
        return Mono.justOrEmpty(ResponseBuilder.custom().data(userService.users(userName, page, pageSize)).build());
    }
}
