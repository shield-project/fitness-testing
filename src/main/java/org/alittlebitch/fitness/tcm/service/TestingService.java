package org.alittlebitch.fitness.tcm.service;

import lombok.Data;
import org.alittlebitch.fitness.dto.*;
import org.alittlebitch.fitness.tcm.bean.ResultRecord;
import org.alittlebitch.fitness.tcm.bean.SomatoInfo;
import org.alittlebitch.fitness.tcm.dao.TestingDao;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    public TcmQuestionResp question() {
        List<TcmQuestion> tcmQuestions = TcmQuestionBuilder.create(testingDao.findQuestion());
        TcmQuestionResp tcmQuestionResp = new TcmQuestionResp();
        tcmQuestionResp.setTotal(tcmQuestions.size());
        tcmQuestionResp.setQuestions(tcmQuestions);
        return tcmQuestionResp;
    }

    public Object submit(TcmRequest tcmRequest) {
        List<TcmResult> tcmResults = tcmRequest.getTcmResult();

        UserInfo userInfo = tcmRequest.getUserInfo();

        Map<SomatoType, Double> somatoTypeScoreMap = tcmResults.stream().collect(Collectors.groupingBy(TcmResult::getSomatoType, Collectors.summingDouble(TcmResult::getScore)));

        Map<SomatoType, Long> somatoTypeCountMap = tcmResults.stream().collect(Collectors.groupingBy(TcmResult::getSomatoType, Collectors.counting()));
        ResultRecord resultRecord = new ResultRecord();
        List<SomatoInfo> somatoInfos = new ArrayList<>();
        somatoTypeScoreMap.entrySet().stream().forEach(e -> {
            SomatoType key = e.getKey();
            Double value = e.getValue();
            Long count = somatoTypeCountMap.get(key);
            float percent = (float) (100 * ((value - count) / (count * 4)));
            somatoInfos.add(new SomatoInfo(e.getKey().getTitle(), e.getKey().name(), percent));
        });
        Optional<SomatoInfo> first = somatoInfos.stream().filter(e -> e.getTypeValue().equals(SomatoType.MILDPHYSICAL.name())).findFirst();
        SomatoInfo mildPhysical = first.get();
        somatoInfos.remove(mildPhysical);
        Map<String, Float> biasedMap = new HashMap<>();
        somatoInfos.stream().forEach(e -> biasedMap.put(e.getTypeName(), e.getPercent()));
        List<String> gte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 40 && e.getValue() >= 30).map(Map.Entry::getKey).collect(Collectors.toList());
        List<String> lte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 30).map(Map.Entry::getKey).collect(Collectors.toList());
        List<String> gte40 = biasedMap.entrySet().stream().filter(e -> e.getValue() >= 40).map(Map.Entry::getKey).collect(Collectors.toList());
        if (!gte40.isEmpty()) {
            biasedMap.entrySet().stream().forEach(e -> {
                if (e.getValue() >= 40) {
                    String key = e.getKey();
                    somatoInfos.stream().filter(b -> b.getTypeName().equalsIgnoreCase(key)).forEach(c -> c.setActive(true));
                }
            });
        } else {
            if (!lte30.isEmpty() && mildPhysical.getPercent() >= 60)
                mildPhysical.setActive(true);
            else if (!gte30.isEmpty() && mildPhysical.getPercent() >= 60) {
                mildPhysical.setActive(true);
                gte30.stream().forEach(e -> somatoInfos.stream().filter(s -> s.getTypeName().equals(e)).forEach(b -> b.setActive(true)));
            } else
                mildPhysical.setActive(false);
            biasedMap.entrySet().stream().forEach(e -> {
                if (e.getValue() > 30) {
                    String key = e.getKey();
                    somatoInfos.stream().filter(b -> b.getTypeName().equals(key)).forEach(c -> c.setActive(true));
                }
            });
        }
        somatoInfos.add(mildPhysical);
//        resultRecord.setTestResult(somatoInfos);
//        resultRecord.setUserInfo(userInfo);
//        resultRecord.setId(UUID.randomUUID().toString());
        //先提取平和质的属性
