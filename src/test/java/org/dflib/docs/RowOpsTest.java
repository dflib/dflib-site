package org.dflib.docs;

import org.dflib.BooleanSeries;
import org.dflib.DataFrame;
import org.dflib.Exp;
import org.dflib.IntSeries;
import org.dflib.RowMapper;
import org.dflib.Series;
import org.dflib.junit5.DataFrameAsserts;
import org.dflib.series.IntSequenceSeries;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

import static org.dflib.Exp.*;

public class RowOpsTest extends BaseTest {

    @Test
    public void rowsByCondition() {

// tag::rowsByCondition[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

        DataFrame df1 = df
                .rows($str("last").startsWith("W").eval(df)) // <1>
                .select(); // <2>
// end::rowsByCondition[]

        print("rowsByCondition", df1);
    }

    @Test
    public void rowsByConditionPrecalc() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

// tag::rowsByConditionPrecalc[]
        IntSeries salaries = Series.ofInt(100000, 50000, 45600); // <1>
        BooleanSeries selector = salaries.locateInt(s -> s > 49999); // <2>

        DataFrame df1 = df.rows(selector).select();
// end::rowsByConditionPrecalc[]

        print("rowsByConditionPrecalc", df1);
    }

    @Test
    public void rowsByPredicate() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

// tag::rowsByPredicate[]
        DataFrame df1 = df
                .rows(r -> r.get("last", String.class).startsWith("W"))
                .select();
// end::rowsByPredicate[]

        print("rowsByIndex", df1);
    }

    @Test
    public void rowsByArray() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

// tag::rowsByArray[]
        DataFrame df1 = df
                .rows(2, 0, 2) // <1>
                .select();
// end::rowsByArray[]

        print("rowsByArray", df1);
    }

    @Test
    public void rowsByIndex() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

        IntSeries salaries = Series.ofInt(100000, 50000, 45600);

// tag::rowsByIndex[]
        IntSeries selector = salaries.indexInt(s -> s > 49999); // <1>

        DataFrame df1 = df.rows(selector).select();
// end::rowsByIndex[]

        print("rowsByIndex", df1);
    }


    @Test
    public void rowsByRange() {

        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

// tag::rowsByRange[]
        DataFrame df1 = df
                .rowsRange(0, 2) // <1>
                .select();
// end::rowsByRange[]

        print("rowsByRange", df1);
    }

    @Test
    public void rowsSelectExp() {

// tag::rowsSelectExp[]
        DataFrame df = DataFrame.foldByRow("first", "last", "age", "retires_soon").of(
                "Jerry", "Cosin", 61, false,
                "Juliana", "Walewski", 25, false,
                "Joan", "O'Hara", 59, false);

        DataFrame df1 = df
                .rows($int("age").mapConditionVal(a -> 67 - a < 10))
                .select(
                        $col("first"),
                        $col("last"),
                        $col("age"),
                        $val(true)); // <1>
// end::rowsSelectExp[]

        print("rowsSelectExp", df1);
    }

    @Test
    public void rowsSelectRowMapper() {
        DataFrame df = DataFrame.foldByRow("first", "last", "age", "retires_soon").of(
                "Jerry", "Cosin", 61, false,
                "Juliana", "Walewski", 25, false,
                "Joan", "O'Hara", 59, false);

// tag::rowsSelectRowMapper[]
        RowMapper mapper = (from, to) -> {
            from.copy(to);
            to.set("retires_soon", true);
        };

        DataFrame df1 = df
                .rows($int("age").mapConditionVal(a -> 67 - a < 10))
                .select(mapper);
// end::rowsSelectRowMapper[]

        print("rowsSelectRowMapper", df1);
    }

    @Test
    public void rowsMergeExp() {

        DataFrame df = DataFrame.foldByRow("first", "last", "age", "retires_soon").of(
                "Jerry", "Cosin", 61, false,
                "Juliana", "Walewski", 25, false,
                "Joan", "O'Hara", 59, false);

        // tag::rowsMergeExp[]
        DataFrame df1 = df
                .rows($int("age").mapConditionVal(a -> 67 - a < 10))
                .map(
                        $col("first"),
                        $col("last"),
                        $col("age"),
                        $val(true));
// end::rowsMergeExp[]

        print("rowsMergeExp", df1);
    }

    @Test
    public void rowsDrop() {

// tag::rowsDrop[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "P");

        DataFrame df1 = df.rows($col("middle").isNull()).drop();
// end::rowsDrop[]

        print("colsDrop", df1);
    }
}
