package org.alittlebitch.fitness.dto;

import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:44
 */
@Getter
@Setter
public class TcmRequest {
    private String question;
    private SomatoType somatoType;
    private List<Integer> score;
}
