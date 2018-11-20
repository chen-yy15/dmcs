package edu.tsinghua.dmcs.util;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created by caizj on 18-11-8.
 * 将基本的编码，cookie的转换以及各类函数全部放在这个地方，供全部的调用
 *
 */
@Component
public class CommonTool {



    public String  ProduceToken(String key, String securitySault) {
        String token =key + securitySault + System.currentTimeMillis();
        String securetoken  = null;
        try {
            MessageDigest Digest= MessageDigest.getInstance("md5");
            byte [] By=Digest.digest(token.getBytes());
            securetoken = new  String(By);
            securetoken = securetoken + "|" + (System.currentTimeMillis() + 1000*3600);
            securetoken = new BASE64Encoder().encode(securetoken.getBytes());
        }catch( Exception e) {
            return null;
        }
        securetoken = URLEncoder.encode(securetoken);
        return securetoken;
    }

    public String translateCookie(Cookie[] cookies, String cookname){
        if(cookies==null||cookname==null){
            return null;
        }
        String token =null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookname)){
                token = cookie.getValue();
                token = URLDecoder.decode(token);
            }
        }
        return token;
    }

    public Integer ChangeStringModuleid(String module) {
        Integer moduleid = 0;
        if(module == null) {
            return moduleid;
        }
        char a = module.charAt(0);
        switch (a){
            case 'a':
                if(module.equals("aa"))
                    moduleid = 1010;
                if(module.equals("ab"))
                    moduleid = 1020;
                if(module.equals("ac"))
                    moduleid = 1030;
                if(module.equals("ad"))
                    moduleid = 1040;
                break;
            case 'b':  moduleid = 2000; break;
            case 'c':  moduleid = 3000; break;
            case 'd':  moduleid = 4000; break;
            case 'e':  moduleid = 5000; break;
            case 'f':  moduleid = 6000; break;
            case 'g':  moduleid = 7000; break;
            case 'h':  moduleid = 8000; break;
            case 'i':  moduleid = 9000; break;
            default: moduleid = 0; break;
        }
        return moduleid;
    }
// 针对各个窗口，我们采取不同的编号进行处理。这里需要分别的进行处理。
//
//

}
