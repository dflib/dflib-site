package org.dflib.docs;

import org.dflib.DataFrame;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PivotTest extends BaseTest {

    @Test
    public void pivot() {

        DataFrame df = DataFrame.foldByRow("name", "salary", "month").of(
                "Jerry Cosin", new BigDecimal("120000"), "2025-01",
                "Jerry Cosin", new BigDecimal("122000"), "2025-02",
                "Juliana Walewski", new BigDecimal("80000"), "2025-01",
                "Joan O'Hara", new BigDecimal("95000"), "2025-01",
                "Joan O'Hara", new BigDecimal("95000"), "2025-02");

        print("pivot-src", df);
// tag::pivot[]


        DataFrame p = df.pivot()
                .cols("month")
                .rows("name")
                .vals("salary"); // <1>
// end::pivot[]

        print("pivot", p);
    }

    @Test
    public void pivot_Agg() {
        DataFrame df = DataFrame.foldByRow("name", "salary", "month").of(
                "Jerry Cosin", new BigDecimal("120000"), "2025-01",
                "Jerry Cosin", new BigDecimal("122000"), "2025-02",
                "Juliana Walewski", new BigDecimal("80000"), "2025-01",
                "Juliana Walewski", new BigDecimal("1000"), "2025-01",
                "Joan O'Hara", new BigDecimal("95000"), "2025-01",
                "Joan O'Hara", new BigDecimal("95000"), "2025-02");

        print("pivot-agg-src", df);

// tag::pivot_Agg[]
        DataFrame p = df.pivot()
                .cols("month")
                .rows("name")
                .vals("salary", e -> e.castAsDecimal().sum());
// end::pivot_Agg[]

        print("pivot_Agg", p);
    }
}
