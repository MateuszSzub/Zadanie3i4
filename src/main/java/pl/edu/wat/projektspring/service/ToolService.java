package pl.edu.wat.projektspring.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.projektspring.dto.ToolRequest;
import pl.edu.wat.projektspring.dto.ToolResponse;
import pl.edu.wat.projektspring.dto.CompanyResponse;
import pl.edu.wat.projektspring.entity.Tool;
import pl.edu.wat.projektspring.entity.Company;
import pl.edu.wat.projektspring.exception.EntityNotFound;
import pl.edu.wat.projektspring.repository.ToolRepository;
import pl.edu.wat.projektspring.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ToolService {
    private final ToolRepository toolRepository;
    private final RatingService ratingService;
    private final CompanyRepository companyRepository;

    @Autowired
    public ToolService(ToolRepository toolRepository, RatingService ratingService, CompanyRepository companyRepository) {
        this.toolRepository = toolRepository;
        this.ratingService = ratingService;
        this.companyRepository = companyRepository;

    }

    public ToolResponse getToolById(String id) throws EntityNotFound {
        Tool tool = toolRepository.findById(id).orElseThrow(EntityNotFound::new);

        Company company = companyRepository.findById(tool.getCompanyId()).orElseThrow(EntityNotFound::new);

        return new ToolResponse(
                tool.getId(),
                tool.getName(),
                tool.getPrice(),
                tool.getCount(),
                tool.getRating(),
                new CompanyResponse(company.getId(), company.getName(), company.getCity()));
    }

    public ToolResponse save(ToolRequest toolRequest) throws EntityNotFound {
        Tool tool = new Tool();
        tool.setName(toolRequest.getName());
        tool.setPrice(toolRequest.getPrice());
        tool.setCount(toolRequest.getCount());
        tool.setRating(ratingService.getRating(tool));

        Company company = companyRepository.findById(toolRequest.getCompanyId())
                .orElseThrow(EntityNotFound::new);

        tool.setCompanyId(company.getId());
        tool = toolRepository.save(
                tool
        );
        System.out.println(tool);
        return new ToolResponse(
                tool.getId(),
                tool.getName(),
                tool.getPrice(),
                tool.getCount(),
                tool.getRating(),
                new CompanyResponse(company.getId(), company.getName(), company.getCity()));
    }

    public List<ToolResponse> getAll() {

        return toolRepository.findAll()
                .stream()
                .map(this::toToolResponse)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<ToolResponse> toToolResponse(Tool tool) {
        try {
            Company company = companyRepository.findById(tool.getCompanyId()).orElseThrow(EntityNotFound::new);
            return Optional.of(
                    new ToolResponse(tool.getId(), tool.getName(), tool.getPrice(), tool.getCount(), tool.getRating(), new CompanyResponse(company.getId(), company.getName(), company.getCity()))
            );
        } catch (EntityNotFound e) {
            return Optional.empty();
        }
    }

    public void deleteById(String id) throws EntityNotFound {
        toolRepository.findById(id)
                .orElseThrow(EntityNotFound::new);
        toolRepository.deleteById(id);
    }



}