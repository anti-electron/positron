package io.antielectron.framework.app;

import io.antielectron.framework.event.IntCancellable;
import io.antielectron.framework.event.NullaryEventStream;
import io.antielectron.framework.event.UnaryEventStream;
import io.antielectron.framework.window.BrowserWindow;
import io.antielectron.framework.window.ScreenInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * TODO Document
 * @author Evan Geng
 */
public class App extends Application {

    private static boolean initialized = false;
    private static BiConsumer<App, String[]> mainFunc;
    private static String[] argArray;

    public static void startApp(String[] args, BiConsumer<App, String[]> main) {
        if (!initialized) {
            initialized = true;
            mainFunc = main;
            argArray = args;
            Application.launch(App.class, args);
        } else {
            throw new IllegalStateException("Positron already initialized!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        primaryStage.close();
        mainFunc.accept(this, argArray);
    }

    private final Set<BrowserWindow> windows = new HashSet<>();
    private final ScreenInfo screenInfo = new ScreenInfo();

    public final UnaryEventStream<BrowserWindow> onWindowClosed = new UnaryEventStream<>();
    public final NullaryEventStream onAllWindowsClosed = new NullaryEventStream();
    public final UnaryEventStream<IntCancellable> onQuit = new UnaryEventStream<>();

    public BrowserWindow createWindow(int width, int height) {
        BrowserWindow window = new BrowserWindow(this, new Dimension(width, height), this::destroyWindow);
        windows.add(window);
        return window;
    }

    private void destroyWindow(BrowserWindow window) {
        windows.remove(window);
        onWindowClosed.accept(window);
        if (windows.isEmpty())
            onAllWindowsClosed.accept();
    }

    public BrowserWindow getFocusedWindow() {
        return windows.stream().filter(BrowserWindow::isFocused).findAny().orElse(null);
    }

    public Set<BrowserWindow> getAllWindows() {
        return Collections.unmodifiableSet(windows);
    }

    public ScreenInfo getScreen() {
        return screenInfo;
    }

    public void enableImplicitQuit() {
        onAllWindowsClosed.always(() -> exit(0));
    }

    public void exit(int code) {
        IntCancellable event = new IntCancellable(code);
        onQuit.accept(event);
        if (!event.isCancelled())
            Runtime.getRuntime().exit(code);
    }

}
