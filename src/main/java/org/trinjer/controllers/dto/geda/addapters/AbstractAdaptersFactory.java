package org.trinjer.controllers.dto.geda.addapters;

import com.inspiresoftware.lib.dto.geda.adapter.Adapters;
import com.inspiresoftware.lib.dto.geda.adapter.repository.AdaptersRepository;

public abstract class AbstractAdaptersFactory implements AdaptersFactory {

    protected AdaptersRepository adapters;

    protected AbstractAdaptersFactory() {
         adapters = Adapters.adaptersRepository();
    }
}
