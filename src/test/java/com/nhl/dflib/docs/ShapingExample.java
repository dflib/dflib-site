package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.series.IntSequenceSeries;
import org.junit.Test;

public class ShapingExample extends BaseExample {

    @Test
    public void addColumnFromRow() {
// tag::addColumnFromRow[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        DataFrame df1 = df.addColumn(
                "full",                                   // <1>
                r -> r.get("first") + " " + r.get("last") // <2>
        );
// end::addColumnFromRow[]

        print("addColumnFromRow", df1);
    }

    @Test
    public void addColumnsFromRow() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addColumnsFromRow[]
        DataFrame df1 = df.addColumns(
                new String[]{"last_initial", "first_initial"},
                r -> r.get("last").toString().charAt(0),
                r -> r.get("first").toString().charAt(0)
        );
// end::addColumnsFromRow[]

        print("addColumnsFromRow", df1);
    }

    @Test
    public void addColumnFromSeries() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addColumnFromSeries[]
        IntSeries rowNumbers = new IntSequenceSeries(0, df.height()); // <1>
        DataFrame df1 = df.addColumn(
                "number",
                rowNumbers
        );
// end::addColumnFromSeries[]

        print("addColumnFromSeries", df1);
    }

    @Test
    public void addRowNumbers() {
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

// tag::addRowNumbers[]
        DataFrame df1 = df.addRowNumber("number");
// end::addRowNumbers[]

        print("addRowNumbers", df1);
    }

    @Test
    public void deleteColumns() {

// tag::deleteColumns[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Alanson", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

        DataFrame df1 = df.dropColumns("first", "middle"); // <1>
// end::deleteColumns[]

        print("deleteColumns", df1);
    }

    @Test
    public void selectAndReorderColumns() {

// tag::selectAndReorderColumns[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Alanson", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

        DataFrame df1 = df.selectColumns("last", "first");
// end::selectAndReorderColumns[]

        print("selectAndReorderColumns", df1);
    }

}
