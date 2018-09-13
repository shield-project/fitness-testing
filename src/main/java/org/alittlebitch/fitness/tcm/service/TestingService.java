package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.dto.*;
import org.alittlebitch.fitness.tcm.bean.Analysis;
import org.alittlebitch.fitness.tcm.bean.ResultRecord;
import org.alittlebitch.fitness.tcm.bean.SomatoInfo;
import org.alittlebitch.fitness.tcm.bean.TcmUser;
import org.alittlebitch.fitness.tcm.dao.TestingDao;
import org.alittlebitch.fitness.tcm.enums.Determination;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.shoper.commons.core.CloneUtil;
import org.shoper.commons.core.MD5Util;
import org.shoper.commons.core.StringUtil;
import org.shoper.commons.core.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.alittlebitch.fitness.tcm.enums.SomatoType.*;

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

    public TcmQuestionResp question(String sex) {
        List<TcmQuestion> tcmQuestions = null;
        if (StringUtil.isEmpty(sex))
            tcmQuestions = TcmQuestionBuilder.create(testingDao.findQuestion());
        else
            tcmQuestions = TcmQuestionBuilder.create(testingDao.findQuestionNotBySex(sex));
        Map<String, List<TcmQuestion>> repeat = new HashMap<>();
        for (TcmQuestion tcmQuestion : tcmQuestions) {
            for (TcmQuestion question : tcmQuestions) {
                if (question.getQuestion().equals(tcmQuestion.getQuestion()) && !question.getTypeValue().equals(tcmQuestion.getTypeValue())) {
                    if (!question.getTypeValue().equals(SomatoType.MILDPHYSICAL.getValue())) {
                        if (!repeat.containsKey(question.getQuestion()))
                            repeat.put(question.getQuestion(), new ArrayList<>());
                        repeat.get(question.getQuestion()).add(question);
                    }
                }
            }
        }

        Map<Long, List<TcmQuestionA>> pojo = new HashMap<>();
        tcmQuestions.stream().filter(e -> repeat.containsKey(e.getQuestion())).forEach(e -> {
            List<TcmQuestion> tcmQuestions1 = repeat.get(e.getQuestion());
            List<TcmQuestionA> collect = tcmQuestions1.stream().filter(b -> !e.getTypeValue().equals(b.getTypeValue())).map(c -> {
                TcmQuestionA tcmQuestionA = new TcmQuestionA();
                tcmQuestionA.setId(c.getId());
                tcmQuestionA.setQuestion(c.getQuestion());
                tcmQuestionA.setSortType(c.getSortType());
                tcmQuestionA.setTypeName(c.getTypeName());
                tcmQuestionA.setSex(c.getSex());
                tcmQuestionA.setTypeValue(c.getTypeValue());
                return tcmQuestionA;
            }).collect(Collectors.toList());
            pojo.put(e.getId(), collect);
//            e.setOther(collect);
        });
        List<TcmQuestionA> needRemove = new ArrayList<>();
        for (int i = 0; i < tcmQuestions.size(); i++) {
            TcmQuestion tcmQuestion = tcmQuestions.get(i);
            if (tcmQuestion.getTypeValue().equals(SomatoType.MILDPHYSICAL.getValue())) continue;
            if (pojo.containsKey(tcmQuestion.getId())) {
                List<TcmQuestionA> tcmQuestionAS = pojo.get(tcmQuestion.getId());
                tcmQuestion.setOther(tcmQuestionAS);
            }
        }

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
            Double percent = (100 * ((value - count) / (count * 4)));
            somatoInfos.add(new SomatoInfo(e.getKey().getTitle(), e.getKey().name(), percent));
        });
