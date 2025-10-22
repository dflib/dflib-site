package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.jjava.kernel.JavaKernel;
import org.dflib.jupyter.DFLibJupyter;
import org.junit.jupiter.api.Test;

public class JupyterTest extends BaseTest {

    @Test
    public void setDisplayParams() {

        JavaKernel kernel = JavaKernel.builder().build();

        try {
            kernel.onStartup();

            // tag::setDisplayParams[]
            DFLibJupyter.setMaxDisplayRows(10);
            DFLibJupyter.setMaxDisplayCols(10);
            DFLibJupyter.setMaxDisplayValueWidth(50);
            // end::setDisplayParams[]

        } finally {
            kernel.onShutdown(false);
        }
    }

    @Test
    public void customPrinter() {

        JavaKernel kernel = JavaKernel.builder().build();

        try {
            kernel.onStartup();
            DataFrame df = DataFrame.foldByRow("a", "b").of();

            // tag::customPrinter[]
            Printers.tabular(4, 100, 500).print(df);
            // end::customPrinter[]

        } finally {
            kernel.onShutdown(false);
        }
    }
}
