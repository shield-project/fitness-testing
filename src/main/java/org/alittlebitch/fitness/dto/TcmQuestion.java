package org.alittlebitch.fitness.dto;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/8/19 19:08
 */
@Data
public class TcmQuestion {
    private String sex;
    private Long id;
    private String question;
    private String typeName;
    private String typeValue;
    private String sortType;
    private List<TcmQuestionA> other;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcmQuestion that = (TcmQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
