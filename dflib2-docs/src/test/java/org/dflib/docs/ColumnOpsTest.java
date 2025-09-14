package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Exp;
import org.dflib.RowMapper;
import org.dflib.Series;
import org.dflib.junit5.DataFrameAsserts;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.dflib.Exp.*;

public class ColumnOpsTest extends BaseTest {

    static DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
            "Jerry", "Cosin", "M",
            "Joan", "O'Hara", null);

    @Test
    public void cols() {
// tag::cols[]
        DataFrame df1 = df
                .cols("last", "first") // <1>
                .select(); // <2>
// end::cols[]

        print("cols", df1);
    }


    @Test
    public void colsByPos() {

// tag::colsByPos[]
        DataFrame df1 = df.cols(1, 0).select();
// end::colsByPos[]

        print("colsByPos", df1);
    }

    @Test
    public void colsByPredicate() {
        print("colsByPredicate-src", df);

// tag::colsByPredicate[]
        DataFrame df1 = df.cols(c -> !"middle".equals(c)).select();
// end::colsByPredicate[]

        print("colsByPredicate", df1);
    }

    @Test
    public void colsExcept() {

// tag::colsExcept[]
        DataFrame df1 = df.colsExcept("middle").select();
// end::colsExcept[]

        print("colsExcept", df1);
    }

    @Test
    public void colsAll() {

// tag::colsAll[]
        DataFrame df1 = df.cols().select();
// end::colsAll[]

        print("colsAll", df1);
    }

    @Test
    public void colsSelectAs() {


// tag::colsSelectAs[]
        DataFrame df1 = df
                .cols("last", "first")
                .selectAs("last_name", "first_name"); // <1>
// end::colsSelectAs[]

        print("colsSelectAs", df1);
    }

    @Test
    public void colsSelectAsMap() {

// tag::colsSelectAsMap[]
        DataFrame df1 = df
                .cols("last", "first")
                .selectAs(Map.of("last", "LAST_NAME"));
// end::colsSelectAsMap[]

        print("colsSelectAsMap", df1);
    }

    @Test
    public void colsSelectAsOp() {

// tag::colsSelectAsOp[]
        DataFrame df1 = df
                .cols("last", "first")
                .selectAs(String::toUpperCase);
// end::colsSelectAsOp[]

        print("colsSelectAsOp", df1);
    }

    @Test
    public void colsSelectExp() {

// tag::colsSelectExp[]
        DataFrame df1 = df
                .cols("first_middle", "last") // <1>
                .select("concat(first, ifNull(concat(' ', middle), '')), last")
                ; // <2>
// end::colsSelectExp[]

        print("colsSelectAsOp", df1);
    }

    @Test
    public void colsSelectRowMapper() {

// tag::colsSelectRowMapper[]
        RowMapper mapper = (from, to) -> {
            String middle = from.get("middle") != null
                    ? " " + from.get("middle")
                    : "";
            to.set(0, from.get("first") + middle).set(1, from.get("last"));
        };

        DataFrame df1 = df
                .cols("first_middle", "last")
                .select(mapper);
// end::colsSelectRowMapper[]

        print("colsSelectRowMapper", df1);
    }

    @Test
    public void colsSelectExpand() {
// tag::colsSelectExpand[]
        DataFrame df = DataFrame.foldByRow("name", "phones").of(
                "Cosin", List.of("111-555-5555", "111-666-6666", "111-777-7777"),
                "O'Hara", List.of("222-555-5555"));

        DataFrame df1 = df
                .cols("primary_phone", "secondary_phone")
                .selectExpand($col("phones")); // <1>
// end::colsSelectExpand[]

        print("colsSelectExpand", df1);
    }

    @Test
    public void colsSelectExpandUnlim() {

        DataFrame df = DataFrame.foldByRow("name", "phones").of(
                "Cosin", List.of("111-555-5555", "111-666-6666", "111-777-7777"),
                "O'Hara", List.of("222-555-5555"));

// tag::colsSelectExpandUnlim[]
        DataFrame df1 = df
                .cols() // <1>
                .selectExpand($col("phones"));
// end::colsSelectExpandUnlim[]

        print("colsSelectExpandUnlim", df1);
    }

    @Test
    public void colsSelectExpandArrays() {
// tag::colsSelectExpandArrays[]
        DataFrame df = DataFrame.foldByRow("name", "phones").of(
                "Cosin", "111-555-5555,111-666-6666,111-777-7777",
                "O'Hara", "222-555-5555");

        DataFrame df1 = df
                .cols("primary_phone", "secondary_phone")
                .selectExpandArray($str("phones").split(','));
// end::colsSelectExpandArrays[]

        print("colsSelectExpandArrays", df1);
    }

    @Test
    public void colsMap() {
// tag::colsMap[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "jerry", "cosin", "M",
                "Joan", "O'Hara", null);

        Function<String, Exp<String>> cleanup = col -> $str(col).mapVal(
                s -> s != null && !s.isEmpty()
                        ? Character.toUpperCase(s.charAt(0)) + s.substring(1)
                        : null); // <1>

        DataFrame df1 = df
                .cols("last", "first", "full") // <2>
                .merge( // <3>
                        cleanup.apply("last"),
                        cleanup.apply("first"),
                        concat($str("first"), $val(" "), $str("last"))
                );
