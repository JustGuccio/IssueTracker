package geiffel.da4.issuetracker.user;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import geiffel.da4.issuetracker.Url;

import java.io.IOException;

public class UserJSONSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("nom", user.getNom());
        gen.writeStringField("url", new Url().getDomaineName()+"/users/"+user.getId());
        gen.writeEndObject();
    }
}
