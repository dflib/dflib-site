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

    @Test
    public void expChain() {

// tag::expChain[]
        // "Condition" is an Exp<Boolean> described in more detail below.
        Condition c = and(
                $str("last").startsWith("A"),
                $decimal("salary").add($decimal("benefits")).gt(100000.)
        );
// end::expChain[]
    }

    @Test
    public void sorter() {

// tag::sorter[]
        // sort by last name in the ascending order
        Sorter s = $str("last").asc();
// end::sorter[]
    }
}
