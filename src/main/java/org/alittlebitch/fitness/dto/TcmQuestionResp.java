package org.alittlebitch.fitness.dto;

import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.bean.Question;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/17 1:10
 */
@Getter
@Setter
public class TcmQuestionResp {
    List<Question> questions;
    private String typeName;
    private String typeValue;
}
