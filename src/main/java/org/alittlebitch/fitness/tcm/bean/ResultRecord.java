package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;
import org.alittlebitch.fitness.dto.UserInfo;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/21 0:01
 */
@Data
public class ResultRecord {
    private String id;
    private List<SomatoInfo> testResult;
    private UserInfo userInfo;
}
