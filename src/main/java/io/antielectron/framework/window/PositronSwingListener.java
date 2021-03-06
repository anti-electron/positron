package io.antielectron.framework.window;

import io.antielectron.framework.event.ContextMenuEvent;
import io.antielectron.framework.event.DefaultCancellable;
import io.antielectron.framework.event.ICancellable;

import java.awt.event.*;

/**
 * TODO Document
 * @author Evan Geng
 */
class PositronSwingListener implements WindowListener, FocusListener, ComponentListener, MouseListener, KeyListener {

    private final BrowserWindow bw;

    PositronSwingListener(BrowserWindow bw) {
        this.bw = bw;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ICancellable event = new DefaultCancellable();
        bw.onCloseButton.accept(event);
        if (!event.isCancelled())
            bw.close();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // NO-OP
    }

    @Override
    public void focusGained(FocusEvent e) {
        bw.onFocus.accept();
    }

    @Override
    public void focusLost(FocusEvent e) {
        bw.onUnfocus.accept();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        bw.onResize.accept(bw);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        bw.onMove.accept(bw);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // NO-OP
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // NO-OP
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.isPopupTrigger()) {
            ContextMenuEvent event = new ContextMenuEvent(e.getX(), e.getY(), e.getModifiersEx());
            bw.onContextMenu.accept(event);
            if (event.isCancelled())
                e.consume();
        } else {
            bw.onClick.accept(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // NO-OP
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // NO-OP
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // NO-OP
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // NO-OP
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NO-OP
    }

    @Override
    public void keyPressed(KeyEvent e) {
        bw.onKeyDown.accept(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bw.onKeyUp.accept(e);
    }

}
