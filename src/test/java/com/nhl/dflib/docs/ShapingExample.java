package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.series.IntSequenceSeries;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void selectAndReorderColumns_ByIndex() {

// tag::selectAndReorderColumns_ByIndex[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Alanson", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

        DataFrame df1 = df.selectColumns(1, 0);
// end::selectAndReorderColumns_ByIndex[]

        print("selectAndReorderColumns_ByIndex", df1);
    }

    @Test
    public void renameColumn() {

// tag::renameColumn[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Joan", "O'Hara");

        DataFrame df1 = df
                .renameColumn("first", "first_name")
                .renameColumn("last", "last_name");
// end::renameColumn[]

        print("renameColumn", df1);
    }

    @Test
    public void renameColumns() {

// tag::renameColumns[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns("first_name", "last_name");
// end::renameColumns[]

        print("renameColumns", df1);
    }

    @Test
    public void renameColumns_Map() {

// tag::renameColumns_Map[]
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("first", "first_name");

        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(nameMap);
// end::renameColumns_Map[]

        print("renameColumns_Map", df1);
    }

    @Test
    public void renameColumns_ToLowerCase() {

// tag::renameColumns_ToLowerCase[]
        DataFrame df = DataFrame
                .newFrame("FIRST", "LAST")
                .foldByRow("Jerry", "Cosin",
                        "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(String::toLowerCase);
// end::renameColumns_ToLowerCase[]

        print("renameColumns_ToLowerCase", df1);
    }

    @Test
    public void selectRows() {

// tag::selectRows[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        DataFrame df1 = df.selectRows(2, 0, 0);
// end::selectRows[]

        print("selectRows", df1);
    }

    @Test
    public void selectRowsIndex() {

// tag::selectRowsIndex[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        // a Series of the same size as the DataFrame height
        IntSeries rowNumbers = new IntSequenceSeries(0, df.height());

        // select every third row
        IntSeries selectionIndex = rowNumbers.indexInt(i -> i % 3 == 0);

        DataFrame df1 = df.selectRows(selectionIndex);
// end::selectRowsIndex[]

        print("selectRowsIndex", df1);
    }
}
