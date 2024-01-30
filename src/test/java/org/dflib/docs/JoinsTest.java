package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.join.JoinPredicate;
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
                .join(right) // <1>
                .on("id") // <2>
                .select();

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
                .join(right)
                .on("id")
                .selectExcept(c -> c.endsWith("_"));

        // end::joinNoDupeColumns[]

        print("joinNoDupeColumns", joined);
    }

    @Test
    public void joinAs() {

        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 30, 4, 40);

        // tag::joinAs[]

        DataFrame joined = left.as("left") // <1>
                .join(right.as("right")) // <2>
                .on("id")
                .select();

        // end::joinAs[]

        print("joinAs", joined);
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
                .leftJoin(right)
                .on("id")
                .select();
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
                .fullJoin(right)
                .on("id")
                .indicatorColumn("join_type") // <1>
                .selectExcept(c -> c.endsWith("_"));
        // end::indicatorColumn[]

        print("indicatorColumn", joined);
    }

    @Test
    public void predicatedBy() {

        // tag::predicatedBy[]
        DataFrame df = DataFrame.foldByRow("name", "salary").of(
                "Jerry", 120000,
                "Juliana", 80000,
                "Joan", 95000);

        JoinPredicate p = (r1, r2) ->
                ((Integer) r1.get("salary")) > ((Integer) r2.get("salary"));

        DataFrame joined = df
                .leftJoin(df)
                .predicatedBy(p) // <1>
                .select()
                .sort($int("salary").desc(), $int("salary_").desc()) // <2>
                .cols("name", "name_").selectAs("makes_more", "makes_less");
        // end::predicatedBy[]

        print("predicatedBy", joined);
    }
}
