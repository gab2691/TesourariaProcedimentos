package com.santander.tesourariaProcedimentos.customDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.santander.tesourariaProcedimentos.entities.PalavraChave;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonDeserializerPalavraChave extends JsonDeserializer<PalavraChave> {
    
    public JacksonDeserializerPalavraChave(){
    
    }
    
    @Override
    public PalavraChave deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            PalavraChave palavraChave = new PalavraChave();
            palavraChave.setChave(node.asText());
            if(node.asText().isEmpty()) return null;
            return palavraChave;
        
        } catch (IOException var4) {
        }
    
        return null;
    }
}
