package com.nhl.dflib.docs;

import com.nhl.dflib.jupyter.DFLibJupyter;
import org.junit.jupiter.api.Test;

public class JupyterTest extends BaseTest {

    @Test
    public void setDisplayParams() {

        // tag::setDisplayParams[]
        DFLibJupyter.setMaxDisplayRows(10);
        DFLibJupyter.setMaxDisplayColumnWidth(50);
        // end::setDisplayParams[]
    }
}
