package pl.edu.wat.projektspring.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Company {
    @MongoId
    private String id;
    private String name;
    private String city;
}
