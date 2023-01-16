package pl.edu.wat.projektspring.dto;

import lombok.Data;

@Data
public class ToolRequest {
    private String name;
    private String price;
    private Double count;
    private String companyId;
}