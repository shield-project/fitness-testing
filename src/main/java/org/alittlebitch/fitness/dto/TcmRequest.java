package org.alittlebitch.fitness.dto;

import lombok.Getter;
import lombok.Setter;
import org.alittlebitch.fitness.tcm.enums.SomatoType;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:44
 */
@Getter
@Setter
public class TcmRequest {
    /**
     * start for save question
     */
    private String question;
    private SomatoType somatoType;

    /**
     * end for save question
     */

    //this field for testing score
    private List<TcmResult> tcmResult;
    private UserInfo userInfo;

}
