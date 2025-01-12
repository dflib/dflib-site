package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.print.TabularPrinter;
import org.dflib.window.Window;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.dflib.Exp.$int;


public class WindowTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "amount", "date").of(
            "Jerry Cosin", 8000, LocalDate.of(2024, 1, 15),
            "Juliana Walewski", 8500, LocalDate.of(2024, 1, 15),
            "Joan O'Hara", 9300, LocalDate.of(2024, 1, 15),
            "Jerry Cosin", 8800, LocalDate.of(2024, 2, 15),
            "Juliana Walewski", 8500, LocalDate.of(2024, 2, 15),
            "Joan O'Hara", 9500, LocalDate.of(2024, 2, 15));

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
                .cols("max_salary").merge($int("amount").max()); // <2>
// end::windowPartition[]

        print("windowPartition", df1);
    }


}
