package org.dflib.jjava.docs;

import org.dflib.jjava.kernel.JavaKernel;
import org.junit.jupiter.api.Test;

import static org.dflib.jjava.jupyter.kernel.BaseNotebookStatics.display;
import static org.dflib.jjava.kernel.JavaNotebookStatics.kernel;

public class KernelTest {

    @Test
    public void doKernel() {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            // tag::doKernel[]
            JavaKernel kernel = kernel(); // <1>
            // end::doKernel[]

        } finally {
            kernel1.onShutdown(false);
        }
    }

    @Test
    public void doDisplay() {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            Object o = new Object();

            // tag::doDisplay[]
            display(o);
            // end::doDisplay[]

        } finally {
            kernel1.onShutdown(false);
        }
    }
}
