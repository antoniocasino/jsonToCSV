package it.tony;

import it.tony.services.ExportServiceFacade;
import it.tony.services.ExportServiceFacadeImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by casinan on 05/02/2016.
 */
@SpringBootApplication
public class JsonToCSVApp {
    public static void main(String[] args) throws Exception{
        if(args==null || args.length>1){
            throw new RuntimeException("please provide CITY NAME");
        }
        ApplicationContext context = new AnnotationConfigApplicationContext(JsonToCSVApp.class);
        ExportServiceFacade facade = context.getBean(ExportServiceFacade.class);
        facade.invokeExportService(args[0]);
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
