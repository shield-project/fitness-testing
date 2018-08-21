package org.alittlebitch.fitness.tcm.dao;

import org.alittlebitch.fitness.tcm.bean.Question;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Insert("insert into testing_score_record (id,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical,name,phone,sex,age,address) VALUES (#{id},#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical},#{name},#{phone},#{sex},#{age},#{address});")
    int saveUserResult(@Param("id") String id, @Param("yanginsufficiency") double yanginsufficiency, @Param("yindeficiency") double yindeficiency, @Param("faintphysical") double faintphysical, @Param("phlegmdampness") double phlegmdampness, @Param("dampnessheat") double dampnessheat, @Param("bloodstasis") double bloodstasis, @Param("tebing") double tebing, @Param("qistagnation") double qistagnation, @Param("mildphysical") double mildphysical, @Param("name") String name, @Param("phone") String phone, @Param("sex") String sex, @Param("age") int age, @Param("address") String address);
}
