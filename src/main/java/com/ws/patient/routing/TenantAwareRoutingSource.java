// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.ws.patient.routing;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ws.patient.core.ThreadLocalStorage;

public class TenantAwareRoutingSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
    	System.out.println(ThreadLocalStorage.getTenantName());
        return ThreadLocalStorage.getTenantName();
    	
    	
    }

}
