package local.cdc.consumer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Source {
    public String version;
    public String connector;
    public String name;

    @JsonProperty("ts_ms")
    public long tsMs;

    public String snapshot;
    public String db;
    public String sequence;

    @JsonProperty("ts_us")
    public Long tsUs;

    @JsonProperty("ts_ns")
    public Long tsNs;

    public String schema;
    public String table;

    public Long txId;
    public Long lsn;
    public Long xmin;
}

