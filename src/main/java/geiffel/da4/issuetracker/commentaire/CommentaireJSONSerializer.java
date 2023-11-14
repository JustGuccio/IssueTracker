package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import geiffel.da4.issuetracker.Url;

import java.io.IOException;

public class CommentaireJSONSerializer extends JsonSerializer<Commentaire> {
    @Override
    public void serialize(Commentaire commentaire, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("Id : ", commentaire.getId().toString());
        gen.writeStringField("URL" , new Url().getDomaineName()+"/commentaires/"+commentaire.getId());
        gen.writeEndObject();
    }
}
