package local.cdc.consumer.pojo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Schema {
    public String type;
    public ArrayList<Field> fields;
    public boolean optional;
    public String name;
    public int version;
}