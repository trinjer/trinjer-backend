package org.trinjer.controllers.dto.geda.addapters;

import java.util.Map;

public interface AdaptersFactory {

    Map<String, Object> getAllAdapters();

    void registerAdapters();

}
