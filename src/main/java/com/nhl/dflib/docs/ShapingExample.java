package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.Printers;
import com.nhl.dflib.series.IntSequenceSeries;

public class ShapingExample {

    public static void main(String[] args) {
        addColumnsFromRow();
    }

    private static void addColumnFromRow() {
// tag::addColumnFromRow[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow(
                        "Mord", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        DataFrame df_added = df.addColumn(
                "full",                                   // <1>
                r -> r.get("first") + " " + r.get("last") // <2>
        );
// end::addColumnFromRow[]

        System.out.println(Printers.tabular.toString(df_added));
    }

    private static void addColumnsFromRow() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow(
                        "Mord", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addColumnsFromRow[]
        DataFrame df_added = df.addColumns(
                new String[]{"last_initial", "first_initial"},
                r -> r.get("last").toString().charAt(0),
                r -> r.get("first").toString().charAt(0)
        );
// end::addColumnsFromRow[]

        System.out.println(Printers.tabular.toString(df_added));
    }

    private static void addColumnFromSeries() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow(
                        "Mord", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addColumnFromSeries[]
        IntSeries rowNumbers = new IntSequenceSeries(0, df.height()); // <1>
        DataFrame df_added = df.addColumn(
                "number",
                rowNumbers
        );
// end::addColumnFromSeries[]

        System.out.println(Printers.tabular.toString(df_added));
    }

    private static void addRowNumbers() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow(
                        "Mord", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addRowNumbers[]
        DataFrame df_added = df.addRowNumber("number");
// end::addRowNumbers[]

        System.out.println(Printers.tabular.toString(df_added));
    }

}
