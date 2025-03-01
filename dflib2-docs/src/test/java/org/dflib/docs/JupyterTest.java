package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.jjava.jupyter.kernel.BaseKernel;
import org.dflib.jjava.jupyter.kernel.display.Renderer;
import org.dflib.jupyter.DFLibJupyter;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JupyterTest extends BaseTest {

    @Test
    public void setDisplayParams() {

        BaseKernel kernel = mock(BaseKernel.class);
        when(kernel.getRenderer()).thenReturn(new Renderer());

        DFLibJupyter.init(kernel);

        // tag::setDisplayParams[]
        DFLibJupyter.setMaxDisplayRows(10);
        DFLibJupyter.setMaxDisplayCols(10);
        DFLibJupyter.setMaxDisplayValueWidth(50);
        // end::setDisplayParams[]
    }

    @Test
    public void customPrinter() {

        BaseKernel kernel = mock(BaseKernel.class);
        when(kernel.getRenderer()).thenReturn(new Renderer());

        DFLibJupyter.init(kernel);

        DataFrame df = DataFrame.foldByRow("a", "b").of();

        // tag::customPrinter[]
        Printers.tabular(4, 100, 500).print(df);
        // end::customPrinter[]
    }
}
