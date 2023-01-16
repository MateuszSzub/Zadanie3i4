package pl.edu.wat.projektspring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.projektspring.entity.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
}