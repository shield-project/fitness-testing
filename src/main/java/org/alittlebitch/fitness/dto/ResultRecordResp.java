package org.alittlebitch.fitness.dto;

import lombok.Data;
import org.alittlebitch.fitness.tcm.enums.BiasedDetermination;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;
import java.util.Map;

/**
 * @author ShawnShoper
 * @date 2018/8/21 1:35
 */
@Data
public class ResultRecordResp {
    private int id;
    private MildDetermination mildDetermination;
    private BiasedDetermination biasedDetermination;
    private List<SomatoInfo> biaseds;
    private UserInfo userInfo;
    private Map<SomatoType, Double> score;
    @Data
    static class SomatoInfo {
        private String name;
        private String value;

        public SomatoInfo() {
        }

        public SomatoInfo(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

}
