package com.project.busticket.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.project.busticket.enums.DataEnum;

public class DynamicRoutingDatabase extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataEnum> CONTEXT = new ThreadLocal<>();

    public static void set(DataEnum dbType) {
        CONTEXT.set(dbType);
    }

    public static void clear() {
        CONTEXT.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return CONTEXT.get();
    }

}
