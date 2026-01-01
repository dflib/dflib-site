package org.dflib.jjava.docs;

import org.dflib.jjava.kernel.JavaKernel;
import org.junit.jupiter.api.Test;

import static org.dflib.jjava.jupyter.kernel.BaseNotebookStatics.display;
import static org.dflib.jjava.jupyter.kernel.BaseNotebookStatics.updateDisplay;
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

    @Test
    public void doDisplayMime() {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            // tag::doDisplayMime[]
            display("<b>bold</b>", "text/html");
            display("_italic_", "text/markdown");
            // end::doDisplayMime[]

        } finally {
            kernel1.onShutdown(false);
        }
    }

    @Test
    public void doUpdateDisplay() throws InterruptedException {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            // tag::doUpdateDisplay[]
            String id = display("Countdown: 3");
            for (int i = 3; i >= 0; i--) {
                updateDisplay(id, "Countdown: " + i);
                Thread.sleep(1000L);
            }

            display("Liftoff!");
            // end::doUpdateDisplay[]

        } finally {
            kernel1.onShutdown(false);
        }
    }
}
