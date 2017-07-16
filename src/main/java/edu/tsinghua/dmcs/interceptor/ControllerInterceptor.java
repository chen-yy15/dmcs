package edu.tsinghua.dmcs.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
    
    //@Autowired
    //RoleService roleService;

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
         Long userId = null;
         if(controller.loginRequired()) {
        	logger.info("login require = " + controller.loginRequired());
 	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    

        	 if(!checkLogin(request)) {
        		 // throw login required exception
        	 }
        	 userId = 0L;
         } 
         
         String roleRequireds = controller.roleAllowed();
         String [] roles = StringUtils.split(roleRequireds, ",");
         if(roles != null && roles.length > 0) {
        	 if(userId == null) {
        		 // throw can't get useId exception
        	 }
        	 if(!checkUserContainsRoles(userId, roles)) {
        		 // throw role required exception
        	 }
         }
         
         String description = controller.description();
         if(StringUtils.isNotBlank(description)) {
        	 // add log trace record
         }
    	
    	try {
			return pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    	
    }
    
    private boolean checkLogin(HttpServletRequest request) {
    	Cookie [] cookies = request.getCookies();
    	
    	return true;
    }  
    
    private boolean checkUserContainsRoles(Long userId, String [] roles) {
    	List<String> roleList = Arrays.asList(roles);
    	// Role [] r = roleService.getRoleListByUserId(userId);
//    	for(Role role : r) {
//    		if(roleList.contains(role.getName()))
//    			return true;
//    	}
    	return false;
    }
    
}
