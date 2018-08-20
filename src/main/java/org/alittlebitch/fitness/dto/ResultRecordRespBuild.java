package org.alittlebitch.fitness.dto;

import lombok.Data;
import org.alittlebitch.fitness.tcm.bean.ResultRecord;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShawnShoper
 * @date 2018/8/21 1:35
 */
@Data
public class ResultRecordRespBuild {
    public static ResultRecordResp create(ResultRecord resultRecord) {
        ResultRecordResp resultRecordResp = new ResultRecordResp();
        List<ResultRecordResp.SomatoInfo> biaseds = resultRecord.getBiaseds().stream().map(e -> new ResultRecordResp.SomatoInfo(e.getTitle(), e.name())).collect(Collectors.toList());
        resultRecordResp.setBiaseds(biaseds);
        resultRecordResp.setId(resultRecord.getId());
        resultRecordResp.setUserInfo(resultRecord.getUserInfo());
        resultRecordResp.setBiasedDetermination(resultRecord.getBiasedDetermination());
        resultRecordResp.setMildDetermination(resultRecord.getMildDetermination());
        return resultRecordResp;
    }

}
