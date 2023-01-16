package pl.edu.wat.projektspring.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.projektspring.dto.CompanyRequest;
import pl.edu.wat.projektspring.dto.CompanyResponse;
import pl.edu.wat.projektspring.entity.Company;
import pl.edu.wat.projektspring.exception.EntityNotFound;
import pl.edu.wat.projektspring.service.CompanyService;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<List<CompanyResponse>> getAllCompany() {
        List<CompanyResponse> companyOptional = companyService.getAll();
        return new ResponseEntity<>(companyOptional, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyResponse> getCompanyByIdVar(@PathVariable String id) {
        Optional<CompanyResponse> companyOptional = companyService.getCompanyById(id);
        if (companyOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyOptional.get(), HttpStatus.OK);
    }

    @GetMapping("{id}/city")
    public ResponseEntity<String> getCompanyCityById(@PathVariable String id) {
        Optional<CompanyResponse> companyOptional = companyService.getCompanyById(id);
        if (companyOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyOptional.get().getCity(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createCompany(@RequestBody CompanyRequest companyRequest) {
        return new ResponseEntity<>(companyService.save(companyRequest).getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable String id, @RequestParam(required = false) String name, @RequestParam(required = false) String city) {
        try {
            return new ResponseEntity<>(companyService.update(id, name, city), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        try {
            companyService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}