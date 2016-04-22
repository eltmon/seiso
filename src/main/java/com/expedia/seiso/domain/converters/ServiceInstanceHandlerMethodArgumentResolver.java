package com.expedia.seiso.domain.converters;

import com.expedia.seiso.domain.entity.ServiceInstance;
import com.expedia.seiso.domain.repo.ServiceInstanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by ebecker on 4/18/2016.
 */
public class ServiceInstanceHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private ServiceInstanceRepo serviceInstanceRepo;
    @Override
    public boolean supportsParameter(MethodParameter methodParamater){
        System.out.println("!!!! SUPPORT?");
        return methodParamater.getParameterType().equals(ServiceInstance.class);
    }
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        System.out.println("!!!RESOLVING ARGUMENT");
        String siKey = nativeWebRequest.getParameter("siKey");

        if (isNotSet(siKey)) {
           // Throw exception
        }


        return serviceInstanceRepo.findByKey(siKey);

    }

    private boolean isNotSet(String value) {
        return value == null;
    }
}


