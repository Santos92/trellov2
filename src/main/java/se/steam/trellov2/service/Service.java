package se.steam.trellov2.service;

import se.steam.trellov2.model.AbstractModel;

import java.util.List;
import java.util.UUID;

public interface Service<T extends AbstractModel<T>> {

    T save(T entity);

    T get(UUID entityId);

    void update(T entity);

    List<T> getAll();
}
