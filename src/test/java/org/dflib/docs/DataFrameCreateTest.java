package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Extractor;
import org.dflib.Series;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class DataFrameCreateTest extends BaseTest {

    @Test
    public void createRowAtATime() {
// tag::createRowAtATime[]
        DataFrame df = DataFrame
                .byArrayRow("name", "age") // <1>
                .appender() // <2>
                .append("Joe", 18)   // <3>
                .append("Andrus", 49)
                .append("Joan", 32)
                .toDataFrame();
// end::createRowAtATime[]

        print("createRowAtATime", df);
    }

    @Test
    public void createRowAtATime_FromObject() {
// tag::createRowAtATimeFromObject[]
        record Person(String name, int age) {
        }

        List<Person> people = List.of(
                new Person("Joe", 18),
                new Person("Andrus", 49),
                new Person("Joan", 32));

        DataFrame df = DataFrame
                .byRow( // <1>
                        Extractor.$col(Person::name),
                        Extractor.$int(Person::age))
                .columnNames("name", "age") // <2>
                .appender() // <3>
                .append(people)   // <4>
                .toDataFrame();
// end::createRowAtATimeFromObject[]

        print("createRowAtATimeFromObject", df);
    }

    @Test
    public void createFoldByRow() {
// tag::createFoldByRow[]
        DataFrame df = DataFrame
                .foldByRow("name", "age") // <1>
                .of("Joe", 18, "Andrus", 49, "Joan", 32); // <2>
// end::createFoldByRow[]

        print("createFoldByRow", df);
    }

    @Test
    public void createFoldByColumn() {
// tag::createFoldByColumn[]
        DataFrame df = DataFrame
                .foldByColumn("name", "age")
                .of("Joe", "Andrus", "Joan", 18, 49, 32);
// end::createFoldByColumn[]

        print("createFoldByColumn", df);
    }

    @Test
    public void createFromIntStream() {
// tag::createFromIntStream[]
        DataFrame df = DataFrame
                .foldByColumn("col1", "col2")
                .ofStream(IntStream.range(0, 10000));
// end::createFromIntStream[]

        print("createFromIntStream", df);
    }

    @Test
    public void createFromSeries() {
// tag::createFromSeries[]
        DataFrame df = DataFrame
                .byColumn("name", "age")
                .of(
                        Series.of("Joe", "Andrus", "Joan"),
                        Series.ofInt(18, 49, 32)
                );
// end::createFromSeries[]

        print("createFromSeries", df);
    }
}
