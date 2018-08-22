package org.alittlebitch.fitness.tcm.controller;

import org.alittlebitch.fitness.dto.TcmQuestionResp;
import org.alittlebitch.fitness.dto.TcmRequest;
import org.alittlebitch.fitness.tcm.service.TestingService;
import org.shoper.commons.core.SystemException;
import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<BaseResponse> testCount() {
        return Mono.justOrEmpty(ResponseBuilder.custom().data(testingService.testCount()).build());
    }

    @GetMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<BaseResponse> question() {
        TcmQuestionResp question = testingService.question();
        return Mono.justOrEmpty(ResponseBuilder.custom().data(question).build());
    }

    @PostMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<BaseResponse> question(@RequestBody TcmRequest tcmRequest) {
        testingService.saveQuestion(tcmRequest);
        return Mono.justOrEmpty(ResponseBuilder.custom().data(tcmRequest).build());
    }

    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<BaseResponse> submit(@RequestBody TcmRequest tcmRequest) {
        return Mono.justOrEmpty(ResponseBuilder.custom().data(testingService.submit(tcmRequest)).build());
    }

    @PostMapping(value = "/result/{id}")
    public Mono<BaseResponse> result(@PathVariable("id") String id) throws SystemException {
        return Mono.justOrEmpty(ResponseBuilder.custom().data(testingService.result(id)).build());
    }
}
