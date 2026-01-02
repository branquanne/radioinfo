package Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Domain.Channel;
import Model.Domain.Program;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class Parser {
    private final String json;
    private final ObjectMapper mapper = new ObjectMapper();

    public Parser(HttpResponse<String> response) {
        this.json = response.body();
    }

    public List<Channel> parseChannels() {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode channelIsNode = root.path("channels");
            if (channelIsNode.isMissingNode() || !channelIsNode.isArray()) {
                return Collections.emptyList();
            }
            return mapper.convertValue(channelIsNode, new TypeReference<List<Channel>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Program> parsePrograms() {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode programsNode = root.path("schedule");
            if (programsNode.isMissingNode() || !programsNode.isArray()) {
                return Collections.emptyList();
            }
            return mapper.convertValue(programsNode, new TypeReference<List<Program>>() {
            });

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
