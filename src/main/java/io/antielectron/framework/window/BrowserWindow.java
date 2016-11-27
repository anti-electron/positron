package io.antielectron.framework.window;

import io.antielectron.framework.app.App;
import io.antielectron.framework.event.ContextMenuEvent;
import io.antielectron.framework.event.ICancellable;
import io.antielectron.framework.event.NullaryEventStream;
import io.antielectron.framework.event.UnaryEventStream;
import io.antielectron.framework.geometry.IMovable;
import io.antielectron.framework.geometry.IPositional;
import io.antielectron.framework.geometry.IRectDimensional;
import io.antielectron.framework.geometry.IRectResizable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * TODO Document
 * @author Evan Geng
 */
public class BrowserWindow implements IRectResizable, IMovable {

    private final App parent;
    private final SubEngine engine;
    private final JFrame window;
    private final Consumer<BrowserWindow> destructFunction;
    private PositronSwingListener swingListener;

    public final UnaryEventStream<String> onPageLoad = new UnaryEventStream<>();
    public final UnaryEventStream<String> onPageTitleUpdate = new UnaryEventStream<>();
    public final UnaryEventStream<String> onConsoleMessage = new UnaryEventStream<>();
    public final UnaryEventStream<ICancellable> onCloseButton = new UnaryEventStream<>();
    public final NullaryEventStream onClosed = new NullaryEventStream();
    public final NullaryEventStream onFocus = new NullaryEventStream();
    public final NullaryEventStream onUnfocus = new NullaryEventStream();
    public final UnaryEventStream<IRectDimensional> onResize = new UnaryEventStream<>();
    public final UnaryEventStream<IPositional> onMove = new UnaryEventStream<>();
    public final UnaryEventStream<MouseEvent> onClick = new UnaryEventStream<>();
    public final UnaryEventStream<ContextMenuEvent> onContextMenu = new UnaryEventStream<>();
    public final UnaryEventStream<KeyEvent> onKeyDown = new UnaryEventStream<>();
    public final UnaryEventStream<KeyEvent> onKeyUp = new UnaryEventStream<>();

    public BrowserWindow(App parent, Dimension size, Consumer<BrowserWindow> destructFunction) {
        this.parent = parent;
        this.engine = new SubEngine(this);
        this.window = new JFrame();
        window.setLayout(new CardLayout(0, 0));
        window.getContentPane().add(engine.getBrowserView());
        window.setSize(size);
        this.destructFunction = destructFunction;
        initEventStreams();
    }

    private void initEventStreams() {
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        swingListener = new PositronSwingListener(this);
        window.addWindowListener(swingListener);
        window.addFocusListener(swingListener);
        window.addComponentListener(swingListener);
        engine.initListeners(swingListener);
    }

    public App getParentApp() {
        return parent;
    }

    public SubEngine getEngine() {
        return engine;
    }

    @Override
    public int getX() {
        return window.getX();
    }

    @Override
    public int getY() {
        return window.getY();
    }

    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    @Override
    public BrowserWindow setPosition(int x, int y) {
        window.setLocation(x, y);
        return this;
    }

    @Override
    public BrowserWindow setSize(int width, int height) {
        window.setSize(width, height);
        return this;
    }

    @Override
    public BrowserWindow setMinSize(int width, int height) {
        window.setMinimumSize(new Dimension(width, height));
        return this;
    }

    @Override
    public BrowserWindow setMaxSize(int width, int height) {
        window.setMaximumSize(new Dimension(width, height));
        return this;
    }

    public BrowserWindow setResizable(boolean resizable) {
        window.setResizable(resizable);
        return this;
    }

    public BrowserWindow setAlwaysOnTop(boolean alwaysOnTop) {
        window.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    public BrowserWindow setTitle(String title) {
        window.setTitle(title);
        return this;
    }

    public BrowserWindow setIcon(Image icon) {
        window.setIconImage(icon);
        return this;
    }

    public BrowserWindow setBackgroundColour(Color colour) {
        window.setBackground(colour);
        return this;
    }

    public BrowserWindow setFrameless(boolean frameless) {
        window.setUndecorated(frameless);
        return this;
    }

    public boolean isFocused() {
        return window.isFocused();
    }

    public BrowserWindow focus() {
        window.requestFocus();
        return this;
    }

    public BrowserWindow setVisible(boolean visible) {
        window.setVisible(visible);
        return this;
    }

    public void close() {
        window.dispose();
        engine.cleanUp();
        destructFunction.accept(this);
        onClosed.accept();
    }

    public boolean isOpen() {
        return engine.isActive();
    }

    public void minimize() {
        window.setState(Frame.NORMAL);
    }

    public void restore() {
        window.setState(Frame.ICONIFIED);
    }

    public void maximize() {
        window.setState(Frame.MAXIMIZED_BOTH);
    }

    public void unmaximize() {
        window.setState(Frame.NORMAL);
    }

    public void center() {
        window.setLocationRelativeTo(null);
    }

}
