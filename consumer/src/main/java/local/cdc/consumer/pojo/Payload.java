package local.cdc.consumer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Payload<T extends AbstractData> {
    public T before;
    public T after;
    public Source source;
    public String op;
    
    @JsonProperty("ts_ms")
    public Long tsMs;

    @JsonProperty("ts_us")
    public Long tsUs;

    @JsonProperty("ts_ns")
    public Long tsNs;

    public Transaction transaction;
}
