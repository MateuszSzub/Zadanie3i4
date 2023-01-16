package pl.edu.wat.projektspring.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.projektspring.dto.CompanyResponse;
import pl.edu.wat.projektspring.dto.ToolRequest;
import pl.edu.wat.projektspring.dto.ToolResponse;
import pl.edu.wat.projektspring.exception.EntityNotFound;
import pl.edu.wat.projektspring.service.ToolService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/tool")
public class ToolController {
    private final ToolService toolService;

    @Autowired
    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping()
    public ResponseEntity<List<ToolResponse>> getAllTool() {
        List<ToolResponse> companyOptional = toolService.getAll();
        return new ResponseEntity<>(companyOptional, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createTool(@RequestBody ToolRequest companyRequest) {
        try {
            return new ResponseEntity<>(toolService.save(companyRequest).getId(), HttpStatus.CREATED);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable String id) {
        try {
            toolService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}