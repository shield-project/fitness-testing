package org.alittlebitch.fitness.dto;

import lombok.Data;
import org.alittlebitch.fitness.tcm.enums.Determination;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

/**
 * @author ShawnShoper
 * @date 2018/8/23 11:49
 */
@Data
public class UnscrambleInfo {
    private SomatoType somatoType;
    private Determination isAccord;
}