//        Optional<SomatoInfo> first = somatoInfos.stream().filter(e -> e.getTypeValue().equals(SomatoType.MILDPHYSICAL.name())).findFirst();
//        SomatoInfo mildPhysical = first.get();
//        somatoInfos.remove(mildPhysical);
//        Map<String, Float> biasedMap = new HashMap<>();
//        somatoInfos.stream().forEach(e -> biasedMap.put(e.getTypeName(), e.getPercent()));
//        List<String> gte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 40 && e.getValue() >= 30).map(Map.Entry::getKey).collect(toList());
//        List<String> lte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 30).map(Map.Entry::getKey).collect(toList());
//        List<String> gte40 = biasedMap.entrySet().stream().filter(e -> e.getValue() >= 40).map(Map.Entry::getKey).collect(toList());
//        if (!gte40.isEmpty()) {
//            biasedMap.entrySet().stream().forEach(e -> {
//                if (e.getValue() >= 40) {
//                    String key = e.getKey();
//                    somatoInfos.stream().filter(b -> b.getTypeName().equalsIgnoreCase(key)).forEach(c -> c.setActive(true));
//                }
//            });
//        } else {
//            if (!lte30.isEmpty() && mildPhysical.getPercent() >= 60)
//                mildPhysical.setActive(true);
//            else if (!gte30.isEmpty() && mildPhysical.getPercent() >= 60) {
//                mildPhysical.setActive(true);
//                gte30.stream().forEach(e -> somatoInfos.stream().filter(s -> s.getTypeName().equals(e)).forEach(b -> b.setActive(true)));
//            } else
//                mildPhysical.setActive(false);
//            biasedMap.entrySet().stream().forEach(e -> {
//                if (e.getValue() > 30) {
//                    String key = e.getKey();
//                    somatoInfos.stream().filter(b -> b.getTypeName().equals(key)).forEach(c -> c.setActive(true));
//                }
//            });
//        }
        String id = UUID.randomUUID().toString();
//        somatoInfos.add(mildPhysical);
        Map<String, Double> score = new HashMap<>();
        somatoInfos.stream().forEach(e -> score.put(e.getTypeValue(), e.getPercent()));
        if (testingDao.existsUserResult(userInfo.getOpenId()) == 1)
            testingDao.deleteUserResult(userInfo.getOpenId());
        testingDao.saveUserResult(id, score.get(YANGINSUFFICIENCY.name()), score.get(YINDEFICIENCY.name()), score.get(FAINTPHYSICAL.name()), score.get(PHLEGMDAMPNESS.name()), score.get(DAMPNESSHEAT.name()), score.get(BLOODSTASIS.name()), score.get(TEBING.name()), score.get(QISTAGNATION.name()), score.get(MILDPHYSICAL.name()), userInfo.getName(), userInfo.getPhone(), userInfo.getSex(), userInfo.getAge(), userInfo.getAddress(), userInfo.getOpenId());
//        resultRecord.setTestResult(somatoInfos);
//        resultRecord.setUserInfo(userInfo);
//        resultRecord.setId();
        //先提取平和质的属性
//        Map<SomatoType, Determination> mildMap = new HashMap<>();
//        mildMap.put(SomatoType.MILDPHYSICAL, null);
//        Double mildScore = somatoTypeScoreMap.get(SomatoType.MILDPHYSICAL);
//        //去除平和质属性
//        Map<SomatoType, Double> map = CloneUtil.depthClone(somatoTypeScoreMap, Map.class);
//        map.remove(SomatoType.MILDPHYSICAL);
//        //提取出分数30-39的偏颇
//        Map<SomatoType, Determination> biasedMap = new HashMap<>();
//        map.entrySet().forEach((e) -> {
//            if (e.getValue() < 30)
//                biasedMap.put(e.getKey(), Determination.NO);
//            else if (e.getValue() >= 30 && e.getValue() < 40)
//                biasedMap.put(e.getKey(), Determination.MAYBE);
//            else
//                biasedMap.put(e.getKey(), Determination.YES);
//        });
//        if (mildScore >= 60 && biasedMap.values().contains(Determination.NO))
//            mildMap.put(SomatoType.MILDPHYSICAL, Determination.YES);
//        else if (mildScore >= 60 && biasedMap.values().contains(Determination.YES))
//            mildMap.put(SomatoType.MILDPHYSICAL, Determination.MAYBE);
//        else
//            mildMap.put(SomatoType.MILDPHYSICAL, Determination.NO);
//
//        //构建result record
//        ResultRecord resultRecord = new ResultRecord();
//        resultRecord.setScore(somatoTypeScoreMap);
//        if (mildMap.get(SomatoType.MILDPHYSICAL) != Determination.NO) {
//            //主要体征为平和质
//            resultRecord.setDetermination(mildMap.get(SomatoType.MILDPHYSICAL));
//        }
        //提取偏颇体质为
