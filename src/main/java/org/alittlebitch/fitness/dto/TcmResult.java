package org.alittlebitch.fitness.dto;

import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/17 3:45
 */
@Getter
@Setter
public class TcmResult {
    private SomatoType somatoType;
    private List<Integer> score;
}
