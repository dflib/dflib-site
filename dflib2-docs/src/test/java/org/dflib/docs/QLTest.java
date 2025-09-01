package org.dflib.docs;

import org.dflib.Exp;
import org.dflib.Sorter;
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
        Exp<?> exp = Exp.parseExp("a = 3"); // <1>
        // same as
        //    $col("a").eq(3)

        Exp<?>[] exps = Exp.parseExpArray("name, salary"); // <2>
        // same as
        //    new Exp[] { $col("name"), $col("salary")}
// end::parseExp[]
        assertEquals(Exp.$col("a").eq(3), exp);
        assertArrayEquals(new Exp[] {Exp.$col("name"), Exp.$col("salary")}, exps);
    }

    @Test
    public void doParseSorter() {

// tag::parseSorter[]
        Sorter s1 = Sorter.parseSorter("a");
        Sorter[] ss = Sorter.parseSorterArray("a, b desc");
// end::parseSorter[]
    }

    @Test
    public void literalParam() {

// tag::literalParam[]
        Exp<?> exp1 = Exp.parseExp("str(a) = ?", "S1");
        Exp<?> exp2 = Exp.parseExp("str(a) in ('S1', ?, ?)", "S2", "S3");
// end::literalParam[]

        assertEquals($str("a").eq($strVal("S1")), exp1);
        assertEquals($str("a").in($strVal("S1"), $strVal("S2"), $strVal("S3")), exp2);
    }

    @Test
    public void listParam() {

// tag::listParam[]
        Exp<?> exp = Exp.parseExp("str(a) in ?", Set.of("S1", "S2", "S3"));
// end::listParam[]

        assertEquals($str("a").in($strVal("S1"), $strVal("S2"), $strVal("S3")), exp);
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
        assertEquals( $col(5), exp);
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
}
