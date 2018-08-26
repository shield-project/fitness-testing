package org.alittlebitch.fitness.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/19 19:08
 */
@Data
public class TcmQuestion {
    private Long id;
    private String question;
    private String typeName;
    private String typeValue;
    private String sortType;
    private List<TcmQuestionA> other;

    @Override
    public String toString() {
        return "TcmQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", sortType='" + sortType + '\'' +
                ", other=" + other +
                '}';
    }
}
