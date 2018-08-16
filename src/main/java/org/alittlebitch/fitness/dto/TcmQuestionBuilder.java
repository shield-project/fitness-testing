package org.alittlebitch.fitness.dto;

import org.alittlebitch.fitness.tcm.bean.Question;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ShawnShoper
 * @date 2018/8/17 1:12
 */
public class TcmQuestionBuilder {

    public static List<TcmQuestionResp> create(List<Question> questions) {
        Map<SomatoType, List<Question>> collect = questions.stream().collect(Collectors.groupingBy(Question::getSomatoType));
        List<TcmQuestionResp> collect1 = collect.entrySet().stream().map(e -> create(e.getKey(), e.getValue())).collect(Collectors.toList());
//        collect.keySet().stream().map(e-> create(e, collect.get(e))).
        return collect1;
    }

    public static TcmQuestionResp create(SomatoType somatoType, List<Question> question) {
        TcmQuestionResp tcmQuestionResp = new TcmQuestionResp();
        tcmQuestionResp.setTypeName(somatoType.getTitle());
        tcmQuestionResp.setQuestions(question);
        tcmQuestionResp.setTypeValue(somatoType.name());
        return tcmQuestionResp;
    }
}
