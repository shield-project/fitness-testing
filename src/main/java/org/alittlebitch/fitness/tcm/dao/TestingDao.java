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

    @Select("select question,id,somato_type,create_time from testing_question order by create_time asc")
    List<Question> findQuestion();

    @Insert("insert into testing_question (question,somato_type) values (#{question},#{somatoType})")
    int saveQuestion(@Param("question") String question, @Param("somatoType") SomatoType somatoType);
}
