package net.xstopho.screen_test.config;

import java.util.List;
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

    public static class BooleanEntry extends TestConfigEntry<Boolean> {
        public BooleanEntry(Boolean defaultValue, Boolean configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class StringEntry extends TestConfigEntry<String> {
        public StringEntry(String defaultValue, String configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class FloatEntry extends TestConfigEntry<Float> {
        public FloatEntry(Float defaultValue, Float configValue) {
            super(defaultValue, configValue);
        }
    }

    public static class EnumConfigEntry<T extends Enum<T>> extends TestConfigEntry<T> {
        private final Class<T> enumClass;

        public EnumConfigEntry(Class<T> enumClass, T defaultValue, T configValue) {
            super(defaultValue, configValue);
            this.enumClass = enumClass;
        }

        public Class<T> getEnumClass() {
            return enumClass;
        }

        public T[] getEnumValues() {
            return enumClass.getEnumConstants();
        }
    }

    public static class ListConfigEntry extends TestConfigEntry<List<?>> {
        public ListConfigEntry(List<?> defaultValue, List<?> configValue) {
            super(defaultValue, configValue);
        }
    }
}
