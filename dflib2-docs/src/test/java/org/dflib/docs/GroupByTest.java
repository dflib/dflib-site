package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.GroupBy;
import org.dflib.Printers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.dflib.Exp.*;

public class GroupByTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "amount", "date").of(
            "Jerry Cosin", 8000, LocalDate.of(2024, 1, 15),
            "Juliana Walewski", 8500, LocalDate.of(2024, 1, 15),
            "Joan O'Hara", 9300, LocalDate.of(2024, 1, 15),
            "Jerry Cosin", 4000, LocalDate.of(2024, 2, 15),
            "Juliana Walewski", 8500, LocalDate.of(2024, 2, 15),
            "Joan O'Hara", 9300, LocalDate.of(2024, 2, 15),
            "Jerry Cosin", 8000, LocalDate.of(2024, 3, 15),
            "Joan O'Hara", 9300, LocalDate.of(2024, 3, 15));

    @Test
    public void groupBy() {
// tag::groupBy[]
        GroupBy groupBy = df.group("date"); // <1>
// end::groupBy[]
    }

    @Test
    public void groupBy_Agg() {
// tag::agg[]
        DataFrame agg = df
                .group("date")
                .agg(
                        $col("date"), // <1>
                        $double("amount").sum(), // <2>
                        count() // <3>
                );
// end::agg[]

        print("agg", agg);
    }

    @Test
    public void groupBy_Agg_Cols() {
// tag::agg_cols[]
        DataFrame agg = df
                .group("date")
                .cols("date", "total", "employees")
                .agg(
                        $col("date"),
                        $double("amount").sum(),
                        count()
                );
// end::agg_cols[]

        print("agg_cols", agg);
    }

    @Test
    public void rank() throws IOException {
// tag::rank[]
        DataFrame ranked = df.group("date")
                .sort($double("amount").desc()) // <1>
                .cols("date", "name", "rank")
                .select( // <2>
                        $col("date"),
                        $col("name"),
                        rowNum() // <3>
                );
// end::rank[]

        Printers.tabular(8, 100, 100).printTo(System.out, ranked);
    }

    @Test
    public void head() {
// tag::topSalary[]
        DataFrame topSalary = df.group("date")
                .sort($double("amount").desc()) // <1>
                .head(1) // <2>
                .select();
// end::topSalary[]

        print("head", topSalary);
    }
}
