package org.alittlebitch.fitness.tcm.enums;

import lombok.Getter;

/**
 * @author ShawnShoper
 * @date 2018/8/19 21:26
 */
@Getter
public enum MildDetermination {
    YES("是"), MAYBE("基本是"), NO("否");
    private String name;

    MildDetermination(String name) {
        this.name = name;
    }
}
