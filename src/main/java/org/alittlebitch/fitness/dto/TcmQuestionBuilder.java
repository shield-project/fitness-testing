package org.alittlebitch.fitness.dto;

import org.alittlebitch.fitness.tcm.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/17 1:12
 */
public class TcmQuestionBuilder {

    public static List<TcmQuestion> create(List<Question> questions) {
        List<TcmQuestion> tcmQuestionResps = new ArrayList<>(questions.size());
        questions.forEach(e -> tcmQuestionResps.add(create(e)));
//        collect.keySet().stream().map(e-> create(e, collect.get(e))).
        return tcmQuestionResps;
    }

    public static TcmQuestion create(Question question) {
        TcmQuestion tcmQuestion = new TcmQuestion();
        tcmQuestion.setId(question.getId());
        tcmQuestion.setTypeName(question.getSomatoType().getTitle());
        tcmQuestion.setQuestion(question.getQuestion());
        if (question.getSortType() == 0) {
            tcmQuestion.setSortType("ASC");
        } else {
            tcmQuestion.setSortType("DESC");
        }
        tcmQuestion.setTypeValue(question.getSomatoType().name());
        return tcmQuestion;
    }
}
