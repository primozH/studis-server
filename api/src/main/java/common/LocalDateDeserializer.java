package common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken().equals(JsonToken.VALUE_STRING)) {
            return LocalDate.parse(jsonParser.getText());
        } throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "Expected string");
    }
}
