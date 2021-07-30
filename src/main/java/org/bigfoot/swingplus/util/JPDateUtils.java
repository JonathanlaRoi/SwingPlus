package org.bigfoot.swingplus.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Jonathan la Roi
 * @since 30/07/2021
 */
@UtilityClass
public class JPDateUtils {

    @Nullable
    public static LocalDate mapUtilDateToLocalDate(@Nullable Date dateToConvert) {
        if (dateToConvert == null)
            return null;
        //We gebruiken de default ZoneId want die value pleuren we hierna weer weg wanneer het een local date wordt
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Nullable
    public static LocalDateTime mapUtilDateToLocalDateTime(@Nullable Date dateToConvert) {
        if (dateToConvert == null)
            return null;
        //We gebruiken de default ZoneId want die value pleuren we hierna weer weg wanneer het een local date time wordt
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Nullable
    public static Date mapLocalDateToUtilDate(@Nullable LocalDate dateToConvert) {
        if (dateToConvert == null)
            return null;
        return java.sql.Date.valueOf(dateToConvert);
    }

    @Nullable
    public static Date mapLocalDateTimeToUtilDate(@Nullable LocalDateTime dateToConvert) {
        if (dateToConvert == null)
            return null;
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

}
