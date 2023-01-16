package pl.edu.wat.projektspring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.projektspring.entity.Tool;

@Repository
public interface ToolRepository extends MongoRepository<Tool, String> {
}
