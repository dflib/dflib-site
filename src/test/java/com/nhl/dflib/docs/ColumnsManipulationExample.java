package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.DoubleSeries;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.series.IntSequenceSeries;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnsManipulationExample extends BaseExample {

    @Test
    public void addColumnFromRow() {
// tag::addColumnFromRow[]
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
                "Jerry", "Cosin",
                "Amanda", "Gabrielly",
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
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
                "Jerry", "Cosin",
                "Amanda", "Gabrielly",
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
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
                "Jerry", "Cosin",
                "Amanda", "Gabrielly",
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
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
                "Jerry", "Cosin",
                "Amanda", "Gabrielly",
                "Joan", "O'Hara");

// tag::addRowNumbers[]
        DataFrame df1 = df.addRowNumber("number");
// end::addRowNumbers[]

        print("addRowNumbers", df1);
    }

    @Test
    public void deleteColumns() {

// tag::deleteColumns[]
        DataFrame df = DataFrame.newFrame("first", "last", "middle").foldByRow(
                "Jerry", "Cosin", "M",
                "Amanda", "Gabrielly", null,
                "Joan", "O'Hara", "J");

        DataFrame df1 = df.dropColumns("first", "middle"); // <1>
// end::deleteColumns[]

        print("deleteColumns", df1);
    }

    @Test
    public void renameColumn() {

// tag::renameColumn[]
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
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
        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
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

        DataFrame df = DataFrame.newFrame("first", "last").foldByRow(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(nameMap);
// end::renameColumns_Map[]

        print("renameColumns_Map", df1);
    }

    @Test
    public void renameColumns_ToLowerCase() {

// tag::renameColumns_ToLowerCase[]
        DataFrame df = DataFrame.newFrame("FIRST", "LAST").foldByRow(
                "Jerry", "Cosin",
                "Joan", "O'Hara");

        DataFrame df1 = df.renameColumns(String::toLowerCase);
// end::renameColumns_ToLowerCase[]

        print("renameColumns_ToLowerCase", df1);
    }

    @Test
    public void convertColumn() {

// tag::convertColumn[]
        DataFrame df = DataFrame.newFrame("year", "sales").foldByRow(
                "2017", "2005365.01",
                "2018", "4355098.75");

        DataFrame df1 = df
                .convertColumn(0, (String s) -> Integer.valueOf(s))
                .convertColumn(1, (String s) -> new BigDecimal(s));
// end::convertColumn[]

        print("convertColumn", df1);
    }


    @Test
    public void toPrimitiveColumn() {
        DataFrame df = DataFrame.newFrame("year", "sales").foldByRow(
                "2017", "2005365.01",
                "2018", "4355098.75");

// tag::toPrimitiveColumn[]
        DataFrame df1 = df
                .toIntColumn("year", 0) // <1>
                .toDoubleColumn("sales", 0.); // <1>
// end::toPrimitiveColumn[]

        assertTrue(df1.getColumnAsInt(0) instanceof IntSeries);
        assertTrue(df1.getColumnAsDouble(1) instanceof DoubleSeries);

        print("toPrimitiveColumn", df1);
    }


}
