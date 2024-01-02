package org.dflib.docs;

import org.dflib.DataFrame;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$int;

public class JoinsTest extends BaseTest {

    @Test
    public void join() {

        // tag::join[]
        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 30, 4, 40);

        DataFrame joined = left
                .innerJoin() // <1>
                .on("id") // <2>
                .with(right);

        // end::join[]

        print("join", joined);
    }

    @Test
    public void join_RemoveDupes() {

        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 30, 4, 40);

        // tag::joinNoDupeColumns[]
        DataFrame joined = left
                .innerJoin()
                .on("id")
                .with(right)
                .dropColumns(c -> c.endsWith("_"));

        // end::joinNoDupeColumns[]

        print("joinNoDupeColumns", joined);
    }

    @Test
    public void leftJoin() {


        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 30, 4, 40);

        // tag::leftJoin[]
        DataFrame joined = left
                .leftJoin()
                .on("id")
                .with(right);

        // end::leftJoin[]

        print("leftJoin", joined);
    }

    @Test
    public void indicatorColumn() {

        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 30, 4, 40);

        // tag::indicatorColumn[]
        DataFrame joined = left
                .fullJoin()
                .on("id")
                .indicatorColumn("join_type") // <1>
                .with(right)
                .dropColumns(c -> c.endsWith("_"));

        // end::indicatorColumn[]

        print("indicatorColumn", joined);
    }

    @Test
    public void predicatedBy() {

        // tag::predicatedBy[]
        DataFrame df = DataFrame.foldByRow("name", "salary").of(
                "Jerry", 120_000,
                "Juliana", 80_000,
                "Joan", 95_000);

        DataFrame joined = df
                .leftJoin()
                .predicatedBy((r1, r2) -> ((Integer) r1.get("salary")) > ((Integer) r2.get("salary"))) // <1>
                .with(df)
                .sort($int("salary").desc(), $int("salary_").desc()) // <2>
                .renameColumn("name", "makes_more")
                .renameColumn("name_", "makes_less")
                .dropColumns(c -> c.startsWith("salary"));

        // end::predicatedBy[]

        print("predicatedBy", joined);
    }
}
