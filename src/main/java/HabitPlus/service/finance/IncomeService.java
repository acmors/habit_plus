package HabitPlus.service.finance;
import HabitPlus.DTO.finance.IncomeRequest;
import HabitPlus.DTO.finance.IncomeResponse;
import HabitPlus.controllers.finance.IncomeController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ConflictException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.finance.IncomeEntity;
import HabitPlus.repository.finance.IncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    private Logger logger = LoggerFactory.getLogger(IncomeService.class.getName());

    public IncomeResponse create(IncomeRequest request){

        logger.info("Creating a Income!");
        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        IncomeEntity newIncome = new IncomeEntity();
        newIncome.setname(request.name());
        newIncome.setdescription(request.description());
        newIncome.setcategory(request.category());
        newIncome.setvalue(request.value());
        if (repository.existsByName(newIncome.getname())) throw new ConflictException("Income already exists");

        IncomeEntity savedIncome = repository.save(newIncome);
        IncomeResponse response = convertToResponse(savedIncome);

        addHateoasLinks(response);
        return response;
    }

    public IncomeResponse update(Long id, IncomeRequest request){

        logger.info("Updating a Income!");
        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Income name cannot be empty");
        }

        IncomeEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Income not found"));
        entity.setname(request.name());
        entity.setdescription(request.description());
        entity.setcategory(request.category());
        entity.setvalue(request.value());

        IncomeEntity updatedIncome = repository.save(entity);
        IncomeResponse response = convertToResponse(updatedIncome);

        addHateoasLinks(response);
        return response;
    }

    public IncomeResponse findById(Long id){

        logger.info("Finding a Income!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Income not found"));

        IncomeResponse response = convertToResponse(entity);
        addHateoasLinks(response);
        return response;
    }

    public List<IncomeResponse> findAll(){

        logger.info("Finding all Incomes!");

        List<IncomeEntity> incomes = repository.findAll();
        List<IncomeResponse> responses = incomes.stream()
                        .map(this::convertToResponse)
                        .toList();
        responses.forEach(this::addHateoasLinks);
        return responses;
    }
    
    public void delete(Long id){

        logger.info("Deleting a Income!");

        IncomeEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Income not found"));

        repository.delete(entity);
    }

    private void addHateoasLinks(IncomeResponse dto) {
        dto.add(linkTo(methodOn(IncomeController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(IncomeController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(IncomeController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(IncomeController.class).create(null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(IncomeController.class).update(dto.getId(), null)).withRel("update").withType("PUT"));
    }

    //DTO conversion method
    private IncomeResponse convertToResponse(IncomeEntity entity){
        return new IncomeResponse(
                entity.getId(),
                entity.getname(),
                entity.getcategory(),
                entity.getdescription(),
                entity.getvalue()
        );
    }
}
