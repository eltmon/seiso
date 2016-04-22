package com.expedia.seiso;

import com.expedia.seiso.domain.converters.ServiceInstanceKeytoIDConverter;
import com.expedia.seiso.domain.entity.ServiceInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.URI;

/**
 * Created by ebecker on 4/18/2016.
 */
@Configuration
public class SeisoWebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addFormatters(FormatterRegistry registry){
        System.out.println("GOT here");
        //System.exit(1);
        registry.addConverter(URI.class, ServiceInstance.class, new ServiceInstanceKeytoIDConverter());
        registry.addConverter(ServiceInstance.class, URI.class, new ServiceInstanceKeytoIDConverter());

    }
}
