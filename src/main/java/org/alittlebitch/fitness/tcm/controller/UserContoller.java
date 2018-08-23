package org.alittlebitch.fitness.tcm.controller;

import org.alittlebitch.fitness.tcm.service.UserService;
import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
