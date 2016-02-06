package it.tony.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by casinan on 05/02/2016.
 */
@Service
public class ExportServiceFacadeImpl implements ExportServiceFacade{
    @Autowired
    private ApplicationContext context;
    private static final Logger logger =Logger.getLogger(ExportServiceFacadeImpl.class);
    public void invokeExportService(String cityName){
        ExportService exportService = context.getBean(ExportService.class);
        try {
            exportService.invoke(cityName);
        }catch(Exception e){
            logger.error(e);
        }
    }

}
