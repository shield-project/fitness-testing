package org.alittlebitch.fitness.tcm.bean;

import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:36
 */
@Getter
@Setter
public class Question {
    private String title;
    private SomatoType somatoType;
}
