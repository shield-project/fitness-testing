package org.alittlebitch.fitness.tcm.enums;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:08
 */
public enum SomatoType {
    YangInsufficiency(0, "阳虚质"),//阳虚质
    YinDeficiency(1, "阳虚质"),//阴虚质
    FaintPhysical(2, "阳虚质"),//气虚质
    PhlegmDampness(3, "阳虚质"),//痰湿质
    DampnessHeat(4, "阳虚质"),//湿热质
    BloodStasis(5, "阳虚质"),//血瘀质
    Tebing(6, "阳虚质"),//特禀质
    QiStagnation(7, "阳虚质"),//气郁质
    mildPhysical(8, "阳虚质");//平和质
    private int index;
    private String name;

    SomatoType(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