// end::colsMap[]

        print("colsMap", df1);
    }

    @Test
    public void colsAppendMap() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "jerry", "cosin", "M",
                "Joan", "O'Hara", null);

        Function<String, Exp<String>> cleanup = col -> $str(col).mapVal(
                s -> !s.isEmpty()
                        ? Character.toUpperCase(s.charAt(0)) + s.substring(1)
                        : null);

// tag::colsAppendMap[]
        DataFrame df1 = df
                .colsAppend("last", "first") // <1>
                .merge(
                        cleanup.apply("last"),
                        cleanup.apply("first")
                );
// end::colsAppendMap[]

        print("colsAppendMap", df1);
    }

    @Test
    public void colsDrop() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Joan", "O'Hara", null);

// tag::colsDrop[]
        DataFrame df1 = df.cols("middle").drop();
// end::colsDrop[]

        print("colsDrop", df1);
    }

    @Test
    public void colsExceptSelect() {
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Joan", "O'Hara", null);

// tag::colsExceptSelect[]
        DataFrame df1 = df.colsExcept("middle").select();
// end::colsExceptSelect[]

        print("colsSelectToDrop", df1);
    }

    @Test
    public void fillNulls() {

// tag::fillNulls[]
        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                "a1", "a2",
                null, null,
                "b1", "b2");

        DataFrame clean = df.cols("c1", "c2").fillNulls("X");
// end::fillNulls[]

        print("fillNulls", clean);
    }

    @Test
    public void fillNullsForwardBackwards() {

        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                "a1", "a2",
                null, null,
                "b1", "b2");

// tag::fillNullsForwardBackwards[]
        DataFrame clean = df
                .cols("c1").fillNullsBackwards() // <1>
                .cols("c2").fillNullsForward(); // <2>
// end::fillNullsForwardBackwards[]

        print("fillNullsForwardBackwards", clean);
    }


    @Test
    public void fillNullsFromSeries() {

        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                "a1", "a2",
                null, null,
                "b1", "b2");

// tag::fillNullsFromSeries[]

        Series<String> mask = Series.of("A", "B", "C", "D");
        DataFrame clean = df.cols("c1", "c2").fillNullsFromSeries(mask);

// end::fillNullsFromSeries[]

        print("fillNullsFromSeries", clean);
    }

    @Test
    public void fillNullsWithExp() {
// tag::fillNullsWithExp[]
        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                "a1", "a2",
                null, null,
                "b1", "b2");

        DataFrame clean = df.cols("c1", "c2")
                .fillNullsWithExp(rowNum()); // <1>
// end::fillNullsWithExp[]

        print("fillNullsWithExp", clean);
    }

    @Test
    public void compact() {

// tag::compact[]
        DataFrame df = DataFrame.foldByRow("year", "sales").of(
                "2022", "2005365.01",
                "2023", "4355098.75");

        DataFrame df1 = df
                .cols("year").compactInt(0).merge() // <1>
                .cols("sales").compactDouble(0.).merge();
// end::compact[]

        new DataFrameAsserts(df1, "year", "sales")
                .expectIntColumns(0)
                .expectDoubleColumns(1)
                .expectRow(0, 2022, 2005365.01)
                .expectRow(1, 2023, 4355098.75);

        print("compact", df1);
    }

    @Test
    public void understandings0() {
// tag::understandings0[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

        DataFrame df1 = df
                .cols("last", "first")
                .select();
// end::understandings0[]

        print("understandings0", df1);
    }

    @Test
    public void understandings1() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

// tag::understandings1[]
        DataFrame df1 = df
                .cols("last", "first", "middle")
                .select();
// end::understandings1[]

        print("understandings1", df1);
    }

    @Test
    public void understandings2() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

// tag::understandings2[]
        DataFrame df1 = df
                .cols("last", "first", "middle")
                .selectAs("L", "F", "M");
// end::understandings2[]

        print("understandings2", df1);
    }
}
