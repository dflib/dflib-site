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
                .of(2, 25, 3, 59, 4, 40);

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
                .of(2, 25, 3, 59, 4, 40);

        // tag::joinNoDupeColumns[]
        DataFrame joined = left
                .join(right)
                .on("id")
                .colsExcept(c -> c.endsWith("_"))
                .select();

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
                .of(2, 25, 3, 59, 4, 40);

        // tag::joinAs[]

        DataFrame joined = left.as("L") // <1>
                .join(right.as("R")) // <2>
                .on("id")
                .select();

        // end::joinAs[]

        print("joinAs", joined);
    }


    @Test
    public void selectExp() {

        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 59, 4, 40);

        // tag::selectExp[]

        DataFrame joined = left.as("L")
                .join(right.as("R"))
                .on("id")
                .cols("name", "retires_soon")
                .select("name, int(`R.age`) > 57");
        // .select($col("name"), $int("R.age").gt(57));

        // end::selectExp[]

        print("selectExp", joined);
    }


    @Test
    public void leftJoin() {


        DataFrame left = DataFrame
                .foldByRow("id", "name")
                .of(1, "Jerry", 2, "Juliana", 3, "Joan");

        DataFrame right = DataFrame
                .foldByRow("id", "age")
                .of(2, 25, 3, 59, 4, 40);

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
                .of(2, 25, 3, 59, 4, 40);

        // tag::indicatorColumn[]
        DataFrame joined = left
                .fullJoin(right)
                .on("id")
                .indicatorColumn("join_type") // <1>
                .colsExcept(c -> c.endsWith("_"))
                .select();
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

    @Test
    public void positional() {
// tag::positional[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "b").of(
                5, 6,
                7, 8);

        DataFrame dfh = df1.innerJoin(df2).positional().select(); // <1>
// end::positional[]

        print("positional", dfh);
    }

    @Test
    public void positional_LeftMismatch() {

// tag::positional_LeftMismatch[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4,
                5, 6);

        DataFrame df2 = DataFrame.foldByRow("c", "d").of(
                7, 8,
                9, 10);

        DataFrame dfv = df1.leftJoin(df2).positional().select();
// end::positional_LeftMismatch[]

        print("positional_LeftMismatch", dfv);
    }
}
