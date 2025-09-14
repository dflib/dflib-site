package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.LongSeries;
import org.dflib.Series;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class SortingTest extends BaseTest {

    @Test
    public void sortSeries_Sorter() {

// tag::sortSeries_Sorter[]
        // sort series by String length
        Series<String> s = Series
                .of("12", "1", "123")
                .sort("len(str(0)) desc");
        // .sort($str("").mapVal(String::length).desc());
// end::sortSeries_Sorter[]

        print("sortSeries", s);
    }

    @Test
    public void sortSeries() {

// tag::sortSeries[]
        Series<String> s = Series
                .of("12", "1", "123")
                .sort(Comparator.comparingInt(String::length));
// end::sortSeries[]

        print("sortSeries", s);
    }

    @Test
    public void sortSeries_Natural() {

// tag::sortSeries_Natural[]
        Series<String> s = Series
                .of("c", "d", "a")
                .sort("col(0)");
// end::sortSeries_Natural[]

        print("sortSeries_Natural", s);
    }

    @Test
    public void sortSeries_Long() {

// tag::sortSeries_Long[]
        LongSeries s = Series
                .ofLong(Long.MAX_VALUE, 15L, 0L)
                .sortLong();
// end::sortSeries_Long[]

        print("sortSeries_Long", s);
    }

    @Test
    public void sortDataFrame_Sorter() {

// tag::sortDataFrame_Sorter[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Jerry", "Albert", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort("first, last");
        // df.sort($col("first").asc(), $col("last").asc());
// end::sortDataFrame_Sorter[]

        print("sortDataFrame_Sorter", df1);
    }

    @Test
    public void sortDataFrameComparableColumn() {

// tag::sortDataFrameComparableColumn[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort("first", true); // <1>
// end::sortDataFrameComparableColumn[]

        print("sortDataFrameComparableColumn", df1);
    }

    @Test
    public void sortDataFrameComparableColumns() {

// tag::sortDataFrameComparableColumns[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Jerry", "Albert", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.sort(
                new String[]{"last", "first"},
                new boolean[]{true, false});
// end::sortDataFrameComparableColumns[]

        print("sortDataFrameComparableColumns", df1);
    }
}
