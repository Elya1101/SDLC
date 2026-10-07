package com.lab.converter;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.err.println(
                "Не удалось применить системный стиль: "
                    + e.getMessage()
            );
        }

        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Controller controller = new Controller(model, view);

            // Восстанавливаем последние сохранённые данные.
            controller.init();

            view.setVisible(true);
        });
    }
}