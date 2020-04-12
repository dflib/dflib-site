package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.csv.Csv;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvExample extends BaseExample {

    @Test
    public void readCsv() {
        // tag::readCsv[]
        DataFrame df = Csv.load("src/test/resources/f1.csv"); // <1>
        // end::readCsv[]

        print("readCsv", df);
    }

    @Test
    public void readCsvLoader() {
        // tag::readCsvLoader[]
        DataFrame df = Csv.loader() // <1>
                .skipRows(1) // <2>
                .header("x", "y") // <3>
                .intColumn("x") // <4>
                .load("src/test/resources/f1.csv");
        // end::readCsvLoader[]

        assertTrue(df.getColumnAsInt("x") instanceof IntSeries);

        print("readCsvLoader", df);
    }

    @Test
    public void writeCsv() {
        DataFrame df = DataFrame
                .newFrame("name", "age")
                .addRow("Joe", 18)
                .addRow("Andrus", 45)
                .addRow("Joan", 32)
                .create();

        // tag::writeCsv[]
        Csv.save(df, "target/df.csv"); // <1>
        // end::writeCsv[]
    }

    @Test
    public void writeCsvSaver() {
        DataFrame df = DataFrame
                .newFrame("name", "age")
                .addRow("Joe", 18)
                .addRow("Andrus", 45)
                .addRow("Joan", 32)
                .create();

        // tag::writeCsvSaver[]
        Csv.saver() // <1>
                .createMissingDirs() // <2>
                .format(CSVFormat.EXCEL) // <3>
                .save(df, "target/csv/df.csv");
        // end::writeCsvSaver[]
    }
}
