package org.dflib.docs;

import org.dflib.DataFrame;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class StackTest extends BaseTest {

    @Test
    public void stack() {

        DataFrame df = DataFrame.foldByRow("name", "2025-01", "2025-02").of(
                "Jerry Cosin", new BigDecimal("120000"), new BigDecimal("122000"),
                "Juliana Walewski", new BigDecimal("80000"), null,
                "Joan O'Hara", new BigDecimal("95000"), new BigDecimal("95000"));

        print("stack-src", df);
// tag::stack[]

        DataFrame p = df.stack();
// end::stack[]

        print("stack", p);
    }

    @Test
    public void stackIncludeNulls() {

        DataFrame df = DataFrame.foldByRow("name", "2025-01", "2025-02").of(
                "Jerry Cosin", new BigDecimal("120000"), new BigDecimal("122000"),
                "Juliana Walewski", new BigDecimal("80000"), null,
                "Joan O'Hara", new BigDecimal("95000"), new BigDecimal("95000"));

        print("stack-nulls-src", df);
// tag::stack-nulls[]

        DataFrame p = df.stackIncludeNulls();
// end::stack-nulls[]

        print("stack-nulls", p);
    }
}
