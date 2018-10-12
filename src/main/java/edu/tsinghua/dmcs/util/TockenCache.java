package edu.tsinghua.dmcs.util;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TockenCache {

    private static Map<String, String> tokens =Maps.newConcurrentMap();

    public void setTokenForUser(String token, String user) {
        tokens.put(token, user);
    }

    public boolean isTokenExist(String token) {
        return tokens.containsKey(token);
    }

    public String getUserNameByToken(String token) {
        return tokens.get(token);
    }

    public String getUserid(String token){
        if(token==null)
            return null;
        return tokens.get(token);
    }

    public String getTokenExpiredTime(String token) {
        if(token != null) {
            return token.split("|")[1];
        }
        return null;
    }

    public void removeToken(String token) {
        if(token != null) {
            tokens.remove(token);
        }
    }
}