//        List<SomatoType> biaseds = biasedMap.entrySet().stream().filter(e -> e.getValue() != Determination.NO).map(e -> e.getKey()).collect(Collectors.toList());
//        if (!biaseds.isEmpty()) {
//            List<Map.Entry<SomatoType, Determination>> anyYesBiased = biasedMap.entrySet().stream()
//                    .filter(e -> e.getValue().equals(Determination.NO)
//                    )
//                    .collect(Collectors.toList());
//            if (!anyYesBiased.isEmpty()) {
//                resultRecord.setDetermination(Determination.YES);
//                resultRecord.setBiaseds(anyYesBiased.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
//            } else {
//                List<Map.Entry<SomatoType, Determination>> anyMaybeBiased = biasedMap.entrySet().stream()
//                        .filter(e -> e.getValue().equals(Determination.MAYBE)
//                        )
//                        .collect(Collectors.toList());
//                if (!anyMaybeBiased.isEmpty()) {
//                    resultRecord.setDetermination(Determination.MAYBE);
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
        return id;
    }

    public void saveQuestion(TcmRequest tcmRequest) {
        if (Objects.isNull(tcmRequest))
            throw new IllegalArgumentException("参数不能为空");
        if (testingDao.saveQuestion(tcmRequest.getQuestion(), tcmRequest.getSomatoType()) != 1) {
            throw new IllegalStateException("保存失败");
        }
    }

    public Object result(String id) throws SystemException {
        Map<String, Object> resultRecordMap = testingDao.queryResult(id);
        ResultRecord resultRecord = new ResultRecord();
        resultRecord.setId((String) resultRecordMap.get("id"));
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress((String) resultRecordMap.get("address"));
        userInfo.setAge((Integer) resultRecordMap.get("age"));
        userInfo.setName((String) resultRecordMap.get("name"));
        userInfo.setPhone((String) resultRecordMap.get("phone"));
        userInfo.setSex((String) resultRecordMap.get("sex"));
        Double yanginsufficiency = (Double) resultRecordMap.get("yanginsufficiency");
        Double yindeficiency = (Double) resultRecordMap.get("yindeficiency");
        Double faintphysical = (Double) resultRecordMap.get("faintphysical");
        Double phlegmdampness = (Double) resultRecordMap.get("phlegmdampness");
        Double dampnessheat = (Double) resultRecordMap.get("dampnessheat");
        Double bloodstasis = (Double) resultRecordMap.get("bloodstasis");
        Double tebing = (Double) resultRecordMap.get("tebing");
        Double qistagnation = (Double) resultRecordMap.get("qistagnation");
        Double mildphysical = (Double) resultRecordMap.get("mildphysical");
        List<SomatoInfo> somatoInfos = new ArrayList<>(9);
        somatoInfos.add(new SomatoInfo(YANGINSUFFICIENCY.getTitle(), YANGINSUFFICIENCY.name(), yanginsufficiency));
        somatoInfos.add(new SomatoInfo(YINDEFICIENCY.getTitle(), YINDEFICIENCY.name(), yindeficiency));
        somatoInfos.add(new SomatoInfo(FAINTPHYSICAL.getTitle(), FAINTPHYSICAL.name(), faintphysical));
        somatoInfos.add(new SomatoInfo(PHLEGMDAMPNESS.getTitle(), PHLEGMDAMPNESS.name(), phlegmdampness));
        somatoInfos.add(new SomatoInfo(DAMPNESSHEAT.getTitle(), DAMPNESSHEAT.name(), dampnessheat));
        somatoInfos.add(new SomatoInfo(BLOODSTASIS.getTitle(), BLOODSTASIS.name(), bloodstasis));
        somatoInfos.add(new SomatoInfo(TEBING.getTitle(), TEBING.name(), tebing));
        somatoInfos.add(new SomatoInfo(QISTAGNATION.getTitle(), QISTAGNATION.name(), qistagnation));
        somatoInfos.add(new SomatoInfo(MILDPHYSICAL.getTitle(), MILDPHYSICAL.name(), mildphysical));

        {
            Optional<SomatoInfo> first = somatoInfos.stream().filter(e -> e.getTypeValue().equals(SomatoType.MILDPHYSICAL.name())).findFirst();
            SomatoInfo mildPhysical = first.get();
            somatoInfos.remove(mildPhysical);
            Map<String, Double> biasedMap = new HashMap<>();
            somatoInfos.stream().forEach(e -> biasedMap.put(e.getTypeName(), e.getPercent()));
            List<String> gte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 40 && e.getValue() >= 30).map(Map.Entry::getKey).collect(toList());
            List<String> lte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 30).map(Map.Entry::getKey).collect(toList());
            List<String> gte40 = biasedMap.entrySet().stream().filter(e -> e.getValue() >= 40).map(Map.Entry::getKey).collect(toList());
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
        }


        resultRecord.setTestResult(somatoInfos);
        resultRecord.setUserInfo(userInfo);

        Map<SomatoType, Double> somatoTypeFloatMap = new HashMap<>();
        somatoInfos.stream().forEach(e -> somatoTypeFloatMap.put(SomatoType.valueOf(e.getTypeValue()), e.getPercent()));

        //查询解读
        Double mildScore = somatoTypeFloatMap.get(MILDPHYSICAL);
        somatoTypeFloatMap.remove(SomatoType.MILDPHYSICAL);
        Map<SomatoType, Double> biasedMap = CloneUtil.depthClone(somatoTypeFloatMap, Map.class);

        Map<SomatoType, Determination> determinationDeter = new HashMap<>();
        List<SomatoType> gte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 40 && e.getValue() >= 30).map(Map.Entry::getKey).collect(toList());
        List<SomatoType> lte30 = biasedMap.entrySet().stream().filter(e -> e.getValue() < 30).map(Map.Entry::getKey).collect(toList());
        List<SomatoType> gte40 = biasedMap.entrySet().stream().filter(e -> e.getValue() >= 40).map(Map.Entry::getKey).collect(toList());
        if (!gte40.isEmpty()) {
            biasedMap.entrySet().stream().forEach(e -> {
                if (gte40.contains(e.getKey())) {
                    SomatoType key = e.getKey();
                    somatoInfos.stream().filter(b -> b.getTypeValue().equals(key.name())).forEach(c -> determinationDeter.put(SomatoType.valueOf(c.getTypeValue()), Determination.YES));
                }
            });
            determinationDeter.put(SomatoType.MILDPHYSICAL, Determination.NO);
        } else {
            if (!lte30.isEmpty() && mildScore >= 60)
                determinationDeter.put(SomatoType.MILDPHYSICAL, Determination.YES);
            else if (!gte30.isEmpty() && mildScore >= 60) {
                determinationDeter.put(SomatoType.MILDPHYSICAL, Determination.MAYBE);
                gte30.stream().forEach(e -> somatoInfos.stream().filter(s -> s.getTypeName().equals(e)).forEach(b -> determinationDeter.put(SomatoType.valueOf(b.getTypeValue()), Determination.MAYBE)));
            } else
                determinationDeter.put(SomatoType.MILDPHYSICAL, Determination.NO);
        }
        biasedMap.entrySet().stream().forEach(e -> {
            if (!determinationDeter.containsKey(e.getKey())) {
                determinationDeter.put(e.getKey(), Determination.NO);
            }
        });

        //查询数据库所有数据进行内存运算
        List<Analysis> analyses = testingDao.queryAllAnalysis();
