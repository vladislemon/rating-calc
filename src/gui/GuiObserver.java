package gui;

import main.Observer;
import main.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * slimon
 * 07.07.2014
 */
public class GuiObserver extends JPanel {

    public JPanel settings, color, time, timeFields, coords, buttons;
    public JLabel colorLabel, timeLeft, timeLabel, timeRandomLabel, coordsLabel, timeLeftLabel;
    public JTextField colorField, timeField, timeRandomField, coordsField;
    public JCheckBox coordsCheckBox;
    public JButton save, start, stop, checkScreen;

    public GuiObserver() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.PAGE_AXIS));

        color = new JPanel();
        color.setLayout(new BoxLayout(color, BoxLayout.PAGE_AXIS));
        colorLabel = new JLabel("Цвета RGB:");
        colorField = new JTextField();
        colorField.setMaximumSize(new Dimension(350, 20));
        colorField.setText(Options.get("color"));

        time = new JPanel();
        time.setLayout(new BoxLayout(time, BoxLayout.PAGE_AXIS));
        timeLabel = new JLabel("Интервал обновления:");
        timeFields = new JPanel();
        timeFields.setLayout(new BoxLayout(timeFields, BoxLayout.LINE_AXIS));
        timeField = new JTextField();
        timeField.setMaximumSize(new Dimension(150, 20));
        timeField.setText(Options.get("time"));
        timeRandomLabel = new JLabel("+/-");
        timeRandomField = new JTextField();
        timeRandomField.setMaximumSize(new Dimension(150, 20));
        timeRandomField.setText(Options.get("randTime"));

        coords = new JPanel();
        coords.setLayout(new BoxLayout(coords, BoxLayout.LINE_AXIS));
        coordsLabel = new JLabel("Область сканирования:");
        coordsCheckBox = new JCheckBox();
        coordsCheckBox.setSelected(Boolean.parseBoolean(Options.get("coordsEnabled")));
        coordsField = new JTextField();
        coordsField.setMaximumSize(new Dimension(350, 20));
        coordsField.setText(Options.get("coords"));
        coordsField.setEnabled(Boolean.parseBoolean(Options.get("coordsEnabled")));
        checkScreen = new JButton("Проверить");

        save = new JButton("Сохранить настройки");

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        timeLeftLabel = new JLabel("Время до обновления:");
        timeLeft = new JLabel("-");
        start = new JButton("Начать");
        stop = new JButton("Остановить");
        start.setMaximumSize(stop.getMaximumSize());
        stop.setEnabled(false);


        linkComponents();
        initActionListeners();
    }

    private void linkComponents() {
        this.add(settings);

        settings.add(color);
        color.add(colorLabel);
        color.add(colorField);

        settings.add(time);
        time.add(timeLabel);
        time.add(timeFields);
        timeFields.add(timeField);
        timeFields.add(timeRandomLabel);
        timeFields.add(timeRandomField);

        settings.add(coordsLabel);
        settings.add(coords);
        coords.add(coordsCheckBox);
        coords.add(coordsField);

        settings.add(checkScreen);

        settings.add(save);

        this.add(buttons);

        buttons.add(timeLeftLabel);
        buttons.add(timeLeft);
        buttons.add(start);
        buttons.add(stop);
    }

    private void initActionListeners() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Observer.saveSettings();
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Observer.startObserve()) {
                    start.setEnabled(false);
                    stop.setEnabled(true);
                }
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Observer.stopObserve()) {
                    start.setEnabled(true);
                    stop.setEnabled(false);
                }
            }
        });

        coordsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordsField.setEnabled(coordsCheckBox.isSelected());
                //save.doClick();
            }
        });

        checkScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Observer.saveScreenShotToFile();
            }
        });
    }
}
