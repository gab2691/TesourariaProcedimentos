package com.santander.tesourariaProcedimentos.customDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.santander.tesourariaProcedimentos.entities.Imagem;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonDeserializerImagem  extends JsonDeserializer<Imagem> {
    
    public JacksonDeserializerImagem(){}
    
    public Imagem deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            Imagem imagem = new Imagem();
            imagem.setNomeImagem(node.asText());
            if(node.asText().isEmpty()) return null;
            return imagem;
            
        } catch (IOException var4) {
        }
        
        return null;
    }
}
