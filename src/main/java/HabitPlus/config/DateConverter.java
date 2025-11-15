package HabitPlus.config;

import com.github.dozermapper.core.CustomConverter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source,
                          Class<?> destinationClass, Class<?> sourceClass) {

        if (source == null) return null;

        // DTO -> Entity (Date)
        if (source instanceof LocalDate && destinationClass.equals(Date.class)) {
            return Date.from(((LocalDate) source).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        // Entity -> DTO (LocalDate)
        if (source instanceof Date && destinationClass.equals(LocalDate.class)) {
            return ((Date) source).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        return null;
    }
}