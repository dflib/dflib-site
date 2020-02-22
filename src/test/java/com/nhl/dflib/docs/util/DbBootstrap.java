package com.nhl.dflib.docs.util;

import io.bootique.BQRuntime;
import io.bootique.jdbc.DataSourceFactory;
import io.bootique.jdbc.JdbcModule;
import io.bootique.jdbc.test.Table;
import io.bootique.jdbc.test.runtime.DatabaseChannelFactory;
import io.bootique.test.junit.BQTestFactory;

import javax.sql.DataSource;

public class DbBootstrap {

    private BQRuntime runtime;

    public DbBootstrap(BQRuntime runtime) {
        this.runtime = runtime;
    }

    public static DbBootstrap create(BQTestFactory testFactory, String initFile) {
        BQRuntime runtime = testFactory.app("-c", "classpath:com/nhl/dflib/docs/jdbc.yml")
                .autoLoadModules()
                .module(b -> JdbcModule.extend(b).addDataSourceListener(new DbInitializer(initFile)))
                .createRuntime();

        return new DbBootstrap(runtime);
    }

    public DataSource getDataSource() {
        return runtime.getInstance(DataSourceFactory.class).forName("ds");
    }

    public Table getPersonTable() {
        return runtime.getInstance(DatabaseChannelFactory.class)
                .getChannel()
                .newTable("person")
                .columnNames("id", "name", "salary")
                .initColumnTypesFromDBMetadata()
                .build();
    }
}
