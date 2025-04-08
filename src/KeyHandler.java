import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    Panel dp;

    public KeyHandler(Panel dp){
        this.dp = dp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing to do here!
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // 'Enter' for manual search
        if (code == KeyEvent.VK_ENTER) {
            dp.manualSearch();
        }
        // 'Shift' for auto search
        else if (code == KeyEvent.VK_SHIFT) {
            dp.autoSearch();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing to do here!
    }

}
