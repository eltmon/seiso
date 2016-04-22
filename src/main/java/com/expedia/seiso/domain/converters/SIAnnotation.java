package com.expedia.seiso.domain.converters;

import java.lang.annotation.*;

/**
 * Created by ebecker on 4/18/2016.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SIAnnotation {
}

