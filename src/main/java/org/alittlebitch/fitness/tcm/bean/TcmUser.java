package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author ShawnShoper
 * @date 2018/8/24 14:53
 */
@Data
public class TcmUser {
    private String id;
    private int age;
    private Double yanginsufficiency, yindeficiency, faintphysical, phlegmdampness, dampnessheat, bloodstasis, tebing, qistagnation, mildphysical;
    private String name, phone, sex, address;
    private Date createTime;
    private Integer analysisId;
}
