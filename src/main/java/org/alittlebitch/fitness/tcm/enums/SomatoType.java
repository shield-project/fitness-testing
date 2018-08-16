package org.alittlebitch.fitness.tcm.enums;

import lombok.Getter;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:08
 */
@Getter
public enum SomatoType {
    YANGINSUFFICIENCY(0, "阳虚质"),//阳虚质
    YINDEFICIENCY(1, "阴虚质"),//阴虚质
    FAINTPHYSICAL(2, "气虚质"),//气虚质
    PHLEGMDAMPNESS(3, "痰湿质"),//痰湿质
    DAMPNESSHEAT(4, "湿热质"),//湿热质
    BLOODSTASIS(5, "血瘀质"),//血瘀质
    TEBING(6, "特禀质"),//特禀质
    QISTAGNATION(7, "气郁质"),//气郁质
    MILDPHYSICAL(8, "平和质");//平和质
    private int value;
    private String title;

    SomatoType(int value, String title) {
        this.value = value;
        this.title = title;
    }
}
