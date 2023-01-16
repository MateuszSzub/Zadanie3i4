package pl.edu.wat.projektspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyResponse {
    private String id;
    private String name;
    private String city;
}