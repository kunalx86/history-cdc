package local.cdc.consumer.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Field {
    public String type;
    public ArrayList<Field> fields;
    public boolean optional;
    public String name;
    public String field;
    public int version;

    @JsonProperty("default") 
    public Object mydefault;

    public Parameters parameters;
}
