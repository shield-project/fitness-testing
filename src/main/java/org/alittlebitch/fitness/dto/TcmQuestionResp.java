package org.alittlebitch.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/17 1:10
 */
@Getter
@Setter
public class TcmQuestionResp {
    private List<TcmQuestion> questions;
    private int total;
}
