package org.alittlebitch.fitness.tcm.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.Date;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:36
 */
@Getter
@Setter
public class Question {
    private Long id;
    private String question;
    private SomatoType somatoType;
    @JsonIgnore
    private Date createTime;

    private int sortType;
}
