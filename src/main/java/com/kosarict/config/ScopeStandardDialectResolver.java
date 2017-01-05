package com.kosarict.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

/**
 * Created by Sadegh-Pc on 11/29/2016.
 */
public class ScopeStandardDialectResolver implements DialectResolver {


    private static final long serialVersionUID = 1L;

    @Override
    public Dialect resolveDialect(DialectResolutionInfo info) {
        Dialect customDialectResolver = customDialectResolver(info);
        //Log.getInstance().logInfo(Thread.currentThread().getStackTrace(), customDialectResolver.getClass().getName());
        return customDialectResolver;
    }

    private Dialect customDialectResolver(DialectResolutionInfo info) {
        final String databaseName = info.getDatabaseName();
        final int majorVersion = info.getDatabaseMajorVersion();
        if (isSqlServer2014(databaseName, majorVersion)) {
            return new SQLServer2012Dialect();
        } else {
            return StandardDialectResolver.INSTANCE.resolveDialect(info);
        }
    }

    private boolean isSqlServer2014(final String databaseName, final int majorVersion) {
        return databaseName.startsWith("Microsoft SQL Server") && majorVersion == 12;
    }

}
