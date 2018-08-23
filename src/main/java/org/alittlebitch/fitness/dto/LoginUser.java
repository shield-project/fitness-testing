package org.alittlebitch.fitness.dto;

import lombok.Data;
import org.alittlebitch.fitness.tcm.bean.User;

/**
 * @author ShawnShoper
 * @date 2018/8/23 11:04
 */
@Data
public class LoginUser {
    private User user;
    private String token;
}
