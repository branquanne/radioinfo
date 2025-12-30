package Model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
    private List<Program> programs;
    private int channelId;
    private String channelName;
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

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}
