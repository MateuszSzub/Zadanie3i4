package pl.edu.wat.projektspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToolResponse {
    private String id;
    private String name;
    private String price;
    private Double count;
    private Integer rating;
    private CompanyResponse company;
}