package pl.edu.wat.projektspring.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.projektspring.service.ScriptService;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PutMapping()
    public ResponseEntity<String> execScript() {
        //@RequestBody String script
        String script = """
                var Tool = Java.type('pl.edu.wat.projektspring.entity.Tool');
                var Company = Java.type('pl.edu.wat.projektspring.entity.Company');
                var Set = Java.type('java.util.Set');
                function pricezl(){
                for(tool of toolRepository.findAll()){
                var toolPrice = tool.getPrice();
                var pricezl = toolPrice + ' zl';
                tool.setPrice(pricezl);   
                toolRepository.save(tool);      
                }  
                return toolRepository.findAll();
                  }
                  pricezl();               
                """;
        String scriptunknown = """
                var Tool = Java.type('pl.edu.wat.projektspring.entity.Tool');
                var Company = Java.type('pl.edu.wat.projektspring.entity.Company');
                var Set = Java.type('java.util.Set');
                function owner(){
                for(company of companyRepository.findAll()){
                var companyOwner = company.getOwner();
                var owner = "Unknown"
                company.setOwner(owner);   
                companyRepository.save(company);      
                }  
                return companyRepository.findAll();
                  }
                  owner();  
                """;
        return new ResponseEntity<>(scriptService.exec(scriptunknown), HttpStatus.OK) ;
    }
}