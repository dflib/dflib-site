package com.nhl.dflib.docs;

import com.nhl.dflib.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

// tag::expImport[]
import static com.nhl.dflib.Exp.*;
// end::expImport[]

public class ExpExample extends BaseExample {

    @Test
    public void columnExp() {

// tag::columnExp[]
        StrExp lastExp = $str("last");
        DecimalExp salaryExp = $decimal(2);
// end::columnExp[]

// tag::columnExpEval[]
        DataFrame df = DataFrame.newFrame("first", "last", "salary").foldByRow(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Amanda", "Gabrielly", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

        Series<String> last = lastExp.eval(df);
        Series<BigDecimal> salary = salaryExp.eval(df);
// end::columnExpEval[]
    }
}
