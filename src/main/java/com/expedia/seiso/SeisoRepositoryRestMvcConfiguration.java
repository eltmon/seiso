//package com.expedia.seiso;
//
//import com.expedia.seiso.domain.converters.ServiceInstanceKeytoIDConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.convert.support.ConfigurableConversionService;
//import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
//import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
//import org.springframework.format.support.DefaultFormattingConversionService;
//
///**
// * Created by ebecker on 4/15/2016.
// */
//@Configuration
//@Import(RepositoryRestMvcConfiguration.class)
//public class SeisoRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {
//
////    @Override
////    public void configureConversionService(ConfigurableConversionService service) {
////        super.configureConversionService(service);
////        service.addConverter(new ServiceInstanceKeytoIDConverter());
////    }
//
//
//    @Override
//    public DefaultFormattingConversionService defaultConversionService() {
//        super.defaultConversionService().addConverter(new ServiceInstanceKeytoIDConverter());
//
//    }
//}