//        Map<SomatoType, MildDetermination> mildMap = new HashMap<>();
//        mildMap.put(SomatoType.MILDPHYSICAL, null);
//        Double mildScore = somatoTypeScoreMap.get(SomatoType.MILDPHYSICAL);
//        //去除平和质属性
//        Map<SomatoType, Double> map = CloneUtil.depthClone(somatoTypeScoreMap, Map.class);
//        map.remove(SomatoType.MILDPHYSICAL);
//        //提取出分数30-39的偏颇
//        Map<SomatoType, BiasedDetermination> biasedMap = new HashMap<>();
//        map.entrySet().forEach((e) -> {
//            if (e.getValue() < 30)
//                biasedMap.put(e.getKey(), BiasedDetermination.NO);
//            else if (e.getValue() >= 30 && e.getValue() < 40)
//                biasedMap.put(e.getKey(), BiasedDetermination.MAYBE);
//            else
//                biasedMap.put(e.getKey(), BiasedDetermination.YES);
//        });
//        if (mildScore >= 60 && biasedMap.values().contains(BiasedDetermination.NO))
//            mildMap.put(SomatoType.MILDPHYSICAL, MildDetermination.YES);
//        else if (mildScore >= 60 && biasedMap.values().contains(BiasedDetermination.YES))
//            mildMap.put(SomatoType.MILDPHYSICAL, MildDetermination.MAYBE);
//        else
//            mildMap.put(SomatoType.MILDPHYSICAL, MildDetermination.NO);
//
//        //构建result record
//        ResultRecord resultRecord = new ResultRecord();
//        resultRecord.setScore(somatoTypeScoreMap);
//        if (mildMap.get(SomatoType.MILDPHYSICAL) != MildDetermination.NO) {
//            //主要体征为平和质
//            resultRecord.setMildDetermination(mildMap.get(SomatoType.MILDPHYSICAL));
//        }
        //提取偏颇体质为
//        List<SomatoType> biaseds = biasedMap.entrySet().stream().filter(e -> e.getValue() != BiasedDetermination.NO).map(e -> e.getKey()).collect(Collectors.toList());
//        if (!biaseds.isEmpty()) {
//            List<Map.Entry<SomatoType, BiasedDetermination>> anyYesBiased = biasedMap.entrySet().stream()
//                    .filter(e -> e.getValue().equals(BiasedDetermination.NO)
//                    )
//                    .collect(Collectors.toList());
//            if (!anyYesBiased.isEmpty()) {
//                resultRecord.setBiasedDetermination(BiasedDetermination.YES);
//                resultRecord.setBiaseds(anyYesBiased.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
//            } else {
//                List<Map.Entry<SomatoType, BiasedDetermination>> anyMaybeBiased = biasedMap.entrySet().stream()
//                        .filter(e -> e.getValue().equals(BiasedDetermination.MAYBE)
//                        )
//                        .collect(Collectors.toList());
//                if (!anyMaybeBiased.isEmpty()) {
//                    resultRecord.setBiasedDetermination(BiasedDetermination.MAYBE);
//                    resultRecord.setBiaseds(anyMaybeBiased.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
//                }
//            }
//        }


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
//        Map<SomatoType, Double> somatoGroupScore = tcmResult.stream().
//                collect(groupingBy(TcmResult::getSomatoType,
//                        collectingAndThen(
//                                mapping(TcmResult::getScore, toList()),
//                                list -> list
//                                        .stream()
//                                        .flatMap(Collection::stream)
//                                        .mapToDouble(e -> e).sum())));
//        Map<SomatoType, TcmResult> tcmResultMap = tcmResult.stream()
//                .collect(Collectors.toMap(TcmResult::getSomatoType, tr -> tr));
        //转化分数=[（原始分-条目数）/（条目数×4）] ×100
//        somatoGroupScore.keySet().stream().forEach(e -> {
//            TcmResult tcmResult1 = tcmResultMap.get(e);
//            double size = tcmResult1.getScore().size();
//            Double totalScore = somatoGroupScore.get(e);
//            double finalScore = 100 * ((totalScore - size) / (size * 4));
//            somatoGroupScore.put(e, finalScore);
//        });


//        resultRecord.setUserInfo(userInfo);
//        ResultRecordRespBuild.create(resultRecord);
        return resultRecord;
    }

    public void saveQuestion(TcmRequest tcmRequest) {
        if (Objects.isNull(tcmRequest))
            throw new IllegalArgumentException("参数不能为空");
        if (testingDao.saveQuestion(tcmRequest.getQuestion(), tcmRequest.getSomatoType()) != 1) {
            throw new IllegalStateException("保存失败");
        }
    }

    @Data
    class Item {
        private String name;
        private int age;
        private BigDecimal score;

        public Item(String name, int age, BigDecimal score) {
            this.name = name;
            this.age = age;
            this.score = score;
        }
    }
}
