package com.example.APIServer.services;

import java.util.List;

public interface ITemplateService<T, S>
{
    List<T> getAll();
    T findById(S s);
    S create(T t);
}
