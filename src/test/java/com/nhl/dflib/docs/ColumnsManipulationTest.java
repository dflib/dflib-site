package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.Exp;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.junit5.DataFrameAsserts;
import com.nhl.dflib.series.IntSequenceSeries;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.nhl.dflib.Exp.*;

public class ColumnsManipulationTest extends BaseTest {

    @Test
    public void addColumn() {
// tag::addColumn[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

        DataFrame df1 = df.addColumn(
                concat($str("first"), $val(" "), $str("last")) // <1>
                        .as("full")  // <2>
        );
// end::addColumn[]

        print("addColumnFromRow", df1);
    }

    @Test
    public void addColumns() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

// tag::addColumns[]
        DataFrame df1 = df.addColumns(
                $str("last").mapVal(s -> s.charAt(0)).as("last_initial"),
                $str("first").mapVal(s -> s.charAt(0)).as("first_initial")
        );
// end::addColumns[]

        print("addColumnsFromRow", df1);
    }

    @Test
    public void addColumnFromRow() {
// tag::addColumnFromRow[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

        DataFrame df1 = df.addColumn(
                "full",                                   // <1>
                r -> r.get("first") + " " + r.get("last") // <2>
        );
// end::addColumnFromRow[]

        print("addColumnFromRow", df1);
    }

    @Test
    public void addColumnFromSeries() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

// tag::addColumnFromSeries[]
        IntSeries rowNumbers = new IntSequenceSeries(1, df.height() + 1); // <1>
        DataFrame df1 = df.addColumn(
                "number",
                rowNumbers
        );
// end::addColumnFromSeries[]

        print("addColumnFromSeries", df1);
    }

    @Test
    public void addRowNumbers() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Juliana", "Walewski",
                "Joan", "O'Hara");

// tag::addRowNumbers[]
        DataFrame df1 = df.addRowNumberColumn("number");
// end::addRowNumbers[]

        print("addRowNumbers", df1);
    }

    @Test
    public void deleteColumns() {

// tag::deleteColumns[]
        DataFrame df = DataFrame.foldByRow("first", "last", "middle").of(
                "Jerry", "Cosin", "M",
                "Juliana", "Walewski", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.dropColumns("first", "middle"); // <1>
// end::deleteColumns[]

        print("deleteColumns", df1);
    }

    @Test
    public void renameColumn() {

// tag::renameColumn[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
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
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
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

        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(nameMap);
// end::renameColumns_Map[]

        print("renameColumns_Map", df1);
    }

    @Test
    public void renameColumns_ToLowerCase() {

// tag::renameColumns_ToLowerCase[]
        DataFrame df = DataFrame.foldByRow("FIRST", "LAST").of(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(String::toLowerCase);
// end::renameColumns_ToLowerCase[]

        print("renameColumns_ToLowerCase", df1);
    }

    @Test
    public void convertColumn() {

// tag::replaceColumn[]
        DataFrame df = DataFrame.foldByRow("year", "sales").of(
                "2022", "2005365.01",
                "2023", "4355098.75");

        DataFrame df1 = df
                .replaceColumn(0, Exp.$str(0).castAsInt())
                .replaceColumn(1, Exp.$str(1).castAsDecimal());
// end::replaceColumn[]

        new DataFrameAsserts(df1, "year", "sales")
                .expectRow(0, 2022, new BigDecimal("2005365.01"))
                .expectRow(1, 2023, new BigDecimal("4355098.75"));

        print("convertColumn", df1);
    }


    @Test
    public void compact() {
        DataFrame df = DataFrame.foldByRow("year", "sales").of(
                "2022", "2005365.01",
                "2023", "4355098.75");

// tag::compact[]
        DataFrame df1 = df
                .compactInt("year", 0) // <1>
                .compactDouble("sales", 0.);
// end::compact[]

        new DataFrameAsserts(df1, "year", "sales")
                .expectIntColumns(0)
                .expectDoubleColumns(1)
                .expectRow(0, 2022, 2005365.01)
                .expectRow(1, 2023, 4355098.75);

        print("toPrimitiveColumn", df1);
    }
}
