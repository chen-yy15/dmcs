package edu.tsinghua.dmcs.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.util.TockenCache;
import org.apache.maven.shared.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.service.RoleService;

@Aspect  
@Component 
public class ControllerInterceptor {
	
    private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);  
    
    @Autowired
    RoleService roleService;

    @Autowired
	TockenCache tockenCache;

    @Pointcut("execution(* edu.tsinghua.dmcs.web..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")  
    public void controllerMethodPointcut(){
    }  
    
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp){ 
    	logger.info("interceptor");
    	 MethodSignature signature = (MethodSignature) pjp.getSignature();  
         Method method = signature.getMethod(); 
         DmcsController[] controllers = method.getAnnotationsByType(DmcsController.class);
         if(controllers == null || controllers.length == 0) {
        	 
        	 logger.info("contronllers");
        	 try {
				return pjp.proceed();
			} catch (Throwable e) {
				logger.error("fail to process ", e);
			}
        	 
         }
         
         DmcsController controller = controllers[0];
         String userName = null;
         if(controller.loginRequired()) {
        	logger.info("login require = " + controller.loginRequired());
 	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
 	        userName = checkLogin(request);
 	        if(userName == null) {
				 return Response.NEW().loginRequired();
			 }
         }
         
         String roleRequireds = controller.roleAllowed();
         String [] roles = StringUtils.split(roleRequireds, ",");
         if(roles != null && roles.length > 0) {

        	 if(!checkUserContainsRoles(userName, roles)) {
				 return Response.NEW().authorizationRequired();
        	 }
         }
         
         String description = controller.description();
         if(StringUtils.isNotBlank(description)) {
        	 // add log trace record
         }
    	
    	try {
			return pjp.proceed();
		} catch (Throwable e) {
			logger.error("fail to process in interceptor ", e);
		}
    	return null;
    	
    }
    
    private String checkLogin(HttpServletRequest request) {
    	Cookie [] cookies = request.getCookies();
    	if(cookies == null || cookies.length == 0)
    		return null;
    	for(Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			if("dmcstoken".equals(cookieName)) {
				String cookieValue = cookie.getValue();
				if(tockenCache.isTokenExist(cookieValue)) {
					return tockenCache.getUserNameByToken(cookieValue);
				} else {
					return null;
				}
			}
		}
    	
    	return null;
    }  
    
    private boolean checkUserContainsRoles(String userName, String [] roles) {
    	List<String> roleList = Arrays.asList(roles);
    	List<Role> rs = roleService.getRoleListByUserName(userName);
    	if(rs == null || rs.size() == 0)
    		return false;
    	for(Role role : rs) {
    		if(roleList.contains(role.getName()))
    			return true;
    	}
    	return false;
    }
    
}
