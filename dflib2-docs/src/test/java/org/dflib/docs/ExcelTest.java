package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.excel.Excel;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ExcelTest extends BaseTest {

    @Test
    public void loadSheet() {
        String fileName = "src/test/resources/test.xlsx";
        // tag::loadSheet[]
        DataFrame df = Excel.loadSheet(fileName, "Sheet1");
        // end::loadSheet[]

        print("loadSheet", df);
    }

    @Test
    public void loadSheets() {
        String fileName = "src/test/resources/test.xlsx";
        // tag::loadSheets[]
        Map<String, DataFrame> dfs = Excel.load(fileName);
        // end::loadSheets[]

        print("loadSheets", dfs);
    }

    @Test
    public void loader() {
        String fileName = "src/test/resources/test.xlsx";
        // tag::loader[]
        DataFrame df = Excel.loader() // <1>
                .firstRowAsHeader() // <2>
                .loadSheet(fileName, "Sheet1");
        // end::loader[]

        print("loadSheet", df);
    }

    @Test
    public void saveSheet() {
        DataFrame df = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        String fileName = "target/df.xlsx";

        // tag::saveSheet[]
        Excel.saveSheet(df, fileName, "Sheet1"); // <1>
        // end::saveSheet[]
    }

    @Test
    public void saveFile() {
        DataFrame df1 = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        DataFrame df2 = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Helen", 61)
                .toDataFrame();

        String fileName = "target/dfs.xlsx";

        // tag::saveFile[]
        Map<String, DataFrame> dfs = Map.of("Sheet1", df1, "Sheet2", df2);
        Excel.save(dfs, fileName);
        // end::saveFile[]
    }

    @Test
    public void noHeader() {
        DataFrame df = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        String fileName = "target/df-no-header.xlsx";

        // tag::noHeader[]
        Excel.saver().noHeader().saveSheet(df, fileName, "Sheet1");
        // end::noHeader[]
    }

}
