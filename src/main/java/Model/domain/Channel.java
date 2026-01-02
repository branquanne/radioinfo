package Model.Domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
    private List<Program> programs;
    private int channelId;
    private String channelName;
    private String tagline;
    private String thumbnailLink;

    public List<Program> getPrograms() {
        return programs;
    }

    @JsonProperty("id")
    public int getChannelId() {
        return channelId;
    }

    @JsonProperty("name")
    public String getChannelName() {
        return channelName;
    }

    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    @JsonProperty("imageurl")
    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
