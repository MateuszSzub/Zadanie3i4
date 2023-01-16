package pl.edu.wat.projektspring.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.projektspring.dto.CompanyRequest;
import pl.edu.wat.projektspring.dto.CompanyResponse;
import pl.edu.wat.projektspring.entity.Company;
import pl.edu.wat.projektspring.exception.EntityNotFound;
import pl.edu.wat.projektspring.mapper.CompanyMapper;
import pl.edu.wat.projektspring.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Optional<CompanyResponse> getCompanyById(String id) {
        return companyRepository.findById(id)
                .map(companyMapper::map);
    }

    public CompanyResponse save(CompanyRequest companyRequest) {
        Company company = companyMapper.map(companyRequest);
        company = companyRepository.save(
                company
        );
        return companyMapper.map(company);
    }

    public List<CompanyResponse> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::map)
                .toList();
    }

    public CompanyResponse update(String id, String name, String city) throws EntityNotFound {
        Company company = companyRepository.findById(id).orElseThrow(EntityNotFound::new);
        if(StringUtils.isNotBlank(name)) {
            company.setName(name);
        }

        if(StringUtils.isNotBlank(city)) {
            company.setCity(city);
        }

        company = companyRepository.save(company);
        return companyMapper.map(company);
    }

    public void deleteById(String id) throws EntityNotFound {
        if (!companyRepository.existsById(id)) {
            throw new EntityNotFound();
        }
        companyRepository.deleteById(id);

    }
}