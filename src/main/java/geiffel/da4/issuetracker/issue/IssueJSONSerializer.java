package geiffel.da4.issuetracker.issue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import geiffel.da4.issuetracker.Url;

import java.io.IOException;

public class IssueJSONSerializer extends JsonSerializer<Issue> {
    @Override
    public void serialize(Issue issue, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("Titre" , issue.getTitle());
        gen.writeStringField("URL" , new Url().getDomaineName()+"/issues/"+ issue.getCode());
        gen.writeEndObject();
    }
}
