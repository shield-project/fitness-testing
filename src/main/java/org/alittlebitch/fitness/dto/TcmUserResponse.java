package org.alittlebitch.fitness.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/24 15:24
 */
@Data
public class TcmUserResponse {
    UserInfo userInfo;
    List<UnscrambleInfo> unscramble;
    String unscrambleContent;
    boolean analysis;
}
