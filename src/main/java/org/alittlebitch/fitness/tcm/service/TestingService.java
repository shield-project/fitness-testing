package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.dto.TcmQuestionBuilder;
import org.alittlebitch.fitness.dto.TcmQuestionResp;
import org.alittlebitch.fitness.dto.TcmRequest;
import org.alittlebitch.fitness.dto.TcmResult;
import org.alittlebitch.fitness.tcm.dao.TestingDao;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    public Object submit(TcmRequest tcmRequest) {
        List<TcmResult> tcmResult = tcmRequest.getTcmResult();
        //solution A
//        Map<SomatoType, Integer> collect = tcmResult.stream()
//                .collect(groupingBy(TcmResult::getSomatoType,
//                        mapping(TcmResult::getScore,
//                                mapping(List::stream,
//                                        mapping(e -> e.mapToInt(Integer::intValue),
//                                                summingInt(IntStream::sum))))));

        //solution B
//        Map<SomatoType, Integer> group = new HashMap<>(tcmResult.size());
//        for (TcmResult result : tcmResult) {
//            Integer totalSocre = result.getScore().stream().collect(summingInt(Integer::intValue));
//            group.put(result.getSomatoType(), totalSocre);
//        }
        //solution now
        Map<SomatoType, Double> somatoGroupScore = tcmResult.stream().
                collect(groupingBy(TcmResult::getSomatoType,
                        collectingAndThen(
                                mapping(TcmResult::getScore, toList()),
                                list -> list
                                        .stream()
                                        .flatMap(Collection::stream)
                                        .mapToDouble(e -> e).sum())));
        Map<SomatoType, TcmResult> tcmResultMap = tcmResult.stream()
                .collect(Collectors.toMap(TcmResult::getSomatoType, tr -> tr));
        //转化分数=[（原始分-条目数）/（条目数×4）] ×100
        somatoGroupScore.keySet().stream().forEach(e -> {
            TcmResult tcmResult1 = tcmResultMap.get(e);
            double size = tcmResult1.getScore().size();
            Double totalScore = somatoGroupScore.get(e);
            double finalScore = 100 * ((totalScore - size) / (size * 4));
            somatoGroupScore.put(e, finalScore);
        });

        //判定平和质

        return somatoGroupScore;
    }

    public void saveQuestion(TcmRequest tcmRequest) {
        if (Objects.isNull(tcmRequest))
            throw new IllegalArgumentException("参数不能为空");
        if (testingDao.saveQuestion(tcmRequest.getQuestion(), tcmRequest.getSomatoType()) != 1) {
            throw new IllegalStateException("保存失败");
        }
    }
}
