package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Series;
import org.dflib.jdbc.Jdbc;
import org.dflib.jdbc.connector.JdbcConnector;
import io.bootique.jdbc.junit5.DbTester;
import io.bootique.jdbc.junit5.derby.DerbyTester;
import io.bootique.junit5.BQTest;
import io.bootique.junit5.BQTestTool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

@BQTest
public class JdbcTest extends BaseTest {

    @BQTestTool
    static final DbTester db = DerbyTester.db()
            .initDB("classpath:org/dflib/docs/init_schema.sql")
            .deleteBeforeEachTest("address", "person");

    private static JdbcConnector connector;

    @BeforeAll
    public static void initConnector() {

        DataSource dataSource = db.getDataSource();

        // tag::connectorDS[]
        JdbcConnector connector = Jdbc.connector(dataSource);
        // end::connectorDS[]

        JdbcTest.connector = connector;
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

        db.getTable("person").insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Juliana Walewski", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();

        // tag::tableLoader[]
        DataFrame df = connector.tableLoader("person").load();
        // end::tableLoader[]

        print("tableLoader", df);
    }

    @Test
    public void tableLoader_wOptions() {

        db.getTable("person").insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Juliana Walewski", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();

        // tag::tableLoader_wOptions[]
        DataFrame condition = DataFrame
                .byColumn("salary")
                .of(Series.ofInt(70_000, 101_000));

        DataFrame df = connector.tableLoader("person")
                .cols("name", "salary")
                .eq(condition)
                .load();
        // end::tableLoader_wOptions[]

        print("tableLoader_wOptions", df);
    }

    @Test
    public void tableSaver() {

        // tag::tableSaver[]
        DataFrame df = DataFrame.byArrayRow("id", "name", "salary")
                .appender()
                .append(1, "Jerry Cosin", 70_000)
                .append(2, "Juliana Walewski", 85_000)
                .append(3, "Joan O'Hara", 101_000)
                .toDataFrame();

        connector.tableSaver("person").save(df);
        // end::tableSaver[]
    }

    @Test
    public void tableSaver_Delete() {

        DataFrame df = DataFrame.byArrayRow("id", "name", "salary")
                .appender()
                .append(1, "Jerry Cosin", 70_000)
                .append(2, "Juliana Walewski", 85_000)
                .append(3, "Joan O'Hara", 101_000)
                .toDataFrame();

        // tag::tableSaver_Delete[]
        connector.tableSaver("person")
                .deleteTableData()
                .save(df);
        // end::tableSaver_Delete[]
    }

    @Test
    public void tableSaver_Merge() {

        DataFrame df_prior = DataFrame.byArrayRow("id", "name", "salary")
                .appender()
                .append(1L, "Jerry Cosin", 60_000)
                .append(3L, "Joan O'Hara", 95_000)
                .toDataFrame();

        connector.tableSaver("person").save(df_prior);

        DataFrame df = DataFrame.byArrayRow("id", "name", "salary")
                .appender()
                .append(1L, "Jerry Cosin", 70_000)
                .append(2L, "Juliana Walewski", 85_000)
                .append(3L, "Joan O'Hara", 101_000)
                .toDataFrame();

        // tag::tableSaver_Merge[]
        connector.tableSaver("person")
                .mergeByPk()
                .save(df);
        // end::tableSaver_Merge[]
    }

    @Test
    public void sqlLoader() {

        db.getTable("person").insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Juliana Walewski", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();
        db.getTable("address").insertColumns("id", "person_id", "city")
                .values(1, 1, "New York")
                .values(2, 2, "London")
                .values(3, 3, "Minsk")
                .values(4, 3, "Warsaw")
                .exec();

        // tag::sqlLoader[]
        DataFrame df = connector.sqlLoader("""
                select "name", "city"
                  from "person" p
                  left join "address" a on (p."id" = a."person_id")
                  where "salary" > 80000
                  order by "name", "city"
                  """).load();
        // end::sqlLoader[]

        print("tableLoader", df);
    }

    @Test
    public void sqlLoader_Params() {

        db.getTable("person").insertColumns("id", "name", "salary")
                .values(1, "Jerry Cosin", 70_000)
                .values(2, "Juliana Walewski", 85_000)
                .values(3, "Joan O'Hara", 101_000)
                .exec();
        db.getTable("address").insertColumns("id", "person_id", "city")
                .values(1, 1, "New York")
                .values(2, 2, "London")
                .values(3, 3, "Minsk")
                .values(4, 3, "Warsaw")
                .exec();

        // tag::sqlLoader_params[]
        DataFrame df = connector.sqlLoader("""
                select "name", "city"
                  from "person" p
                  left join "address" a on (p."id" = a."person_id")
                  where "salary" > ?
                  order by "name", "city"
                  """).load(80000);
        // end::sqlLoader_params[]

        print("tableLoader", df);
    }

    @Test
    public void sqlSaver() {

        // tag::sqlSaver[]
        DataFrame df = DataFrame.byArrayRow("id", "name", "salary")
                .appender()
                .append(1, "Jerry Cosin", 70_000)
                .append(2, "Juliana Walewski", 85_000)
                .append(3, "Joan O'Hara", 101_000)
                .toDataFrame();

        connector.sqlSaver("""
                insert into "person" values (?, ?, ?)
                """).save(df);
        // end::sqlSaver[]
    }
}
