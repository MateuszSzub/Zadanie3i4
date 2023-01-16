package pl.edu.wat.projektspring.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.projektspring.repository.ToolRepository;
import pl.edu.wat.projektspring.repository.CompanyRepository;

@Service
@Slf4j
public class ScriptService {
    private final ToolRepository toolRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public ScriptService(ToolRepository toolRepository, CompanyRepository companyRepository) {
        this.toolRepository = toolRepository;
        this.companyRepository = companyRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("toolRepository", toolRepository);
            bindings.putMember("companyRepository", companyRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}