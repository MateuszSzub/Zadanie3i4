package pl.edu.wat.projektspring.reflection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FieldInformation {

    private String fieldName;
    private String fieldType;

    public FieldInformation() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(new File("fields.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.fieldName = jsonNode.get("fieldName").asText();
        this.fieldType = jsonNode.get("fieldType").asText();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
}