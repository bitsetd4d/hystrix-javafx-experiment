package com.bitsetd4d.util;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

public class JavaFxTestUtil {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public synchronized static void setupJavaFX() {
        if (latch.getCount() == 0) return;

        long timeMillis = System.currentTimeMillis();

        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });

        System.out.println("javafx initialising...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("javafx is initialised in " + (System.currentTimeMillis() - timeMillis) + "ms");
    }

    public static void waitForJavaFxToCatchup() throws InterruptedException {
        CountDownLatch waitLatch = new CountDownLatch(1);
        Platform.runLater(waitLatch::countDown);
        boolean ok = waitLatch.await(5, SECONDS);
        if (!ok) {
            throw new IllegalStateException("JavaFX didn't catch up");
        }
    }
}
