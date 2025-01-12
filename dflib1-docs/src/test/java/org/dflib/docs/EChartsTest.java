package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.echarts.EChartHtml;
import org.dflib.echarts.ECharts;
import org.dflib.echarts.SeriesOpts;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$col;

public class EChartsTest extends BaseTest {

    @Test
    void barChart() {

        // tag::barChart[]
        DataFrame df = DataFrame.foldByRow("name", "salary").of(
                        "J. Cosin", 120000,
                        "J. Walewski", 80000,
                        "J. O'Hara", 95000)
                .sort($col("salary").desc());

        EChartHtml chart = ECharts
                .chart()
                .xAxis("name")
                .series(SeriesOpts.ofBar(), "salary")
                .plot(df);
        // end::barChart[]
    }
}
