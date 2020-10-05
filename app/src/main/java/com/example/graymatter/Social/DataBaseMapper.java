package com.example.graymatter.Social;

import java.util.List;
import java.util.Optional;

public class DataBaseMapper implements DataMapper<DataBaseModel> {
    @Override
    public Optional<DataBaseModel> find(int w8m8) {
        return Optional.empty();
    }

    @Override
    public void delete(DataBaseModel obj) throws DataMapperException {

    }

    @Override
    public void update(DataBaseModel obj) throws DataMapperException {

    }

    @Override
    public void insert(DataBaseModel obj) {

    }

    @Override
    public List<DataBaseModel> get() {
        return null;
    }
}
