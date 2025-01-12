package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.print.Printer;
import org.dflib.print.TabularPrinter;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class PrintersTest extends BaseTest {

    @Test
    public void printDataFrame_Tabular() {

// tag::printDataFrame_Tabular[]
        DataFrame df = DataFrame
                .foldByColumn("col1", "col2", "col3")
                .ofStream(IntStream.range(0, 10000));

        String table = Printers.tabular.toString(df);
        System.out.println(table);
// end::printDataFrame_Tabular[]
    }

    @Test
    public void printDataFrame_Tabular_Custom() {
        DataFrame df = DataFrame
                .foldByColumn("col1", "col2", "col3")
                .ofStream(IntStream.range(0, 10000));

// tag::printDataFrame_Tabular_Custom[]
        Printer printer = new TabularPrinter(3, 3); // <1>
        String table = printer.toString(df);
        System.out.println(table);
// end::printDataFrame_Tabular_Custom[]
    }
}
