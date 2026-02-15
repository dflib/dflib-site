package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.DecimalExp;
import org.dflib.Exp;
import org.dflib.Series;
import org.dflib.Sorter;
import org.dflib.StrExp;
import org.dflib.sort.IntComparator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.dflib.Exp.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class QLTest extends BaseTest {

    @Test
    public void doParseExp() {

// tag::parseExp[]
        Exp<?> exp = parseExp("a = 3"); // <1>
        // Exp<?> exp = $col("a").eq(3) <2>

        Exp<?>[] exps = parseExps("name, salary"); // <3>
        // Exp<?>[] exps = new Exp[] { $col("name"), $col("salary")} <4>
// end::parseExp[]
        assertEquals($col("a").eq(3), exp);
        assertArrayEquals(new Exp[]{$col("name"), $col("salary")}, exps);
    }

    @Test
    public void doParseSorter() {

// tag::parseSorter[]
        Sorter s1 = Sorter.parseSorter("a");
        // same as
        // Sorter s1 = $col("a").asc()

        Sorter[] ss = Sorter.parseSorters("a, b desc");
        // same as
        // Sorter[] ss = new Sorter[]{$col("a").asc(), $col("b").desc()}
// end::parseSorter[]

        assertEquals($col("a").asc(), s1);
        assertArrayEquals(new Sorter[]{$col("a").asc(), $col("b").desc()}, ss);
    }

    @Test
    public void columnExp() {

// tag::columnExp[]
        StrExp lastExp = $str("last"); // <1>
        DecimalExp salaryExp = $decimal(2); // <2>
// end::columnExp[]

// tag::columnExpEval[]
        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

        Series<String> last = lastExp.eval(df);
        Series<BigDecimal> salary = salaryExp.eval(df);
// end::columnExpEval[]
    }

    @Test
    public void literal() {

        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

// tag::literal[]
        Series<?> hi =  Exp.parseExp("'hi!'").eval(df);
        // Series<?> hi = $val("hi!").eval(df);
// end::literal[]

        print("hi", hi);
    }

    static class MyType {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof MyType;
        }
    }

    @Test
    public void literalParam() {

// tag::literalParam[]
        Exp<?> exp1 = Exp.parseExp("a = ?", new MyType());
        Exp<?> exp2 = Exp.parseExp("a in ('S1', ?, ?)", "S2", "S3");
// end::literalParam[]

        assertEquals($col("a").eq($val(new MyType())), exp1);
        assertEquals($col("a").in($val("S1"), $val("S2"), $val("S3")), exp2);
    }

    @Test
    public void listParam() {

// tag::listParam[]
        Exp<?> exp = Exp.parseExp("a in ?", Set.of("S1", "S2", "S3"));
// end::listParam[]

        assertEquals($col("a").in($val("S1"), $val("S2"), $val("S3")), exp);
    }

    @Test
    public void arithmetics() {
        Exp<?> exp = Exp.parseExp(
                // tag::arithmetics[]
                "(int(c1) + long(c2)) / 2.0"
                // $int("c1").add($long("c2")).div(2f)
                // end::arithmetics[]
        );

        assertEquals($int("c1").add($long("c2")).div(2f), exp);
    }

    @Test
    public void as() {

        Exp<?> exp = Exp.parseExp(
                // tag::as[]
                "int(a) * 5 as x5"
                // $int("a").mul(5).as("x5")
                // end::as[]
        );
        assertEquals($int("a").mul(5).as("x5"), exp);
    }

    @Test
    public void asQuote() {

        Exp<?> exp = Exp.parseExp(
                // tag::asQuote[]
                "int(a) * 5 as `multiply by 5`"
                // $int("a").mul(5).as("multiply by 5")
                // end::asQuote[]
        );
        assertEquals($int("a").mul(5).as("multiply by 5"), exp);
    }


    @Test
    public void column() {

        Exp<?> exp = Exp.parseExp(
                // tag::column[]
                "_iAmAColumn"
                // $col("_iAmAColumn")
                // end::column[]
        );
        assertEquals($col("_iAmAColumn"), exp);
    }

    @Test
    public void columnBackticks() {

        Exp<?> exp = Exp.parseExp(
                // tag::columnBackticks[]
                "`I am a column!`"
                // $col("I am a column!")
                // end::columnBackticks[]
        );
        assertEquals($col("I am a column!"), exp);
    }

    @Test
    public void columnBackticksEscape() {

        Exp<?> exp = Exp.parseExp(
                // tag::columnBackticksEscape[]
                "```I am a column!```"
                // $col("`I am a column!`")
                // end::columnBackticksEscape[]
        );
        assertEquals($col("`I am a column!`"), exp);
    }

    @Test
    public void columnByIndex() {

        Exp<?> exp = Exp.parseExp(
                // tag::columnByIndex[]
                "col(5)"
                // $col(5)
                // end::columnByIndex[]
        );
        assertEquals($col(5), exp);
    }

    @Test
    public void strVal() {

        Exp<?> exp = Exp.parseExp(
                // tag::strVal[]
                "'I am a String!'"
                // $strVal("I am a String!")
                // end::strVal[]
        );
        assertEquals($strVal("I am a String!"), exp);
    }

    @Test
    public void strValEscape() {

        Exp<?> exp = Exp.parseExp(
                // tag::strValEscape[]
                "'I''m a String!'"
                // $strVal("I'm a String!")
                // end::strValEscape[]
        );
        assertEquals($strVal("I'm a String!"), exp);
    }

    @Test
    public void intVal() {

        Exp<?> exp = Exp.parseExp(
                // tag::intVal[]
                "5000"
                // $intVal(5000)
                // end::intVal[]
        );
        assertEquals($intVal(5000), exp);
    }

    @Test
    public void intValNegative() {

        Exp<?> exp = Exp.parseExp(
                // tag::intValNegative[]
                "-5_000_000"
                // $intVal(-5000000)
                // end::intValNegative[]
        );
        assertEquals($intVal(-5000000), exp);
    }

    @Test
    public void floatVal() {

        Exp<?> exp = Exp.parseExp(
                // tag::floatVal[]
                "5_000.01"
                // $floatVal(5000.01)
                // end::floatVal[]
        );
        assertEquals($floatVal(5000.01f), exp);
    }

    @Test
    public void decimalVal() {

        Exp<?> exp = Exp.parseExp(
                // tag::decimalVal[]
                "5_000.01m"
                // $decimalVal(new BigDecimal("5000.01"))
                // end::decimalVal[]
        );
        assertEquals($decimalVal(new BigDecimal("5000.01")), exp);
    }

    @Test
    public void typeSetToLong() {

        Exp<?> exp = Exp.parseExp(
                // tag::typeSetToLong[]
                "long(a) > 3"
                // $long("a").gt(3)
                // end::typeSetToLong[]
        );
        assertEquals($long("a").gt(3), exp);
    }

    @Test
    public void typeCastToLong() {

        Exp<?> exp = Exp.parseExp(
                // tag::typeCastToLong[]
                "castAsLong(a) > 3"
                // $col("a").castAsLong().gt(3)
                // end::typeCastToLong[]
        );
        assertEquals($col("a").castAsLong().gt(3), exp);
    }

    @Test
    public void mapVal() {

// tag::mapVal[]
        Exp<byte[]> bytes = $decimal("col")
                .mapVal(d -> d.toBigInteger().toByteArray()); // <1>
// end::mapVal[]
    }

    @Test
    public void map() {

// tag::map[]
        Exp<Integer> exp = $int("col")
                .map(s -> Series.ofVal(s.get(0), s.size())); // <1>
// end::map[]
    }

    @Test
    public void agg() {

// tag::agg[]
        Exp<Integer> exp = $col("col")
                .agg(s -> s.unique().size()); // <1>
// end::agg[]
    }

    @Test
    public void sorter() {

        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

// tag::sorter[]
        // sort by last name in the ascending order
        Sorter s = Sorter.parseSorter("last");
        // Sorter s = $col("last").asc();

        IntComparator sortIndex = s.eval(df);
// end::sorter[]

        assertEquals($col("last").asc(), s);
    }
}
