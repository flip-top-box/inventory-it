package com.it_inventory.api.Types;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "api/v1/types")
public class TypesController {

    private static final Logger logger = LoggerFactory.getLogger(TypesController.class);
    private final TypesService typesService;

    public TypesController(TypesService typesService) {this.typesService = typesService;}


    @GetMapping
    public ResponseEntity<List<Types>> getTypes() {
        try {
            List<Types> types = typesService.getTypes();
            return ResponseEntity.ok(types);
        } catch (Exception e){
            logger.error("Error fetching types", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Types> addType(@RequestBody Types type) {
        try{
            Types newType = typesService.addType(type);
            return new ResponseEntity<>(newType, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while trying to add " + type, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Integer id) {
        boolean deleted = typesService.deleteType(id);

        if (deleted) {
            return new ResponseEntity<>("Type deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Type not found", HttpStatus.NOT_FOUND);
        }
    }

}
