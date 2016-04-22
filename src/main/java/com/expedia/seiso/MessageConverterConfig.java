package com.expedia.seiso;

import com.expedia.seiso.domain.converters.ServiceInstanceHandlerMethodArgumentResolver;
import com.expedia.seiso.domain.converters.ServiceInstanceKeytoIDConverter;
import com.expedia.seiso.domain.entity.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MessageConverterConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {
    @Autowired private ServiceInstanceKeytoIDConverter serviceInstanceKeytoIDConverter;
    @Autowired
    private FormattingConversionService mvcConversionService;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // It appears some other dependency is adding Jax2bRootElementHttpMessageConverter, this
        // will override that behavior.
        List<HttpMessageConverter<?>> baseConverters = new ArrayList<>();
        super.configureMessageConverters(baseConverters);

        for(HttpMessageConverter<?> c : baseConverters){
            if(!(c instanceof Jaxb2RootElementHttpMessageConverter)){
                converters.add(c);
            }
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(URI.class, ServiceInstance.class);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!1");
       // System.exit(1);
        registry.addConverter(new ServiceInstanceKeytoIDConverter());
        //super.addFormatters(registry);
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("application/json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ServiceInstanceHandlerMethodArgumentResolver());
    }

    private RequestMappingHandlerAdapter adapter;

    @Bean
    public DomainClassConverter<?> domainClassConverter() {
        return new DomainClassConverter<FormattingConversionService>(mvcConversionService);
    }

//    @PostConstruct
//public void prioritizeCustomArgumentMethodHandlers () {
//    List<HandlerMethodArgumentResolver> argumentResolvers =
//            new ArrayList<> (adapter.getArgumentResolvers ());
//    List<HandlerMethodArgumentResolver> customResolvers =
//            adapter.getCustomArgumentResolvers ();
//    argumentResolvers.removeAll (customResolvers);
//    argumentResolvers.addAll (0, customResolvers);
//    adapter.setArgumentResolvers (argumentResolvers);
//}
}