//        analyses.get(0).getBloodstasis()
        Optional<Analysis> anyAnalysis = analyses.stream().map(b -> {
            boolean mildphysicalFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.MILDPHYSICAL);
                mildphysicalFlag = judge(b.getMildphysical(), determination);
            }
            boolean yanginsufficiencyFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.YANGINSUFFICIENCY);
                yanginsufficiencyFlag = judge(b.getYanginsufficiency(), determination);
            }
            boolean bloodstasisFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.BLOODSTASIS);
                bloodstasisFlag = judge(b.getBloodstasis(), determination);
            }
            boolean dampnessheatFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.DAMPNESSHEAT);
                dampnessheatFlag = judge(b.getDampnessheat(), determination);
            }
            boolean faintphysicalFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.FAINTPHYSICAL);
                faintphysicalFlag = judge(b.getFaintphysical(), determination);
            }
            boolean phlegmdampnessFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.PHLEGMDAMPNESS);
                phlegmdampnessFlag = judge(b.getPhlegmdampness(), determination);
            }
            boolean qistagnationFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.QISTAGNATION);
                qistagnationFlag = judge(b.getQistagnation(), determination);
            }
            boolean tebingFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.TEBING);
                tebingFlag = judge(b.getTebing(), determination);
            }
            boolean yindeficiencyFlag = false;
            {
                Determination determination = determinationDeter.get(SomatoType.YINDEFICIENCY);
                yindeficiencyFlag = judge(b.getYindeficiency(), determination);
            }
            if (mildphysicalFlag && yanginsufficiencyFlag && bloodstasisFlag && dampnessheatFlag && faintphysicalFlag && phlegmdampnessFlag && qistagnationFlag && tebingFlag && yindeficiencyFlag)
                return b;
            else return null;
        }).filter(Objects::nonNull).findAny();
        if (anyAnalysis.isPresent()) {
            //找到合适的解读
            resultRecord.setUnscrambleContent(anyAnalysis.get().getAnalysis());
            String analysis = null;
            if (anyAnalysis.isPresent())
                analysis = anyAnalysis.get().getAnalysis();
            if (testingDao.existsUserAnalyze(userInfo.getOpenId()) == 1) {
                testingDao.deleteUserAnalyze(userInfo.getOpenId());
            }
            //保存用户的解读数据determinationDeter
            testingDao.saveUserAnalyze(userInfo.getOpenId(),
                    determinationDeter.get(YANGINSUFFICIENCY).name(),
                    determinationDeter.get(YINDEFICIENCY).name(),
                    determinationDeter.get(FAINTPHYSICAL).name(),
                    determinationDeter.get(PHLEGMDAMPNESS).name(),
                    determinationDeter.get(DAMPNESSHEAT).name(),
                    determinationDeter.get(BLOODSTASIS).name(),
                    determinationDeter.get(TEBING).name(),
                    determinationDeter.get(QISTAGNATION).name(),
                    determinationDeter.get(MILDPHYSICAL).name(),
                    analysis
            );
            testingDao.updateIsAnalyze(id, anyAnalysis.get().getId());
        } else {
            //未找到合适的解读
            resultRecord.setUnscrambleContent("暂无匹配的数据,请等待专家提供！");
            //储存无法匹配的相应数据
            String md5Code = MD5Util.getMD5Code(determinationDeter.get(YANGINSUFFICIENCY).getName() + determinationDeter.get(YINDEFICIENCY).getName() + determinationDeter.get(FAINTPHYSICAL).getName() + determinationDeter.get(PHLEGMDAMPNESS).getName() + determinationDeter.get(DAMPNESSHEAT).getName() + determinationDeter.get(BLOODSTASIS).getName() + determinationDeter.get(TEBING).getName() + determinationDeter.get(QISTAGNATION).getName() + determinationDeter.get(MILDPHYSICAL).getName());
            //检查该解读是否已经存库
            if (testingDao.existsUnAnalysisData(md5Code) == 0)
                testingDao.saveUnAnalysisData(md5Code, determinationDeter.get(YANGINSUFFICIENCY), determinationDeter.get(YINDEFICIENCY), determinationDeter.get(FAINTPHYSICAL), determinationDeter.get(PHLEGMDAMPNESS), determinationDeter.get(DAMPNESSHEAT), determinationDeter.get(BLOODSTASIS), determinationDeter.get(TEBING), determinationDeter.get(QISTAGNATION), determinationDeter.get(MILDPHYSICAL));
        }

        return resultRecord;
    }

    private boolean judge(Determination a, Determination determination) {
        boolean flag = false;
        if (Determination.YES.equals(determination)) {
            if (determination.equals(a)) {
                flag = true;
            } else if (Determination.MAYBE.equals(a)) {
                flag = true;
            }
        } else if (Determination.MAYBE.equals(determination)) {
            if (Determination.MAYBE.equals(a)) flag = true;
        } else {
            if (Determination.NO.equals(a)) flag = true;
        }
        return flag;
    }

    public List<UnscrambleRequest> getUnscramble(String name, int page, int pageSize) {
        if (page == 0)
            page = 1;
        if (pageSize == 0 || pageSize > 50)
            pageSize = 10;
        StringBuilder sql = new StringBuilder();
        sql.append("select * from testing_analysis ");
        if (!StringUtil.isEmpty(name))
            sql.append("where name like '%" + name + "%' ");
        sql.append("limit " + (page - 1) * pageSize + "," + pageSize);
        List<Analysis> list = testingDao.getAnalysis(sql.toString());

        List<UnscrambleRequest> unscrambleRequests = new ArrayList<>();
        list.stream().forEach(e -> {
            UnscrambleRequest unscrambleRequest = new UnscrambleRequest();
            unscrambleRequest.setId(e.getId());
            unscrambleRequest.setUnscrambleName(e.getName());
            unscrambleRequest.setUnscrambleContent(e.getAnalysis());
            List<UnscrambleInfo> unscrambleInfos = new ArrayList<>();
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.MILDPHYSICAL);
                unscrambleInfo.setIsAccord(e.getMildphysical());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.BLOODSTASIS);
                unscrambleInfo.setIsAccord(e.getBloodstasis());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.DAMPNESSHEAT);
                unscrambleInfo.setIsAccord(e.getDampnessheat());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.FAINTPHYSICAL);
                unscrambleInfo.setIsAccord(e.getFaintphysical());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.PHLEGMDAMPNESS);
                unscrambleInfo.setIsAccord(e.getPhlegmdampness());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.QISTAGNATION);
                unscrambleInfo.setIsAccord(e.getQistagnation());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.TEBING);
                unscrambleInfo.setIsAccord(e.getTebing());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.YANGINSUFFICIENCY);
                unscrambleInfo.setIsAccord(e.getYanginsufficiency());
                unscrambleInfos.add(unscrambleInfo);
            }
            {
                UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                unscrambleInfo.setSomatoType(SomatoType.YINDEFICIENCY);
                unscrambleInfo.setIsAccord(e.getYindeficiency());
                unscrambleInfos.add(unscrambleInfo);
            }
            unscrambleRequest.setAdapterPhysique(unscrambleInfos);
            unscrambleRequests.add(unscrambleRequest);
        });

        return unscrambleRequests;
    }

    public void addUnscramble(UnscrambleRequest unscrambleRequest) {
        if (Objects.isNull(unscrambleRequest) || StringUtil.isEmpty(unscrambleRequest.getUnscrambleName()))
            throw new RuntimeException("参数不能为null");
        if (testingDao.existsUnscramble(unscrambleRequest.getUnscrambleName()) != 0)
            throw new RuntimeException("名字重复");
        Map<SomatoType, Determination> map = new HashMap();
        unscrambleRequest.getAdapterPhysique().forEach(e -> map.put(e.getSomatoType(), e.getIsAccord()));
        if (testingDao.saveUnscramble(unscrambleRequest.getUnscrambleName(),
                unscrambleRequest.getUnscrambleContent(),
                map.get(YANGINSUFFICIENCY),
                map.get(YINDEFICIENCY),
                map.get(FAINTPHYSICAL),
                map.get(PHLEGMDAMPNESS),
                map.get(DAMPNESSHEAT),
                map.get(BLOODSTASIS),
                map.get(TEBING),
                map.get(QISTAGNATION),
                map.get(MILDPHYSICAL)
        ) != 1) {
            throw new RuntimeException("添加解读失败");
        }
    }

    public void deleteUnscramble(int id) {
        if (testingDao.deleteUnscramble(id) != 1)
            throw new RuntimeException("删除失败");
    }

    public List<Analysis> getUnanalysis(int page, int pageSize) {
        if (page == 0)
            page = 1;
        if (pageSize == 0 || pageSize > 50)
            pageSize = 10;
        return testingDao.getUnanalysis((page - 1) * pageSize, pageSize);
    }

    public int countUnscramble(String name) {
        return testingDao.countUnscramble(name);
    }

    public void upadteUnscramble(int id, UnscrambleRequest unscrambleRequest) {
        Analysis analysis = testingDao.existsUnscrambleById(id);
        if (Objects.isNull(analysis))
            throw new RuntimeException("数据不存在");
        if (!analysis.getName().equals(unscrambleRequest.getUnscrambleName()))
            if (testingDao.existsUnscramble(unscrambleRequest.getUnscrambleName()) != 0)
                throw new RuntimeException("名字重复");
        Map<SomatoType, Determination> map = new HashMap();
        unscrambleRequest.getAdapterPhysique().forEach(e -> map.put(e.getSomatoType(), e.getIsAccord()));
        if (testingDao.updateUnscramble(id, unscrambleRequest.getUnscrambleName(),
                unscrambleRequest.getUnscrambleContent(),
                map.get(YANGINSUFFICIENCY),
                map.get(YINDEFICIENCY),
                map.get(FAINTPHYSICAL),
                map.get(PHLEGMDAMPNESS),
                map.get(DAMPNESSHEAT),
                map.get(BLOODSTASIS),
                map.get(TEBING),
                map.get(QISTAGNATION),
                map.get(MILDPHYSICAL)
        ) != 1) {
            throw new RuntimeException("修改解读失败");
        }

    }

    public List<TcmUserResponse> getTcmUser(String name, Boolean analyze, int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0 || pageSize > 50) pageSize = 50;
        StringBuilder sql = new StringBuilder();
        sql.append("select * from testing_score_record ");
        if (analyze != null || StringUtil.nonEmpty(name)) sql.append("where ");
        if (StringUtil.nonEmpty(name))
            sql.append(" name like '%" + name + "%' ");
        if (Objects.nonNull(analyze)) {
            if (analyze)
                sql.append(" analysis_id is not NULL ");
            else if (!analyze)
                sql.append(" analysis_id is NULL ");
        }
        sql.append("limit " + (page - 1) * pageSize + "," + pageSize);
        System.out.println(sql);
        List<TcmUser> tcmUser = testingDao.getTcmUser(sql.toString());
