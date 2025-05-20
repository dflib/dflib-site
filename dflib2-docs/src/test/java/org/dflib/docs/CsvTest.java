package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.IntSeries;
import org.dflib.csv.Csv;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvTest extends BaseTest {

    @Test
    public void readCsv() {
        String fileName = "src/test/resources/f1.csv";
        // tag::readCsv[]
        DataFrame df = Csv.load(fileName); // <1>
        // end::readCsv[]

        print("readCsv", df);
    }

    @Test
    public void readCsvLoader() {
        String fileName = "src/test/resources/f1.csv";

        // tag::readCsvLoader[]
        DataFrame df = Csv.loader() // <1>
                .offset(1) // <2>
                .header("x", "y") // <3>
                .intCol("x") // <4>
                .load(fileName);
        // end::readCsvLoader[]

        assertTrue(df.getColumn("x").unsafeCastAs(Integer.class) instanceof IntSeries);

        print("readCsvLoader", df);
    }

    @Test
    public void writeCsv() {
        DataFrame df = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        String fileName = "target/df.csv";

        // tag::writeCsv[]
        Csv.save(df, fileName); // <1>
        // end::writeCsv[]
    }

    @Test
    public void writeCsvSaver() {
        DataFrame df = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        String fileName = "target/csv/df.csv";

        // tag::writeCsvSaver[]
        Csv.saver() // <1>
                .createMissingDirs() // <2>
                .format(CSVFormat.EXCEL) // <3>
                .save(df, fileName);
        // end::writeCsvSaver[]
    }
}
