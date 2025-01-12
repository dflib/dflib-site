package org.dflib.docs;

import org.dflib.DataFrame;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class MainDataStructuresTest extends BaseTest {

    @Test
    public void indexGetLabels() {
        DataFrame df = DataFrame
                .foldByColumn("col1", "col2")
                .of(IntStream.range(0, 10000));

// tag::indexGetLabels[]
        String[] labels = df.getColumnsIndex().toArray();
// end::indexGetLabels[]
    }
}
