package software.judger.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil  implements ApplicationContextAware {


    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
   public static  <T> T getBean(String name, Class<T> clazz) throws BeansException{
        return applicationContext.getBean(name,clazz);
   }



}
