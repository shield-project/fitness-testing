package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.dto.TcmQuestionBuilder;
import org.alittlebitch.fitness.dto.TcmQuestionResp;
import org.alittlebitch.fitness.dto.TcmRequest;
import org.alittlebitch.fitness.tcm.dao.TestingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:32
 */
@Service
public class TestingService {
    @Autowired
    TestingDao testingDao;

    public long testCount() {
        return testingDao.testCount();
    }

    public List<TcmQuestionResp> question() {
        return TcmQuestionBuilder.create(testingDao.findQuestion());
    }

    public String submit(TcmRequest tcmRequest) {
        return null;
    }

    public void saveQuestion(TcmRequest tcmRequest) {
        if (Objects.isNull(tcmRequest))
            throw new IllegalArgumentException("参数不能为空");
        testingDao.saveQuestion(tcmRequest.getQuestion(), tcmRequest.getSomatoType());
    }
}
