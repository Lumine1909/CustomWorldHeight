package io.github.lumine1909.customworldheight.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.lumine1909.reflexion.Field;

@SuppressWarnings("unchecked, rawtypes")
public class ReflectionUtil {

    private static final Cache<String, Field> FIELD_CACHE = CacheBuilder.newBuilder().build();

    public static <T> T get(Class<?> clazz, String fieldName, Object instance) {
        String cacheKey = clazz.getName() + "." + fieldName;
        Field field;
        if ((field = FIELD_CACHE.getIfPresent(cacheKey)) == null) {
            field = Field.of(clazz, fieldName);
            FIELD_CACHE.put(cacheKey, field);
        }
        return (T) field.get(instance);
    }

    public static void set(Class<?> clazz, String fieldName, Object instance, Object value) {
        String cacheKey = clazz.getName() + "." + fieldName;
        Field field;
        if ((field = FIELD_CACHE.getIfPresent(cacheKey)) == null) {
            field = Field.of(clazz, fieldName);
            FIELD_CACHE.put(cacheKey, field);
        }
        field.set(instance, value);
    }
}