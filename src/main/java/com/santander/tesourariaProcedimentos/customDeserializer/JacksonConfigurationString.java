package com.santander.tesourariaProcedimentos.customDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonConfigurationString extends JsonDeserializer<String> {
    public JacksonConfigurationString() {
    }
    
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            if(jsonParser.getText().isEmpty()) return null;
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        
        return jsonParser.getText().toString();
    }
}