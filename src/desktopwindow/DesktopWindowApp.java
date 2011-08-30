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
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Пропускная способность сети");
        launch(DesktopWindowApp.class, args);
    }
}
