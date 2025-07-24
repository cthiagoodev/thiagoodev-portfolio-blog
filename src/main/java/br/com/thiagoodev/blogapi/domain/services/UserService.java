package br.com.thiagoodev.blogapi.domain.services;

import br.com.thiagoodev.blogapi.domain.entities.User;

public interface UserService {
    User getByUuid(String uuid);
    User getByEmail(String email);
    User create(User newUser);
    User update(User newUser);
    boolean delete();
}
