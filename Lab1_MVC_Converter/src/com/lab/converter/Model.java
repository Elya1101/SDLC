package com.lab.converter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Model {

    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal rate = BigDecimal.ZERO;
    private BigDecimal result = BigDecimal.ZERO;

    private static final String FILE_NAME = "config.txt";
    private static final int RESULT_SCALE = 2;

    private final PropertyChangeSupport support =
        new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Устанавливает сумму и курс и выполняет
     * один расчёт конвертации.
     *
     * amount — сумма в BYN.
     * rate — сколько BYN стоит 1 EUR.
     */
    public void setValues(
        BigDecimal amount,
        BigDecimal rate
    ) {
        if (amount == null || rate == null) {
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0
            || rate.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        BigDecimal oldAmount = this.amount;
        BigDecimal oldRate = this.rate;
        BigDecimal oldResult = this.result;

        this.amount = amount;
        this.rate = rate;

        result = amount.divide(
            rate,
            RESULT_SCALE,
            RoundingMode.HALF_UP
        );

        support.firePropertyChange(
            "amount",
            oldAmount,
            this.amount
        );

        support.firePropertyChange(
            "rate",
            oldRate,
            this.rate
        );

        support.firePropertyChange(
            "result",
            oldResult,
            this.result
        );
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void saveLastValues() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.println(amount.toPlainString());
            writer.println(rate.toPlainString());
        } catch (IOException e) {
            System.err.println(
                "Ошибка сохранения данных: "
                    + e.getMessage()
            );
        }
    }

    public void loadLastValues() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                 new BufferedReader(
                     new FileReader(file)
                 )) {

            String amountLine = reader.readLine();
            String rateLine = reader.readLine();

            if (amountLine == null || rateLine == null) {
                return;
            }

            BigDecimal loadedAmount =
                new BigDecimal(amountLine.trim());

            BigDecimal loadedRate =
                new BigDecimal(rateLine.trim());

            if (loadedAmount.compareTo(BigDecimal.ZERO) > 0
                && loadedRate.compareTo(BigDecimal.ZERO) > 0) {

                amount = loadedAmount;
                rate = loadedRate;

                result = amount.divide(
                    rate,
                    RESULT_SCALE,
                    RoundingMode.HALF_UP
                );
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(
                "Ошибка чтения конфигурации: "
                    + e.getMessage()
            );
        }
    }
}