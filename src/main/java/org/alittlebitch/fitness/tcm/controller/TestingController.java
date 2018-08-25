package org.alittlebitch.fitness.tcm.controller;

import org.alittlebitch.fitness.dto.TcmQuestionResp;
import org.alittlebitch.fitness.dto.TcmRequest;
import org.alittlebitch.fitness.dto.UnscrambleRequest;
import org.alittlebitch.fitness.tcm.service.TestingService;
import org.shoper.commons.core.SystemException;
import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.alittlebitch.fitness.tcm.service.UserToken.check;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:18
 */
@RestController
@RequestMapping(value = "/tcm")
public class TestingController {
    @Autowired
    TestingService testingService;

    @PostMapping(value = "/testCount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse testCount() {
        return ResponseBuilder.custom().data(testingService.testCount()).build();
    }

    @GetMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse question() {
        TcmQuestionResp question = testingService.question();
        return ResponseBuilder.custom().data(question).build();
    }

    @PostMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse question(@RequestBody TcmRequest tcmRequest) {
        testingService.saveQuestion(tcmRequest);
        return ResponseBuilder.custom().data(tcmRequest).build();
    }

    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse submit(@RequestBody TcmRequest tcmRequest) {
        return ResponseBuilder.custom().data(testingService.submit(tcmRequest)).build();
    }

    @PostMapping(value = "/result/{id}")
    public BaseResponse result(@PathVariable("id") String id) throws SystemException {
        return ResponseBuilder.custom().data(testingService.result(id)).build();
    }
    @GetMapping("/unscramble")
    public BaseResponse getUnscramble(String name, int page, int pageSize, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        return ResponseBuilder.custom().data(testingService.getUnscramble(name, page, pageSize)).totalCount(testingService.countUnscramble(name)).currPage(page).pageSize(pageSize).build();
    }

    @PostMapping("/unscramble")
    public BaseResponse addUnscramble(@RequestBody UnscrambleRequest unscrambleRequest, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        testingService.addUnscramble(unscrambleRequest);
        return ResponseBuilder.custom().build();
    }

    @PutMapping("/unscramble/{id}")
    public BaseResponse updateUnscramble(@PathVariable("id") int id, @RequestBody UnscrambleRequest unscrambleRequest, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        testingService.upadteUnscramble(id, unscrambleRequest);
        return ResponseBuilder.custom().build();
    }
    @DeleteMapping("/unscramble/{id}")
    public BaseResponse deleteUnscramble(@PathVariable("id") int id, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        testingService.deleteUnscramble(id);
        return ResponseBuilder.custom().build();
    }

    @GetMapping("/unanalysis")
    public BaseResponse getUnanalysis(int page, int pageSize, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        return ResponseBuilder.custom().data(testingService.getUnanalysis(page, pageSize)).build();
    }

    @GetMapping("/users")
    public BaseResponse getTcmUser(String name, Boolean analyze, int page, int pageSize, HttpServletRequest req) throws IllegalAccessException {
        check(req);
        int count = testingService.countTcmUser(name, analyze);
        return ResponseBuilder.custom().data(testingService.getTcmUser(name, analyze, page, pageSize)).totalCount(count).pageSize(pageSize).currPage(page).build();
    }


}
