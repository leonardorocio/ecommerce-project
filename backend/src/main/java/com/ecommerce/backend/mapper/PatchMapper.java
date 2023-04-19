package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

@Component
public class PatchMapper {

    public void mapToPatchRequest(Object oldObjectInfo, Object newObjectInfo) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(oldObjectInfo.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                Object requestValue = propertyDescriptor.getReadMethod().invoke(newObjectInfo);
                if (requestValue != null && propertyDescriptor.getWriteMethod() != null) {
                    propertyDescriptor.getWriteMethod().invoke(oldObjectInfo, requestValue);
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException exception) {
            throw new BadRequestException("Request failed: Please try again");
        }
    }
}
