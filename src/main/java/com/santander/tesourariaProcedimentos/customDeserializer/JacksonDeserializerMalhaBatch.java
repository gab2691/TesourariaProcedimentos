package com.santander.tesourariaProcedimentos.customDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.santander.tesourariaProcedimentos.entities.MalhaBatch;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonDeserializerMalhaBatch extends JsonDeserializer<MalhaBatch> {
    
    public JacksonDeserializerMalhaBatch(){
    
    }
    
    public MalhaBatch deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            MalhaBatch malhaBatch = new MalhaBatch();
            malhaBatch.setNomeJob(node.asText());
            if(node.asText().isEmpty()) return null;
            return malhaBatch;
            
        } catch (IOException var4) {
        }
        
        return null;
    }
    
}
