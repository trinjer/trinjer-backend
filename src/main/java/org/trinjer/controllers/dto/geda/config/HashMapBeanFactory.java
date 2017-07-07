package org.trinjer.controllers.dto.geda.config;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;

@Service
public class HashMapBeanFactory implements BeanFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Class<?>> beans;

    @PostConstruct
    public void initializeBeans() {
        beans = new HashMap<>();
    }

    @Override
    public Class<?> getClazz(String entityBeanKey) {
        return beans.get(entityBeanKey);
    }

    @Override
    public Object get(String entityBeanKey) {
        try {
            return beans.get(entityBeanKey).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }
}
