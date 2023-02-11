package by.korzun.vanilla_console_app.util;

import java.time.LocalDateTime;
import java.time.YearMonth;

public final class DateUtils {
    public static boolean isDateInMonth(LocalDateTime date, YearMonth month) {
        return date.isAfter(month.atDay(1).atStartOfDay()) &&
                date.isBefore(month.plusMonths(1).atDay(1).atStartOfDay());
    }
}
