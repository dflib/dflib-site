package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.LongSeries;
import com.nhl.dflib.Series;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.nhl.dflib.Exp.*;

public class SortingExample extends BaseExample {

    @Test
    public void sortSeries_Sorter() {

// tag::sortSeries_Sorter[]
        // sort series by String length
        Series<String> s = Series
                .forData("12", "1", "123")
                .sort($str("").mapVal(String::length).asc());
// end::sortSeries_Sorter[]

        print("sortSeries", s);
    }

    @Test
    public void sortSeries() {

// tag::sortSeries[]
        Series<String> s = Series
                .forData("12", "1", "123")
                .sort(Comparator.comparingInt(String::length));
// end::sortSeries[]

        print("sortSeries", s);
    }

    @Test
    public void sortSeries_Natural() {

// tag::sortSeries_Natural[]
        Series<String> s = Series
                .forData("c", "d", "a")
                .sort($str("").asc());
// end::sortSeries_Natural[]

        print("sortSeries_Natural", s);
    }

    @Test
    public void sortSeries_Long() {

// tag::sortSeries_Long[]
        LongSeries s = LongSeries
                .forLongs(Long.MAX_VALUE, 15L, 0L)
                .sortLong();
// end::sortSeries_Long[]

        print("sortSeries_Long", s);
    }

    @Test
    public void sortDataFrame_Sorter() {

// tag::sortDataFrame_Sorter[]
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Jerry", "Albert", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort(
                $str("first").asc(),
                $str("last").asc());
// end::sortDataFrame_Sorter[]

        print("sortDataFrame_Sorter", df1);
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
    public void sortDataFrameComparableColumns() {

// tag::sortDataFrameComparableColumns[]
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Jerry", "Albert", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort(new String[]{"last", "first"}, new boolean[]{true, false});
// end::sortDataFrameComparableColumns[]

        print("sortDataFrameComparableColumns", df1);
    }
}
