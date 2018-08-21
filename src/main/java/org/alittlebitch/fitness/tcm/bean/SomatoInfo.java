package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;

/**
 * @author ShawnShoper
 * @date 2018/8/21 13:48
 */
@Data
public class SomatoInfo {
    private String typeName;
    private String typeValue;
    private boolean active;
    private float percent;

    public SomatoInfo(String typeName, String typeValue, float percent) {
        this.typeName = typeName;
        this.typeValue = typeValue;
        this.percent = percent;
    }

    public SomatoInfo() {
    }
}
