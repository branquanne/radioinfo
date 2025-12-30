package Model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {
    @JsonProperty("program")
    private ProgramRef program;

    @JsonProperty("channel")
    private ChannelRef channel;

    @JsonProperty("episodeid")
    private int episodeId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @JsonProperty("imageurl")
    private String thumbnailLink;


    @JsonProperty("id")
    public int getProgramId() {
        return program.id;
    }

    @JsonProperty("channelId")
    public int getChannelId() {
        return channel.getId();
    }

    public String getProgramTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public static class ProgramRef {
        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class ChannelRef {
        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
