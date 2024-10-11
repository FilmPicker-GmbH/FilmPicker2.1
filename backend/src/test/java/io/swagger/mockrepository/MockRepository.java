package io.swagger.mockrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.entity.BaseEntity;

public class MockRepository<T extends BaseEntity<ID>, ID> {
    private List<T> mockDatabase = new ArrayList<T>();

    public T save(T entity) {
        if (existsById(entity.getId())) {
            Optional<T> existingElement = findById(entity.getId());
            if (existingElement.isPresent()) {
                int indexOfElement = mockDatabase.indexOf(existingElement.get());
                mockDatabase.set(indexOfElement, entity);
            }
        } else {
            mockDatabase.add(entity);
            return entity;
        }
        return null;
    }

    public List<T> saveAll(List<T> listOfEntity) {
        mockDatabase = new ArrayList<>(listOfEntity);
        return mockDatabase;
    }

    public Optional<T> findById(ID id) {
        return mockDatabase.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    public boolean existsById(ID id) {
        return findById(id)
                .isPresent();
    }

    public List<T> findAll() {
        return new ArrayList<>(mockDatabase);
    }

    public void deleteById(ID id) {
        mockDatabase.removeIf(entity -> entity.getId().equals(id));
    }

    public void clear() {
        mockDatabase.clear();
    }

    public int size() {
        return mockDatabase.size();
    }
}
