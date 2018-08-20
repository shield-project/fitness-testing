package org.alittlebitch.fitness.dto;

import lombok.Data;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

/**
 * @author ShawnShoper
 * @date 2018/8/17 3:45
 */
@Data
public class TcmResult {
    private SomatoType somatoType;
    private Double score;
    private boolean active = false;

    public TcmResult() {
    }

    public TcmResult(SomatoType somatoType, Double score) {
        this.somatoType = somatoType;
        this.score = score;
    }

}
