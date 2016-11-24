package io.antielectron.framework.window;

import io.antielectron.framework.event.*;
import io.antielectron.framework.geometry.IPositional;
import io.antielectron.framework.app.App;
import io.antielectron.framework.geometry.IMovable;
import io.antielectron.framework.geometry.IRectDimensional;
import io.antielectron.framework.geometry.IRectResizable;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

/**
 * TODO Document
 * @author Evan Geng
 */
public class BrowserWindow implements IRectResizable, IMovable {

    private final App parent;
    private final SubEngine engine;
    private final Stage stage;
    private final Consumer<BrowserWindow> destructFunction;

    public final NullaryEventStream onPageLoad = new NullaryEventStream();
    // public final UnaryEventStream<String> onPageTitleUpdate = new UnaryEventStream<>(); TODO Implement
    public final UnaryEventStream<ICancellable> onCloseButton = new UnaryEventStream<>();
    public final NullaryEventStream onFocus = new NullaryEventStream();
    public final NullaryEventStream onUnfocus = new NullaryEventStream();
    public final UnaryEventStream<IRectDimensional> onResize = new UnaryEventStream<>();
    public final UnaryEventStream<IPositional> onMove = new UnaryEventStream<>();
    public final UnaryEventStream<ClickEvent> onContextMenu = new UnaryEventStream<>();

    public BrowserWindow(App parent, Consumer<BrowserWindow> destructFunction) {
        this.parent = parent;
        this.engine = new SubEngine(this);
        this.stage = new Stage();
        stage.setScene(new Scene(engine.getWebView()));
        this.destructFunction = destructFunction;
        initEventStreams();
    }

    private void initEventStreams() {
        stage.setOnCloseRequest(e -> {
            ICancellable event = new DefaultCancellable();
            onCloseButton.accept(event);
            if (!event.isCancelled())
                close();
        });
        stage.focusedProperty().addListener((var, oV, nV) -> {
            if (nV)
                onFocus.accept();
            else
                onUnfocus.accept();
        });
        stage.widthProperty().addListener((var, oV, nV) -> onResize.accept(this));
        stage.heightProperty().addListener((var, oV, nV) -> onResize.accept(this));
        stage.xProperty().addListener((var, oV, nV) -> onMove.accept(this));
        stage.yProperty().addListener((var, oV, nV) -> onMove.accept(this));
        EventHandler<? super ContextMenuEvent> oldCtxMenuHandler = stage.getScene().getOnContextMenuRequested();
        stage.getScene().setOnContextMenuRequested(e -> {
            ClickEvent event = new ClickEvent((int)e.getX(), (int)e.getY(), MouseButton.SECONDARY);
            onContextMenu.accept(event);
            if (!event.isCancelled())
                oldCtxMenuHandler.handle(e);
        });
    }

    public SubEngine getEngine() {
        return engine;
    }

    @Override
    public int getX() {
        return (int)stage.getX();
    }

    @Override
    public int getY() {
        return (int)stage.getY();
    }

    @Override
    public int getWidth() {
        return (int)stage.getWidth();
    }

    @Override
    public int getHeight() {
        return (int)stage.getHeight();
    }

    @Override
    public BrowserWindow setX(int x) {
        stage.setX(x);
        return this;
    }

    @Override
    public BrowserWindow setY(int y) {
        stage.setY(y);
        return this;
    }

    @Override
    public BrowserWindow setWidth(int width) {
        stage.setWidth(width);
        return this;
    }

    @Override
    public BrowserWindow setHeight(int height) {
        stage.setHeight(height);
        return this;
    }

    @Override
    public BrowserWindow setMinWidth(int width) {
        stage.setMinWidth(width);
        return this;
    }

    @Override
    public BrowserWindow setMaxWidth(int width) {
        stage.setMaxWidth(width);
        return this;
    }

    @Override
    public BrowserWindow setMinHeight(int height) {
        stage.setMinHeight(height);
        return this;
    }

    @Override
    public BrowserWindow setMaxHeight(int height) {
        stage.setMaxHeight(height);
        return this;
    }

    public BrowserWindow setResizable(boolean resizable) {
        stage.setResizable(resizable);
        return this;
    }

    public BrowserWindow setAlwaysOnTop(boolean alwaysOnTop) {
        stage.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    public BrowserWindow setTitle(String title) {
        stage.setTitle(title);
        return this;
    }

    public BrowserWindow setIcon(Image... icon) {
        stage.getIcons().clear();
        stage.getIcons().addAll(icon);
        return this;
    }

    public BrowserWindow setBackgroundColour(Color colour) {
        stage.getScene().setFill(colour);
        return this;
    }

    public BrowserWindow setFrameless(boolean frameless) {
        stage.initStyle(frameless ? StageStyle.UNDECORATED : StageStyle.DECORATED);
        return this;
    }

    public boolean isFocused() {
        return stage.isFocused();
    }

    public BrowserWindow focus() {
        stage.requestFocus();
        return this;
    }

    public BrowserWindow setVisible(boolean visible) {
        if (visible)
            stage.show();
        else
            stage.hide();
        return this;
    }

    public BrowserWindow onClosed(Consumer<BrowserWindow> handler) {
        parent.onWindowClosed.always(handler);
        return this;
    }

    public void close() {
        stage.close();
        engine.cleanUp();
        destructFunction.accept(this);
    }

    public boolean isOpen() {
        return engine.isActive();
    }

    public void minimize() {
        stage.setIconified(true);
    }

    public void restore() {
        stage.setIconified(false);
    }

    public void maximize() {
        stage.setMaximized(true);
    }

    public void unmaximize() {
        stage.setMaximized(false);
    }

    public void center() {
        stage.centerOnScreen();
    }
    
}
