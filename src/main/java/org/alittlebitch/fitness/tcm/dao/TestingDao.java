package org.alittlebitch.fitness.tcm.dao;

import org.alittlebitch.fitness.tcm.bean.Analysis;
import org.alittlebitch.fitness.tcm.bean.Question;
import org.alittlebitch.fitness.tcm.bean.TcmUser;
import org.alittlebitch.fitness.tcm.enums.Determination;
import org.alittlebitch.fitness.tcm.enums.SomatoType;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Insert("insert into testing_score_record (id,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical,name,phone,sex,age,address,create_time) VALUES (#{id},#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical},#{name},#{phone},#{sex},#{age},#{address},SYSDATE());")
    int saveUserResult(@Param("id") String id, @Param("yanginsufficiency") double yanginsufficiency, @Param("yindeficiency") double yindeficiency, @Param("faintphysical") double faintphysical, @Param("phlegmdampness") double phlegmdampness, @Param("dampnessheat") double dampnessheat, @Param("bloodstasis") double bloodstasis, @Param("tebing") double tebing, @Param("qistagnation") double qistagnation, @Param("mildphysical") double mildphysical, @Param("name") String name, @Param("phone") String phone, @Param("sex") String sex, @Param("age") int age, @Param("address") String address);

    @Select("select id,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical,name,phone,sex,age,address from testing_score_record where id = #{id};")
    Map<String, Object> queryResult(@Param("id") String id);

    @Select("select * from testing_analysis")
    List<Analysis> queryAllAnalysis();

    @Insert("insert into testing_unanalysis (value,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical) values (#{md5value},#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical})")
    int saveUnAnalysisData(@Param("md5value") String value, @Param("yanginsufficiency") Determination yanginsufficiency, @Param("yindeficiency") Determination yindeficiency, @Param("faintphysical") Determination faintphysical, @Param("phlegmdampness") Determination phlegmdampness, @Param("dampnessheat") Determination dampnessheat, @Param("bloodstasis") Determination bloodstasis, @Param("tebing") Determination tebing, @Param("qistagnation") Determination qistagnation, @Param("mildphysical") Determination mildphysical);

    @Select("${sql}")
    List<Analysis> getAnalysis(@Param("sql") String sql);

    @Select("select count(1) from testing_analysis where name = #{unscrambleName}")
    int existsUnscramble(@Param("unscrambleName") String unscrambleName);

    @Insert("insert into testing_analysis (name,analysis,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical) values (#{name},#{analysis},#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical})")
    int saveUnscramble(@Param("name") String name, @Param("analysis") String analysis, @Param("yanginsufficiency") Determination yanginsufficiency, @Param("yindeficiency") Determination yindeficiency, @Param("faintphysical") Determination faintphysical, @Param("phlegmdampness") Determination phlegmdampness, @Param("dampnessheat") Determination dampnessheat, @Param("bloodstasis") Determination bloodstasis, @Param("tebing") Determination tebing, @Param("qistagnation") Determination qistagnation, @Param("mildphysical") Determination mildphysical);

    @Delete("delete from testing_analysis where id = #{id}")
    int deleteUnscramble(@Param("id") int id);

    @Select("select * from testing_unanalysis limit #{offset},#{pageSize}")
    List<Analysis> getUnanalysis(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("select count(0) from testing_analysis where name like '%${unscrambleName}%'")
    int countUnscramble(@Param("unscrambleName") String name);

    @Select("select * from testing_analysis where id = #{unscrambleId}")
    Analysis existsUnscrambleById(@Param("unscrambleId") int id);

    @Update("update testing_analysis set analysis = #{analysis}, name = #{name},yanginsufficiency= #{yanginsufficiency},yindeficiency=#{yindeficiency},faintphysical=#{faintphysical},phlegmdampness=#{phlegmdampness},dampnessheat=#{dampnessheat},bloodstasis=#{bloodstasis},tebing=#{tebing},qistagnation=#{qistagnation},mildphysical=#{mildphysical} where id =#{uid}")
    int updateUnscramble(@Param("uid") int id, @Param("name") String name,
                         @Param("analysis") String analysis,
                         @Param("yanginsufficiency") Determination yanginsufficiency,
                         @Param("yindeficiency") Determination yindeficiency,
                         @Param("faintphysical") Determination faintphysical,
                         @Param("phlegmdampness") Determination phlegmdampness,
                         @Param("dampnessheat") Determination dampnessheat,
                         @Param("bloodstasis") Determination bloodstasis,
                         @Param("tebing") Determination tebing,
                         @Param("qistagnation") Determination qistagnation,
                         @Param("mildphysical") Determination mildphysical);

    @Update("update testing_score_record set analysis_id = #{aid} where id = #{rid}")
    int updateIsAnalyze(@Param("rid") String rid, @Param("aid") int aid);

    @Select("select count(0) from testing_unanalysis where value = #{md5Code}")
    int existsUnAnalysisData(@Param("md5Code") String md5Code);

    @Select("${sql}")
    List<TcmUser> getTcmUser(@Param("sql") String sql);

    @Insert("insert into testing_user_analysis (user_id,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical,analysis) values (#{userId},#{yanginsufficiency},#{yindeficiency},#{faintphysical},#{phlegmdampness},#{dampnessheat},#{bloodstasis},#{tebing},#{qistagnation},#{mildphysical},#{analysis})")
    int saveUserAnalyze(@Param("userId") String user_id, @Param("yanginsufficiency") String yanginsufficiency, @Param("yindeficiency") String yindeficiency, @Param("faintphysical") String faintphysical, @Param("phlegmdampness") String phlegmdampness, @Param("dampnessheat") String dampnessheat, @Param("bloodstasis") String bloodstasis, @Param("tebing") String tebing, @Param("qistagnation") String qistagnation, @Param("mildphysical") String mildphysical, @Param("analysis") String analysis);

    @Select("select user_id,yanginsufficiency,yindeficiency,faintphysical,phlegmdampness,dampnessheat,bloodstasis,tebing,qistagnation,mildphysical,analysis from testing_user_analysis where user_id = #{userId}")
    Map<String, Object> queryUserAnalyze(String userId);

    @Select("select count(0) from testing_user_analysis where user_id = #{phone}")
    int existsUserAnalyze(@Param("phone") String phone);

    @Delete("delete from testing_user_analysis where user_id = #{phone}")
    int deleteUserAnalyze(@Param("phone") String phone);

    @Select("select count(0) from testing_score_record where phone = #{phone}")
    int existsUserResult(@Param("phone") String phone);

    @Delete("delete from testing_score_record where phone = #{phone}")
    int deleteUserResult(@Param("phone") String phone);

    @Select("${tcmUserSql}")
    int countTcmUser(@Param("tcmUserSql") String sql);
}
