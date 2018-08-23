package org.alittlebitch.fitness.tcm.service;

import io.netty.handler.codec.http.cookie.Cookie;
import org.alittlebitch.fitness.tcm.bean.User;
import org.shoper.commons.core.MD5Util;
import reactor.ipc.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public static void check(HttpServerRequest request) throws IllegalAccessException {
        boolean flag = false;
        if (request.cookies().containsKey(TOKEN)) {
            Set<Cookie> cookies = request.cookies().get(TOKEN);
            if (!cookies.isEmpty()) {
                String value = cookies.iterator().next().value();
                if (userToken.containsKey(value)) flag = true;
            }
        }
        if (!flag) throw new IllegalAccessException("无权限");
    }

}
