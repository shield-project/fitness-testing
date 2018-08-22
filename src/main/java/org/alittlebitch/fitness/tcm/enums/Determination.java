package org.alittlebitch.fitness.tcm.enums;

import lombok.Getter;

/**
 * @author ShawnShoper
 * @date 2018/8/19 21:27
 */
@Getter
public enum Determination {
    YES("是"), MAYBE("倾向是"), NO("否");
    private String name;

    Determination(String name) {
        this.name = name;
    }
}
