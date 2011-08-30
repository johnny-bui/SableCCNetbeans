package com.dreamer.outputhandler;

import java.awt.Color;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.logging.Logger;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputListener;

/**
 * This class handles the output of messages to the output tabs within the platform.
 * @author Javier A. Ortiz Bultrón <javier.ortiz.78@gmail.com>
 */
public final class OutputHandler {

    private static HashMap<String, Thread> monitorThreads =
            new HashMap<String, Thread>();

    /**
     * Private to avoid initialization
     */
    private OutputHandler() {
    }
    /**
     * Map of tabs created with this class
     */
    private final static HashMap<String, InputMonitor> outputMap =
            new HashMap<String, InputMonitor>();

    /**
     * Display output
     * @param name Name of the tab
     * @param mess Message to display
     * @param c Color of the text
     */
    public static void output(String name, String mess, Color c) {
        output(name, mess, c, null);
    }

    /**
     * Display output
     * @param name Name of the tab
     * @param mess Message to display
     * @param listener Listener to be added
     */
    public static void output(String name, String mess, OutputListener listener) {
        output(name, mess, Color.BLACK, listener);
    }

    /**
     * Display output (using default)
     * @param mess Message to display
     */
    public static void output(String mess) {
        output(null, mess, Color.BLACK, null);
    }

    /**
     * Display output (using default)
     * @param mess Message to display
     * @param c Color of the text
     */
    public static void output(String mess, Color c) {
        output(null, mess, c, null);
    }

    /**
     * Display output (using default)
     * @param mess Message to display
     * @param listener Listener to be added
     */
    public static void output(String mess, OutputListener listener) {
        output(null, mess, Color.BLACK, listener);
    }

    /**
     * Print in specified color
     * @param name Tab name
     * @param mess Message to display
     * @param c Color to display the method
     * @param listener Listener to be added
     */
    public static void output(String name, String mess, Color c,
            OutputListener listener) {
        if (name == null || name.isEmpty()) {
            name = IOProvider.getDefault().getName();
        }
        boolean select = getIO(name) || !has(name);
        InputOutput io = IOProvider.getDefault().getIO(name, select);
        if (select) {
            io.select();
        }
        io.setFocusTaken(false);
        if (mess == null || mess.trim().isEmpty()) {
            if (listener == null) {
                io.getOut().print(mess);
            } else {
                try {
                    io.getOut().println(mess, listener);
                } catch (IOException ex1) {
                    Logger.getLogger(OutputHandler.class.getSimpleName(),
                            ex1.getMessage());
                    io.getOut().print(mess);
                }
            }
        } else {
            if (c != null && c != Color.BLACK) {
                try {
                    if (listener == null) {
                        IOColorLines.println(io, mess, c);
                    } else {
                        IOColorLines.println(io, mess, listener, true, c);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OutputHandler.class.getSimpleName(),
                            ex.getMessage());
                    if (listener == null) {
                        io.getOut().print(mess);
                    } else {
                        try {
                            io.getOut().println(mess, listener);
                        } catch (IOException ex1) {
                            Logger.getLogger(OutputHandler.class.getSimpleName(),
                                    ex1.getMessage());
                            io.getOut().print(mess);
                        }
                    }
                }
            } else {
                //Just print in black as default
                if (listener == null) {
                    io.getOut().print(mess);
                } else {
                    try {
                        io.getOut().println(mess, listener);
                    } catch (IOException ex1) {
                        Logger.getLogger(OutputHandler.class.getSimpleName(),
                                ex1.getMessage());
                        io.getOut().print(mess);
                    }
                }
            }
        }
    }

    /**
     * Print in default color black
     * @param name Tab name
     * @param mess Message to display
     */
    public static void output(String name, String mess) {
        output(name, mess, Color.BLACK, null);
    }

    /**
     * Clear a tab (close and so it'll be recreated next time)
     * @param name Tab name
     */
    public static void clear(String name) {
        if (has(name)) {
            try {
                //Remove it
                IOProvider.getDefault().getIO(name, false).getOut().reset();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private static boolean getIO(String name) {
        boolean create = true;
        if (has(name)) {
            create = false;
        } else {
            outputMap.put(name, null);
        }
        return create;
    }

    /**
     * Close all I/O tabs (defined). 
     * Note: outputMap will be empty after this call.
     */
    public static void closeOutputs() {
        synchronized (outputMap) {
            for (String name : outputMap.keySet()) {
                closeOutput(name, false);
            }
            outputMap.clear();
        }
    }

    /**
     * Close the I/O tab and any related monitor. 
     * It is removed from the map as well.
     * @param name Name of the tab to close.
     */
    public static void closeOutput(String name) {
        closeOutput(name, true);
    }
    
    public static void closeOutput(String name, boolean atomic) {
        synchronized (outputMap) {
            IOProvider.getDefault().getIO(name, false).closeInputOutput();
            InputMonitor monitor = outputMap.get(name);
            //If it has a monitor, stop it
            if (monitor != null) {
                monitor.stop();
            }
            if (atomic) {
                outputMap.remove(name);
            }
        }
    }

    /**
     * Create an input monitor for the specified tab.
     * @param name Name of the tab
     * @param polling Delay in milliseconds between polls.
     * @return InputMonitor created. 
     * It'll return null if the tab doesn't exist.
     */
    public static InputMonitor createMonitor(String name, int polling) {
        if (has(name) && outputMap.get(name) == null) {
            InputMonitor monitor = new InputMonitor(name, polling);
            outputMap.put(name, monitor);
            //Enable input on the tab
            setInputEnabled(name, true);
            monitorThreads.put(name, new Thread(monitor));
            monitorThreads.get(name).start();
        }
        return outputMap.get(name);
    }

    /**
     * Stop monitoring a tab
     * @param name Tab to monitor
     * @return true 
     */
    public static boolean stopMonitoring(String name) {
        if (has(name) && outputMap.get(name) != null) {
            InputMonitor monitor = outputMap.get(name);
            monitor.stop();
            outputMap.put(name, null);
            monitorThreads.put(name, null);
        }
        return has(name) && outputMap.get(name) == null;
    }

    /**
     * Select an output tab (if it exists)
     * @param name Tab name
     */
    public static void select(String name) {
        if (has(name)) {
            IOProvider.getDefault().getIO(name, getIO(name)).select();
        }
    }

    /**
     * Update the message in the status windows of the platform
     * @param mess
     */
    public static void setStatus(String mess) {
        StatusDisplayer.getDefault().setStatusText(mess);
    }

    /**
     * Get read 
     * @param name Tab name
     * @return Reader for the output tab
     */
    public static Reader getReader(String name) {
        Reader reader = null;
        if (has(name)) {
            reader = IOProvider.getDefault().getIO(name, false).getIn();
        }
        return reader;
    }

    /**
     * Enable/Disable input for 
     * @param name Tab name
     * @param enable 
     */
    public static void setInputEnabled(String name, boolean enable) {
        if (has(name)) {
            IOProvider.getDefault().getIO(name, false).setInputVisible(enable);
        }
    }

    public static boolean has(String name) {
        return outputMap.containsKey(name);
    }
}
