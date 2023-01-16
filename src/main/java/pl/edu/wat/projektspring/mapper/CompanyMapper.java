package pl.edu.wat.projektspring.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.projektspring.dto.CompanyRequest;
import pl.edu.wat.projektspring.dto.CompanyResponse;
import pl.edu.wat.projektspring.entity.Company;

@Service
public class CompanyMapper {


    public Company map(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setCity(companyRequest.getCity());
        fillCompanyRequest(company, companyRequest);
        return company;
    }

    private void fillCompanyRequest(Company company, CompanyRequest companyRequest) {
        // empty for byte buddy
    }

    public CompanyResponse map(Company company) {
        CompanyResponse companyResponse = new CompanyResponse(company.getId(), company.getName(), company.getCity());
        fillCompany(companyResponse, company);
        return companyResponse;
    }

    private void fillCompany(CompanyResponse companyResponse, Company company) {
        // empty for byte buddy
    }
}