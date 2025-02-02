package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.print.TabularPrinter;
import org.dflib.window.Window;
import org.dflib.window.WindowRange;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.dflib.Exp.*;


public class WindowTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "salary", "date").of(
            "Jerry Cosin", 88000, LocalDate.of(2024, 1, 15),
            "Juliana Walewski", 85000, LocalDate.of(2024, 1, 15),
            "Joan O'Hara", 93000, LocalDate.of(2024, 1, 15),
            "Jerry Cosin", 95000, LocalDate.of(2024, 12, 15),
            "Juliana Walewski", 85000, LocalDate.of(2025, 2, 15),
            "Joan O'Hara", 80000, LocalDate.of(2023, 1, 1),
            "Joan O'Hara", 78000, LocalDate.of(2022, 1, 1));

    static {
        System.out.println(new TabularPrinter(8, 100).toString(df));
    }

    @Test
    public void window() {
// tag::window[]
        Window window = df.over();
// end::window[]
    }

    @Test
    public void windowPartition() {
// tag::windowPartition[]
        DataFrame df1 = df
                .over()
                .partitioned("name") // <1>
                .cols("max_salary").merge($int("salary").max()); // <2>
// end::windowPartition[]

        System.out.println();
        System.out.println("[windowPartition]");
        System.out.println(new TabularPrinter(8, 100).toString(df1));
    }

    @Test
    public void windowPartitionSorting() {
// tag::windowPartitionSorting[]
        DataFrame df1 = df
                .over()
                .partitioned("name")
                .sorted($col("date").asc()) // <1>
                .cols("raises_to_date", "raise_amount").merge(
                        rowNum().sub(1), // <2>
                        $int("salary").sub($int("salary").shift(1)) // <3>
                );
// end::windowPartitionSorting[]

        System.out.println();
        System.out.println("[windowPartitionSorting]");
        System.out.println(new TabularPrinter(8, 100).toString(df1));
    }


    @Test
    public void windowPartitionRange() {
// tag::windowPartitionRange[]

        DataFrame df1 = df
                .over()
                .partitioned("name")
                .sorted($col("date").asc())
                .range(WindowRange.allPreceding)  // <1>
                .cols("history_to_date").merge($col("salary").vConcat(", ")); // <2>
// end::windowPartitionRange[]

        System.out.println();
        System.out.println("[windowPartitionRange]");
        System.out.println(new TabularPrinter(8, 100).toString(df1));
    }
}
