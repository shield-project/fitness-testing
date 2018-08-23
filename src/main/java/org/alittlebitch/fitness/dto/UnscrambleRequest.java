package org.alittlebitch.fitness.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/23 11:48
 */
@Data
public class UnscrambleRequest {
    private int id;
    private String unscrambleName;
    private List<UnscrambleInfo> adapterPhysique;
}
