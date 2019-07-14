package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import org.junit.Test;

public class SortingExample extends BaseExample {

    @Test
    public void sortDataFrameComparableColumn() {

// tag::sortDataFrameComparableColumn[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Amanda", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort("first", true); // <1>
// end::sortDataFrameComparableColumn[]

        print("sortDataFrameComparableColumn", df1);
    }

    @Test
    public void sortDataFrameComparableColumn_ByPosition() {

        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Amanda", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

// tag::sortDataFrameComparableColumn_ByPosition[]
        DataFrame df1 = df.sort(0, true);
// end::sortDataFrameComparableColumn_ByPosition[]

        print("sortDataFrameComparableColumn_ByPosition", df1);
    }

    @Test
    public void sortDataFrameComparableColumns() {

// tag::sortDataFrameComparableColumns[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("John", "Cosin", "M",
                        "Amanda", "Gabrielly", null,
                        "Joan", "Cosin", "J");

        DataFrame df1 = df.sort(new String[]{"last", "first"}, new boolean[]{true, false});
// end::sortDataFrameComparableColumns[]

        print("sortDataFrameComparableColumns", df1);
    }

    @Test
    public void sortDataFrameRowValueMapper() {

// tag::sortDataFrameRowValueMapper[]
        DataFrame df = DataFrame
                .newFrame("first", "last", "middle")
                .foldByRow("Jerry", "Cosin", "M",
                        "Amanda", "Gabrielly", null,
                        "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort(r -> r.get(0).toString().length()); // <1>
// end::sortDataFrameRowValueMapper[]

        print("sortDataFrameRowValueMapper", df1);
    }
}
