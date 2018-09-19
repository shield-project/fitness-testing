package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;
import org.alittlebitch.fitness.tcm.enums.Determination;

/**
 * @author ShawnShoper
 * @date 2018/8/21 13:48
 */
@Data
public class SomatoInfo {
    private String typeName;
    private String typeValue;
    private Determination determination = Determination.NO;
    private Double percent;

    public SomatoInfo(String typeName, String typeValue, Double percent) {
        this.typeName = typeName;
        this.typeValue = typeValue;
        this.percent = percent;
    }

    public SomatoInfo() {
    }
}
