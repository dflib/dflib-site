package com.nhl.dflib.docs;

import com.nhl.dflib.BooleanSeries;
import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.Series;
import com.nhl.dflib.series.IntSequenceSeries;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SelectingExample extends BaseExample {

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

        IntSeries rowNumbers = new IntSequenceSeries(0, df.height());

        IntSeries selectionIndex = rowNumbers.indexInt(i -> i % 3 == 0); // <1>

        DataFrame df1 = df.selectRows(selectionIndex);
        // end::selectRowsIndex[]

        print("selectRowsIndex", df1);
    }

    @Test
    public void filterByColumn() {

// tag::filterByColumn[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        DataFrame df1 = df.filter(
                "first",
                (String f) -> f != null && f.startsWith("J"));
// end::filterByColumn[]

        print("filterByColumn", df1);
    }

    @Test
    public void filterByRow() {

        // tag::filterByRow[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        DataFrame df1 = df.filter(r ->
                r.get("first").toString().startsWith("J")
                        && r.get("last").toString().startsWith("O"));
        // end::filterByRow[]

        print("filterByRow", df1);
    }

    @Test
    public void filterByBoolean() {

        // tag::filterByBoolean[]
        DataFrame df = DataFrame
                .newFrame("first", "last")
                .foldByRow("Jerry", "Cosin",
                        "Alanson", "Gabrielly",
                        "Joan", "O'Hara");

        Series<String> names = Series.forData("Sandra", "Anton", "Joan");
        BooleanSeries index = names.eq(df.getColumn("first")); // <1>

        DataFrame df1 = df.filter(index);
        // end::filterByBoolean[]

        print("filterByBoolean", df1);
    }
}
