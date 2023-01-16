package pl.edu.wat.projektspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.projektspring.reflection.FieldInformation;
import pl.edu.wat.projektspring.reflection.Reflection;

@SpringBootApplication
public class ProjektspringApplication {

    public static void main(String[] args) {
        FieldInformation fieldInformation = new FieldInformation();
        Reflection.apply(fieldInformation.getFieldName(),fieldInformation.getFieldType());
        SpringApplication.run(ProjektspringApplication.class, args);
    }

}
