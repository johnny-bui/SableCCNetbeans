/*
 * Helper to monitor a tab for input
 */
package com.dreamer.outputhandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.util.ObservableReader;
import org.jivesoftware.smack.util.ReaderListener;
import org.openide.ErrorManager;

/**
 *
 * @author Javier A. Ortiz Bultrón <javier.ortiz.78@gmail.com>
 */
public class InputMonitor implements Runnable {

    /**
     * Reader object
     */
    private ObservableReader reader;
    /**
     * Tab name to monitor
     */
    private final String tabName;
    /**
     * Flag to enable/disable polling
     */
    private boolean run = true;
    private static final Logger logger =
            Logger.getLogger(InputMonitor.class.getSimpleName());
    /**
     * Sleep time between polls (in milliseconds)
     */
    private int sleep;

    /**
     * Constructor
     * @param tabName Name to monitor
     * @param sleep Sleep time in milliseconds between polls
     */
    public InputMonitor(String tabName, int sleep) {
        if (OutputHandler.has(tabName)) {
            this.tabName = tabName;
            logger.log(Level.INFO, "Creating monitor for tab: {0}", tabName);
            //Enable tab for input
            OutputHandler.setInputEnabled(tabName, true);
            //Create tab reader
            reader = new ObservableReader(OutputHandler.getReader(tabName));
            this.sleep = sleep;
        } else {
            throw new RuntimeException("Unable to create monitor for: " + tabName
                    + ". Tab does not exist or was not created using OutputHandler.");
        }
    }

    public void addListener(ReaderListener listener) {
        reader.addReaderListener(listener);
    }

    public void removeReaderListener(ReaderListener listener) {
        reader.removeReaderListener(listener);
    }

    @Override
    public void run() {
        run = true;
        logger.log(Level.INFO, "Starting monitoring for tab: {0}", tabName);
        while (run) {
            try {
                while (!reader.ready()) {
                    try {
                        Thread.currentThread().sleep(getSleep());
                    } catch (InterruptedException e) {
                        close();
                        return;
                    }
                }
                char[] buffer = new char[1024];
                if (reader.read(buffer, 0, buffer.length) == -1
                        || Thread.currentThread().isInterrupted()) {
                    close();
                    return;
                }
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
                return;
            }
        }
    }

    private void close() {
        try {
            logger.log(Level.INFO, "Closing reader for tab: {0}", tabName);
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioe) {
            ErrorManager.getDefault().notify(ioe);
        } finally {
        }
    }

    protected void stop() {
        logger.log(Level.INFO, "Stopping monitoring for tab: {0}", tabName);
        close();
        //Stop execution
        run = false;
    }

    /**
     * @return the tabName
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * @return the sleep
     */
    public int getSleep() {
        return sleep;
    }

    /**
     * @param sleep the sleep to set
     */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
}
