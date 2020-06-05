package com.santander.tesourariaProcedimentos.customDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.santander.tesourariaProcedimentos.entities.Sistemas;
import com.santander.tesourariaProcedimentos.service.SistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonDeserializerSistemas extends JsonDeserializer<Sistemas> {
    
    public JacksonDeserializerSistemas(){
    
    }
    
    @Autowired
    private SistemaService sistemasService;
    
    public Sistemas deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            Sistemas sistemas = new Sistemas();
            sistemas.setSigla(node.asText());
            if(node.asText().isEmpty()) return null;
            return sistemas;
            
        } catch (IOException var4) {
        }
        
        return null;
    }
}
