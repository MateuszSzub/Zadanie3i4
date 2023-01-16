package pl.edu.wat.projektspring.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Tool {
    @MongoId
    private String id;
    private String name;
    private String price;
    private Double count;
    private Integer rating;
    private String companyId;
}