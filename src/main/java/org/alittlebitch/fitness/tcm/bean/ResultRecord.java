package org.alittlebitch.fitness.tcm.bean;

import lombok.Data;
import org.alittlebitch.fitness.dto.UserInfo;
import org.alittlebitch.fitness.tcm.enums.BiasedDetermination;
import org.alittlebitch.fitness.tcm.enums.MildDetermination;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/21 0:01
 */
@Data
public class ResultRecord {
    private int id;
    private MildDetermination mildDetermination;
    private BiasedDetermination biasedDetermination;
    private List<SomatoType> biaseds;
    private UserInfo userInfo;
}