//        List<TcmUserResponse> tcmUserResponses = new ArrayList<>();
        List<TcmUserResponse> collect = tcmUser.stream().map(e -> {
            TcmUserResponse tcmUserResponse = new TcmUserResponse();
            UserInfo userInfo = new UserInfo();
            userInfo.setSex(e.getSex());
            userInfo.setPhone(e.getPhone());
            userInfo.setAge(e.getAge());
            userInfo.setAddress(e.getAddress());
            userInfo.setName(e.getName());
            tcmUserResponse.setUserInfo(userInfo);
            if (e.getAnalysisId() != null)
                tcmUserResponse.setAnalysis(true);
            if (tcmUserResponse.isAnalysis()) {
                Map<String, Object> stringObjectMap = testingDao.queryUserAnalyze(e.getPhone());
                if (Objects.nonNull(stringObjectMap)) {
                    stringObjectMap.remove("user_id");
                    String analysis = (String) stringObjectMap.get("analysis");
                    stringObjectMap.remove("analysis");
                    List<UnscrambleInfo> unscrambleInfos = new ArrayList<>();
                    stringObjectMap.entrySet().forEach(b -> {
                        UnscrambleInfo unscrambleInfo = new UnscrambleInfo();
                        unscrambleInfo.setSomatoType(SomatoType.valueOf(b.getKey().toUpperCase()));
                        unscrambleInfo.setIsAccord(Determination.valueOf(((String) b.getValue()).toUpperCase()));
                        unscrambleInfos.add(unscrambleInfo);
                    });
                    tcmUserResponse.setUnscramble(unscrambleInfos);
                    tcmUserResponse.setUnscrambleContent(analysis);
                }
            }
            return tcmUserResponse;
        }).collect(Collectors.toList());
        return collect;
    }

    public int countTcmUser(String name, Boolean analyze) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(0) from testing_score_record ");
        if (analyze != null || StringUtil.nonEmpty(name)) sql.append("where ");
        if (StringUtil.nonEmpty(name))
            sql.append(" name like '%" + name + "%' ");
        if (Objects.nonNull(analyze)) {
            if (analyze)
                sql.append(" analysis_id is not NULL ");
            else if (!analyze)
                sql.append(" analysis_id is NULL ");
        }
        return testingDao.countTcmUser(sql.toString());
    }
}
