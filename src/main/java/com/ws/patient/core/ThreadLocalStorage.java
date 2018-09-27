// Copyright (c) Philipp Wagner. All rights reserved.

// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.ws.patient.core;

public class ThreadLocalStorage {

    private static ThreadLocal<String> tenant = new ThreadLocal<>();

    public static void setTenantName(String tenantName) {
    	System.out.println(tenantName);
    	
        tenant.set(tenantName);
    }

    public static String getTenantName() {
        return tenant.get();
    }

}

