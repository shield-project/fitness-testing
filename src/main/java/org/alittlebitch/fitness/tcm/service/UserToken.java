package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.tcm.bean.User;
import org.shoper.commons.core.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ShawnShoper
 * @date 2018/8/23 10:59
 */
public class UserToken {
    static final String TOKEN = "testing-token";
    private static Map<String, User> userToken = new HashMap<>();

    public static String login(User user) {
        String md5Code = MD5Util.getMD5Code(user.getName() + user.getPassword() + user.getUsername());
        if (!userToken.containsKey(md5Code))
            userToken.put(md5Code, user);
        return md5Code;
    }

    public static void check(HttpServletRequest request) throws IllegalAccessException {
        String header = request.getHeader(TOKEN);
        if (!userToken.containsKey(header)) throw new IllegalAccessException("无权限");
    }
}
