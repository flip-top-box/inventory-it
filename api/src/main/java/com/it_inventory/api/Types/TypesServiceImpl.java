package com.it_inventory.api.Types;

import com.it_inventory.api.Item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypesServiceImpl implements TypesService {

    private final TypesRepository typesRepository;

    @Autowired
    public TypesServiceImpl(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }


    @Override
    public List<Types> getTypes() {
        return typesRepository.findAll();
    }

    @Override
    public Types addType(Types type) {
        return typesRepository.save(type);
    }

    @Override
    public boolean deleteType(Integer id) {
        Optional<Types> optionalType = typesRepository.findById(id);

        if (optionalType.isPresent()) {
            typesRepository.deleteById(id);
        }

        return optionalType.isPresent();
    }
}
