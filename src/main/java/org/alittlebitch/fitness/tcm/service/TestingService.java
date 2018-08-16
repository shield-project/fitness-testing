package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.tcm.bean.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/16 17:32
 */
@Service
public class TestingService {
    public long testCount() {
        return 0;
    }

    public List<Question> question() {
        return new ArrayList<>();
    }
}
