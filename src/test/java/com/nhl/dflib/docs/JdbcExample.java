package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.docs.util.DbBootstrap;
import com.nhl.dflib.jdbc.Jdbc;
import com.nhl.dflib.jdbc.connector.JdbcConnector;
import io.bootique.jdbc.test.Table;
import io.bootique.jdbc.test.TestDataManager;
import io.bootique.test.junit.BQTestFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcExample extends BaseExample {

    @ClassRule
    public static final BQTestFactory testFactory = new BQTestFactory();
    private static DataSource dataSource;
    private static Table personTable;

    @Rule
    public final TestDataManager dataManager = new TestDataManager(true, personTable);
    private JdbcConnector connector;

    @BeforeClass
    public static void initDerby() {
        DbBootstrap bootstrap = DbBootstrap.create(testFactory, "classpath:com/nhl/dflib/docs/init_schema.sql");
        dataSource = bootstrap.getDataSource();
        personTable = bootstrap.getPersonTable();
    }

    @Before
    public void initConnector() {

        // tag::connectorDS[]
        JdbcConnector connector = Jdbc.connector(dataSource);
        // end::connectorDS[]

        this.connector = connector;
    }

    @Test
    public void manualConnector() {
        // tag::connectorManual[]
        JdbcConnector connector = Jdbc
                .connector("jdbc:derby:target/derby/mydb;create=true")
                // .driver("com.foo.MyDriver") <1>
                // .userName("root")
                // .password("secret") <2>
                .build();
        // end::connectorManual[]
    }

    @Test
    public void tableLoader() {

        personTable.insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Amanda Gabrielly", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();

        // tag::tableLoader[]
        DataFrame df = connector.tableLoader("person").load();
        // end::tableLoader[]

        print("tableLoader", df);
    }

    @Test
    public void tableLoader_wOptions() {

        personTable.insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Amanda Gabrielly", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();

        // tag::tableLoader_wOptions[]
        DataFrame condition = DataFrame.newFrame("salary")
                .addRow(70_000)
                .addRow(101_000)
                .create();

        DataFrame df = connector.tableLoader("person")
                .includeColumns("name", "salary")
                .eq(condition)
                .load();
        // end::tableLoader_wOptions[]

        print("tableLoader_wOptions", df);
    }

    @Test
    public void tableSaver() {

        // tag::tableSaver[]
        DataFrame df = DataFrame.newFrame("id", "name", "salary")
                .addRow(1, "Jerry Cosin", 70_000)
                .addRow(2, "Amanda Gabrielly", 85_000)
                .addRow(3, "Joan O'Hara", 101_000)
                .create();

        connector.tableSaver("person").save(df);
        // end::tableSaver[]
    }

    @Test
    public void tableSaver_Delete() {

        DataFrame df = DataFrame.newFrame("id", "name", "salary")
                .addRow(1, "Jerry Cosin", 70_000)
                .addRow(2, "Amanda Gabrielly", 85_000)
                .addRow(3, "Joan O'Hara", 101_000)
                .create();

        // tag::tableSaver_Delete[]
        connector.tableSaver("person")
                .deleteTableData()
                .save(df);
        // end::tableSaver_Delete[]
    }

    @Test
    public void tableSaver_Merge() {

        DataFrame df_prior = DataFrame.newFrame("id", "name", "salary")
                .addRow(1L, "Jerry Cosin", 60_000)
                .addRow(3L, "Joan O'Hara", 95_000)
                .create();

        connector.tableSaver("person").save(df_prior);

        DataFrame df = DataFrame.newFrame("id", "name", "salary")
                .addRow(1L, "Jerry Cosin", 70_000)
                .addRow(2L, "Amanda Gabrielly", 85_000)
                .addRow(3L, "Joan O'Hara", 101_000)
                .create();

        // tag::tableSaver_Merge[]
        connector.tableSaver("person")
                .mergeByPk()
                .save(df);
        // end::tableSaver_Merge[]
    }

}
