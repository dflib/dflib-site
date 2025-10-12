package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.window.Window;
import org.dflib.window.WindowRange;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;


public class WindowTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "salary", "date").of(
            "Jerry Cosin", 88000, LocalDate.of(2024, 1, 15),
            "Juliana Walewski", 85000, LocalDate.of(2024, 1, 15),
            "Joan O'Hara", 93000, LocalDate.of(2024, 1, 15),
            "Jerry Cosin", 95000, LocalDate.of(2024, 12, 15),
            "Juliana Walewski", 85000, LocalDate.of(2025, 2, 15),
            "Joan O'Hara", 80000, LocalDate.of(2023, 1, 1),
            "Joan O'Hara", 78000, LocalDate.of(2022, 1, 1));

    @Test
    public void window() {
// tag::window[]
        Window window = df.over();
// end::window[]
    }

    @Test
    public void windowPartition() throws IOException {
// tag::windowPartition[]
        DataFrame df1 = df
                .over()
                .partition("name") // <1>
                .merge("max(int(salary)) as max_salary"); // <2>
// end::windowPartition[]

        System.out.println();
        System.out.println("[windowPartition]");
        Printers.tabular(8, 100, 100).printTo(System.out, df1);
    }

    @Test
    public void windowPartitionSorting() throws IOException {
// tag::windowPartitionSorting[]
        DataFrame df1 = df
                .over()
                .partition("name")
                .sort("date") // <1>
                .merge("""
                        rowNum() - 1 as raises_to_date,
                        int(salary) - castAsInt(shift(salary, 1)) as raise_amount
                        """ // <2>
                );
// end::windowPartitionSorting[]

        System.out.println();
        System.out.println("[windowPartitionSorting]");
        Printers.tabular(8, 100, 100).printTo(System.out, df1);
    }


    @Test
    public void windowPartitionRange() throws IOException {
// tag::windowPartitionRange[]

        DataFrame df1 = df
                .over()
                .partition("name")
                .sort("date")
                .range(WindowRange.allPreceding)  // <1>
                .merge("vConcat(salary, ', ') as history_to_date"); // <2>
// end::windowPartitionRange[]

        System.out.println();
        System.out.println("[windowPartitionRange]");
        Printers.tabular(8, 100, 100).printTo(System.out, df1);
    }
}
