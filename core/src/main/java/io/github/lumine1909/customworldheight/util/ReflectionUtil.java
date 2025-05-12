package io.github.lumine1909.customworldheight.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.lang.reflect.Field;

public class ReflectionUtil {

    private static final Cache<String, Field> FIELD_CACHE = CacheBuilder.newBuilder().build();

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<?> clazz, String fieldName, Object instance) {
        try {
            String cacheKey = clazz.getName() + "." + fieldName;
            Field field;
            if ((field = FIELD_CACHE.getIfPresent(cacheKey)) == null) {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                FIELD_CACHE.put(cacheKey, field);
            }
            return (T) field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Class<?> clazz, String fieldName, Object instance, Object value) {
        try {
            String cacheKey = clazz.getName() + "." + fieldName;
            Field field;
            if ((field = FIELD_CACHE.getIfPresent(cacheKey)) == null) {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                FIELD_CACHE.put(cacheKey, field);
            }
            field.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
