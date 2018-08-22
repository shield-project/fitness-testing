package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;
import org.alittlebitch.fitness.tcm.enums.Determination;

/**
 * @author ShawnShoper
 * @date 2018/8/22 22:38
 */
@Data
public class Analysis {
    private int id;
    private Determination yanginsufficiency, yindeficiency, faintphysical, phlegmdampness, dampnessheat, bloodstasis, tebing, qistagnation, mildphysical;
    private String analysis;
}
