package org.trinjer.controllers.dto.geda.addapters;

import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author Andrey Korchan
 * @since 20-Feb-17 14:08
 */
@Component
public class AllAdaptersFactory extends AbstractAdaptersFactory {

    @Override
    public Map<String, Object> getAllAdapters() {
        return adapters.getAll();
    }

    @Override
    @PostConstruct
    public void registerAdapters() {
    }
}
