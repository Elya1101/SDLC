package com.lab.converter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

    private static final Color BACKGROUND =
        new Color(245, 247, 250);

    private static final Color CARD =
        Color.WHITE;

    private static final Color TEXT =
        new Color(45, 50, 55);

    private static final Color SECONDARY =
        new Color(105, 112, 120);

    private static final Color PRIMARY =
        new Color(35, 105, 190);

    private static final Color PRIMARY_DARK =
        new Color(25, 80, 155);

    private static final Color BORDER =
        new Color(220, 224, 229);

    private JLabel lblAmount;
    private JLabel lblRate;
    private JLabel lblResult;
    private JButton btnInput;

    public View() {
        setTitle("Конвертер BYN → EUR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(360, 330));
        setSize(430, 400);
        setLocationRelativeTo(null);

        createInterface();
    }

    private void createInterface() {
        JPanel root = new JPanel(new BorderLayout(0, 15));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(20, 22, 20, 22));

        // Заголовок
        JPanel header = new JPanel(
            new BorderLayout(0, 4)
        );
        header.setOpaque(false);

        JLabel title = new JLabel("Конвертер валют");
        title.setFont(new Font(
            "SansSerif",
            Font.BOLD,
            22
        ));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
            "Белорусские рубли → евро"
        );
        subtitle.setFont(new Font(
            "SansSerif",
            Font.PLAIN,
            13
        ));
        subtitle.setForeground(SECONDARY);

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);

        // Информация
        JPanel dataPanel = new JPanel(
            new GridLayout(3, 1, 0, 8)
        );
        dataPanel.setOpaque(false);

        lblAmount = createLabel();
        lblRate = createLabel();
        lblResult = createResultLabel();

        dataPanel.add(createCard(lblAmount));
        dataPanel.add(createCard(lblRate));
        dataPanel.add(createResultCard(lblResult));

        root.add(dataPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(
            new BorderLayout(0, 7)
        );
        bottom.setOpaque(false);

        btnInput = createButton("Ввод чисел");

        JLabel hint = new JLabel(
            "Введите сумму и курс, например: 100 3.50",
            SwingConstants.CENTER
        );
        hint.setFont(new Font(
            "SansSerif",
            Font.PLAIN,
            11
        ));
        hint.setForeground(SECONDARY);

        bottom.add(btnInput, BorderLayout.NORTH);
        bottom.add(hint, BorderLayout.SOUTH);

        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font(
            "SansSerif",
            Font.PLAIN,
            16
        ));
        label.setForeground(TEXT);
        label.setBorder(new EmptyBorder(
            0, 15, 0, 15
        ));
        return label;
    }

    private JLabel createResultLabel() {
        JLabel label = createLabel();
        label.setFont(new Font(
            "SansSerif",
            Font.BOLD,
            19
        ));
        return label;
    }

    private JPanel createCard(JLabel label) {
        JPanel panel = new JPanel(
            new BorderLayout()
        );
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(10, 0, 10, 0)
        ));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createResultCard(JLabel label) {
        JPanel panel = createCard(label);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(180, 200, 225)
            ),
            new EmptyBorder(10, 0, 10, 0)
        ));
        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font(
            "SansSerif",
            Font.BOLD,
            14
        ));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(35, 105, 190));

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.setFocusPainted(false);
        button.setCursor(new Cursor(
            Cursor.HAND_CURSOR
        ));

        button.setMargin(new Insets(
            12, 15, 12, 15
        ));

        button.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(
                    java.awt.event.MouseEvent e
                ) {
                    button.setBackground(PRIMARY_DARK);
                }

                @Override
                public void mouseExited(
                    java.awt.event.MouseEvent e
                ) {
                    button.setBackground(PRIMARY);
                }
            }
        );

        return button;
    }

    public void updateData(
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal result
    ) {
        lblAmount.setText(
            "Сумма: " + amount.toPlainString() + " BYN"
        );

        lblRate.setText(
            "Курс: " + rate.toPlainString() + " BYN / EUR"
        );

        lblResult.setText(
            "Результат: " + result.toPlainString() + " EUR"
        );
    }

    public String showInputDialog(
        BigDecimal amount,
        BigDecimal rate
    ) {
        JPanel panel = new JPanel(
            new GridLayout(4, 1, 0, 5)
        );

        panel.setBorder(new EmptyBorder(
            5, 5, 5, 5
        ));

        JLabel amountLabel =
            new JLabel("Сумма BYN:");

        JTextField amountField =
            new JTextField(amount.toPlainString());

        JLabel rateLabel =
            new JLabel("Курс BYN / EUR:");

        JTextField rateField =
            new JTextField(rate.toPlainString());

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(rateLabel);
        panel.add(rateField);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Ввод данных",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return amountField.getText()
            + " "
            + rateField.getText();
    }

    public JButton getBtnInput() {
        return btnInput;
    }
}

