package org.bigfoot.swingplus.configurable;

import org.bigfoot.swingplus.util.JPGuiUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.bigfoot.swingplus.util.JPClassUtils.getRealClass;

/**
 * @author Jonathan la Roi
 * @since 10/14/2020
 */
public interface JPTranslator {

    default boolean containsKey(String key) {
        return containsKey(key, getRealClass(this.getClass()));
    }

    default boolean containsKey(String key, Class<?> clazz) {
        return JPGuiUtils.containsKey(key, clazz);
    }

    default String translate(String key, String... paramKeys) {
        return translate(key, getRealClass(this.getClass()), paramKeys);
    }

    default String translate(String key, Class<?> clazz, String... paramKeys) {
        if (paramKeys != null && paramKeys.length > 0) {
            return String.format(JPGuiUtils.translate(key, clazz),
                    Arrays.stream(paramKeys)
                            .map(paramKey -> JPGuiUtils.translate(paramKey, clazz))
                            .collect(Collectors.toList()));
        } else {
            return JPGuiUtils.translate(key, clazz);
        }
    }
}