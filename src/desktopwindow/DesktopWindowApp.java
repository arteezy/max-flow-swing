package desktopwindow;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

public class DesktopWindowApp extends SingleFrameApplication {

    @Override protected void startup() {
        show(new DesktopWindowView(this));
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static DesktopWindowApp getApplication() {
        return Application.getInstance(DesktopWindowApp.class);
    }

    public static void main(String[] args) {
        launch(DesktopWindowApp.class, args);
    }
}
