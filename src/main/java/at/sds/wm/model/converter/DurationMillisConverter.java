package at.sds.wm.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter
public class DurationMillisConverter
        implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration value) {
        return value == null ? null : value.toMillis();
    }

    @Override
    public Duration convertToEntityAttribute(Long value) {
        return value == null ? null : Duration.ofMillis(value);
    }
}
