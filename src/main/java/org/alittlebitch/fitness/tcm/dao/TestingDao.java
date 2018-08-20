package org.alittlebitch.fitness.tcm.dao;

import org.alittlebitch.fitness.tcm.bean.Question;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/17 0:46
 */
@Mapper
public interface TestingDao {
    @Select("select count(0) from testing_question")
    long testCount();

    @Select("select question,id,somato_type,create_time,sort_type from testing_question order by somato_type asc")
    List<Question> findQuestion();

    @Insert("insert into testing_question (question,somato_type) values (#{question},#{somatoType})")
    int saveQuestion(@Param("question") String question, @Param("somatoType") SomatoType somatoType);

    @Insert("insert into testing_score_record (yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical) VALUES (#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical});")
    @Options(useGeneratedKeys = true)
    Integer saveUserResult(double yanginsufficiency, double yindeficiency, double faintphysical, double phlegmdampness, double dampnessheat, double bloodstasis, double tebing, double qistagnation, double mildphysical);
}
