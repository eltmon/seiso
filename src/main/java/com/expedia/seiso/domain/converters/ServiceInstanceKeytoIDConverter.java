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
 */package com.expedia.seiso.domain.converters;

import com.expedia.seiso.domain.entity.ServiceInstance;
import com.expedia.seiso.domain.repo.ServiceInstanceRepo;
import com.expedia.seiso.domain.repo.impl.ServiceInstanceRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author ebecker
 */
@Component
public class ServiceInstanceKeytoIDConverter implements Converter<URI, ServiceInstance> {
    @Autowired
    private ServiceInstanceRepo serviceInstanceRepo;

    @Override
    public ServiceInstance convert (URI si){
        System.out.println("YESSS");

        if (si.toString() == null){
            //TODO throw exception
        }

        return serviceInstanceRepo.findByKey(si.toString());

    }
}
