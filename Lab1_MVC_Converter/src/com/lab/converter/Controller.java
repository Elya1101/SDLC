package com.lab.converter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class Controller implements PropertyChangeListener {

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        model.addListener(this);

        view.getBtnInput().addActionListener(
            e -> handleInput()
        );
    }

    public void init() {
        model.loadLastValues();

        view.updateData(
            model.getAmount(),
            model.getRate(),
            model.getResult()
        );
    }

    private void handleInput() {

        String input = view.showInputDialog(
            model.getAmount(),
            model.getRate()
        );

        if (input == null) {
            return;
        }

        input = input.trim();

        if (input.isEmpty()) {
            showError("Введите сумму и курс.");
            return;
        }

        try {
            String normalized = input.replace(";", " ");

            String[] parts = normalized
                .trim()
                .split("\\s+");

            if (parts.length != 2) {
                throw new IllegalArgumentException(
                    "Необходимо ввести сумму BYN и курс EUR."
                );
            }

            BigDecimal amount = parseNumber(parts[0]);
            BigDecimal rate = parseNumber(parts[1]);

            validate(amount, rate);

            // Один вызов — один расчёт.
            model.setValues(amount, rate);

            model.saveLastValues();

        } catch (NumberFormatException e) {
            showError(
                "Некорректный формат числа.\n"
                    + "Например: 154 3,50"
            );

        } catch (IllegalArgumentException e) {
            showError(e.getMessage());

        } catch (Exception e) {
            showError(
                "Произошла ошибка при обработке данных."
            );
        }
    }

    private BigDecimal parseNumber(String value) {

        String normalized = value
            .trim()
            .replace(',', '.');

        return new BigDecimal(normalized);
    }

    private void validate(
        BigDecimal amount,
        BigDecimal rate
    ) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Сумма BYN должна быть больше нуля."
            );
        }

        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Курс EUR должен быть больше нуля."
            );
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Ошибка ввода",
            JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        view.updateData(
            model.getAmount(),
            model.getRate(),
            model.getResult()
        );
    }
}