/* 
 * Copyright 2013-2016 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.seiso;

import com.expedia.seiso.domain.converters.ServiceInstanceKeytoIDConverter;
import com.expedia.seiso.domain.entity.ServiceInstance;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Willie Wheeler
 */
@Configuration
@Slf4j
public class SeisoIntegrationConfig {
	@Autowired private SeisoProperties seisoProperties;
	@Autowired private CachingConnectionFactory connectionFactory;
	@Autowired private ServiceInstanceKeytoIDConverter serviceInstanceKeytoIDConverter = new ServiceInstanceKeytoIDConverter();
	
	@Bean
	public RabbitAdmin rabbitAdmin() {
		log.trace("connectionFactory.host={}", connectionFactory.getHost());
		val admin = new RabbitAdmin(connectionFactory);
		admin.declareExchange(seisoNotificationsExchange());
		return admin;
	}

	@Bean
	public Exchange seisoNotificationsExchange() {
		return new TopicExchange(seisoProperties.getChangeNotificationExchange());
	}
	
	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		// TODO Consider using HAL mapping here, since clients may want to do a callback to Seiso. [WLW]
		val converter = new Jackson2JsonMessageConverter();
//		converter.setJsonObjectMapper(halMapper);
		return converter;
	}
	
	@Bean
	public AmqpTemplate amqpTemplate() {
		val template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}

//	@Bean//(name="conversionService")
//	public ConversionService getConversionService()
//	{
//		ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//
////		GenericConversionService cs = new GenericConversionService();
////		cs.addConverter(serviceInstanceKeytoIDConverter);
//		//ConversionServiceFactoryBean bean = new DefaultFormattingConversionService();
//
//		bean.setConverters( getConverters() );
//		bean.afterPropertiesSet();
//
//		ConversionService object = bean.getObject();
//		System.out.println(object);
//	//
	//	return object//;
	//}//

//	private Set<Converter> getConverters() //{//
//
//		Set<Converter> converters = new HashSet<Converter>()//;//
//
//		//converters.add( serviceInstanceKeytoIDConverter )//;
//		// add here more custom converters, either as spring bean references or directly instantiate//d//
//
//		return converters//;
//	}
//	@PostConstruct
//	@Bean
//	private void registerConverters() {
//		converterRegistry.addConverter(new ServiceInstanceKeytoIDConverter());
//	}
//public ConversionService getConversionService() {
//	ConversionServiceFactoryBean bean =
//	bean.  addConverter(String.class, ServiceInstanceKeytoIDConverter.class, stringToStateTypeConverter);
//	return bean;
//}
//@Override
//public void addFormatters(FormatterRegistry registry) {
//
//}
}
