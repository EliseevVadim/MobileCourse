package com.example.lab6.core.repositories;

import java.util.ArrayList;

public interface iRepository<T> {
    ArrayList<T> getAll();
    T getById(int id);
    void create(T element);
    void update(T element);
    void delete(int id);
}
