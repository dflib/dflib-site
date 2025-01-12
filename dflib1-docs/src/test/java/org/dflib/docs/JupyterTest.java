package org.dflib.docs;

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
        DFLibJupyter.setMaxDisplayColumnWidth(50);
        // end::setDisplayParams[]
    }
}
