package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.junit5.DataFrameAsserts;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$str;

public class DataFrameOpsTest extends BaseTest {

    @Test
    public void compact() {

// tag::compact[]
        DataFrame df = DataFrame.foldByRow("year", "sales").of(
                "2022", "2005365.01",
                "2023", "4355098.75");
        DataFrame df1 = df
                .compactInt("year", 0) // <1>
                .compactDouble("sales", 0.);
// end::compact[]

        new DataFrameAsserts(df1, "year", "sales")
                .expectIntColumns(0)
                .expectDoubleColumns(1)
                .expectRow(0, 2022, 2005365.01)
                .expectRow(1, 2023, 4355098.75);

        print("compact", df1);
    }

    @Test
    public void headDataFrame() {

        // tag::headDataFrame[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

        DataFrame df1 = df.head(2); // <1>
        // end::headDataFrame[]

        print("headDataFrame", df1);
    }

    @Test
    public void tailDataFrame() {


        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

        // tag::tailDataFrame[]
        DataFrame df1 = df.tail(1);
        // end::tailDataFrame[]

        print("tailDataFrame", df1);
    }

    @Test
    public void negativeHeadDataFrame() {

        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

        // tag::negativeHeadDataFrame[]
        DataFrame df1 = df.head(-2);
        // end::negativeHeadDataFrame[]

        print("headDataFrame", df1);
    }
}
