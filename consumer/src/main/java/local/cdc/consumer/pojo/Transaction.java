package local.cdc.consumer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transaction {
    public String id;
    public long total_order;

    @JsonProperty("data_collection_order")
    public long dataCollectionOrder;
}

