package net.xstopho.screen_test.config;

import java.util.function.Supplier;

public class TestConfigEntry<T> implements Supplier<T> {

    private final T defaultValue;
    private T configValue;

    public TestConfigEntry(T defaultValue, T configValue) {
        this.defaultValue = defaultValue;
        this.configValue = configValue;
    }

    @Override
    public T get() {
        return this.configValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getConfigValue() {
        return configValue;
    }

    public void setConfigValue(T value) {
        this.configValue = value;
    }

    public static class IntegerEntry extends TestConfigEntry<Integer> {
        public IntegerEntry(Integer defaultValue, Integer configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class DoubleEntry extends TestConfigEntry<Double> {
        public DoubleEntry(Double defaultValue, Double configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class BooleanEntry extends TestConfigEntry<BooleanEntry> {
        public BooleanEntry(BooleanEntry defaultValue, BooleanEntry configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class StringEntry extends TestConfigEntry<String> {
        public StringEntry(String defaultValue, String configValue) {
            super(defaultValue, configValue);
        }
    }
}
