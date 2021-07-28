package org.bigfoot.swingplus.util;

import lombok.experimental.UtilityClass;

/**
 * Helper class voor class gerelateerde meuk
 * Static keyword niet weghalen, is nodig voor method
 *
 * @author Jonathan la Roi
 * @since 20/01/2021
 */
@UtilityClass
public class JPClassUtils {

    public static Class<?> getRealClass(Class<?> clazz) {
        if (clazz == null) {
            return null;
        } else if (clazz.isAnonymousClass()) {
            return clazz.getSuperclass() != null ? clazz.getSuperclass() :
                    clazz.getInterfaces().length > 0 ? clazz.getInterfaces()[0] : clazz;
        } else {
            return clazz;
        }
    }

}
