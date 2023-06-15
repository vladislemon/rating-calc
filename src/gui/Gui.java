package gui;

import javax.swing.*;
import java.awt.*;

/**
 * slimon
 * 05.07.2014
 */
public class Gui extends JFrame {

    public JPanel content;
    public GuiObserver attackObserver;
    public GuiChat chat;
    public JTabbedPane tabbedPane;

    public Gui(String title, int width, int height, int posX, int posY, boolean resizable) {
        super(title);
        setSize(width, height);
        setLocation(posX, posY);
        setResizable(resizable);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //=============================================

        content = new JPanel();
        content.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        attackObserver = new GuiObserver();

        chat = new GuiChat();

        //=============================================

        this.add(content);
        content.add(tabbedPane);

        tabbedPane.addTab("Оповещалка", attackObserver);
        tabbedPane.addTab("Чат", chat);
        //tabbedPane.addTab("Расчёт рейтинга", ratingCalc);
    }
}
