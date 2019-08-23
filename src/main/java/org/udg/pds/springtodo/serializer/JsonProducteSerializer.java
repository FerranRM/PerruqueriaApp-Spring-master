package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.entity.Views;

import java.io.IOException;


public class JsonProducteSerializer extends JsonSerializer<Producte> {

  @Override
  public void serialize(Producte producte, JsonGenerator gen, SerializerProvider provider)
          throws IOException, JsonProcessingException {
    if (provider.getActiveView() == Views.Complete.class)
      gen.writeString(producte.getDescripcioProducte());
    else {
      gen.writeStartObject();
      gen.writeNumberField("id", producte.getId());
      gen.writeNumberField("preuProducte", producte.getPreuProducte());
      gen.writeStringField("descripcioProducte", producte.getDescripcioProducte());
      gen.writeEndObject();
    }
  }
}
