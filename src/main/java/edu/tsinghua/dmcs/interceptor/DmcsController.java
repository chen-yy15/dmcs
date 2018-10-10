package edu.tsinghua.dmcs.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface DmcsController {    
    
    public String description()  default "";
    
    public String roleAllowed() default "";

    public boolean authRequired() default false;
    
    public boolean loginRequired() default true;
    
    
} 