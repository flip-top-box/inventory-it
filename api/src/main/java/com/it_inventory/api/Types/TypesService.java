package com.it_inventory.api.Types;

import java.util.List;

public interface TypesService {

    List<Types> getTypes();

    Types addType(Types type);

    boolean deleteType(Integer id);
}
