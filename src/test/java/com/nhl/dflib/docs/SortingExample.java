package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.LongSeries;
import com.nhl.dflib.Series;
import org.junit.Test;

import java.util.Comparator;

public class SortingExample extends BaseExample {

    @Test
    public void sortSeries() {

// tag::sortSeries[]
        Series<String> s = Series.forData("12", "1", "123")
                .sort(Comparator.comparingInt(String::length));
// end::sortSeries[]

        print("sortSeries", s);
    }

    @Test
    public void sortSeries_Natural() {

// tag::sortSeries_Natural[]
        Series<String> s = Series.forData("c", "d", "a")
                .sort(Comparator.naturalOrder());
// end::sortSeries_Natural[]

        print("sortSeries_Natural", s);
    }

    @Test
    public void sortSeries_Long() {

// tag::sortSeries_Long[]
        LongSeries s = LongSeries.forLongs(Long.MAX_VALUE, 15L, 0L)
                .sortLong();
// end::sortSeries_Long[]

        print("sortSeries_Long", s);
    }

    @Test
    public void sortDataFrameComparableColumn() {

// tag::sortDataFrameComparableColumn[]
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort("first", true); // <1>
// end::sortDataFrameComparableColumn[]

        print("sortDataFrameComparableColumn", df1);
    }

    @Test
    public void sortDataFrameComparableColumn_ByPosition() {

        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
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
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "John", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Joan", "Cosin", "J");

        DataFrame df1 = df.sort(new String[]{"last", "first"}, new boolean[]{true, false});
// end::sortDataFrameComparableColumns[]

        print("sortDataFrameComparableColumns", df1);
    }

    @Test
    public void sortDataFrameRowValueMapper() {

// tag::sortDataFrameRowValueMapper[]
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort(r -> r.get(0).toString().length()); // <1>
// end::sortDataFrameRowValueMapper[]

        print("sortDataFrameRowValueMapper", df1);
    }
}
