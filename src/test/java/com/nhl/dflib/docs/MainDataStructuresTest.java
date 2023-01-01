package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class MainDataStructuresTest extends BaseTest {

    @Test
    public void indexGetLabels() {

// tag::indexGetLabels[]
        DataFrame df = DataFrame
                .newFrame("col1", "col2")
                .foldIntStreamByColumn(IntStream.range(0, 10000));

        String[] labels = df.getColumnsIndex().getLabels(); // <1>
// end::indexGetLabels[]
    }
}
