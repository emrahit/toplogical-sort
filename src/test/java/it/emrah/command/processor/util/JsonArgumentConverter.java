package it.emrah.command.processor.util;

import java.io.IOException;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonArgumentConverter implements ArgumentConverter {

    private ObjectMapper mapper;

    public JsonArgumentConverter() {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.findAndRegisterModules();
    }

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        try {
            return mapper.readValue((String) source, context.getParameter().getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
