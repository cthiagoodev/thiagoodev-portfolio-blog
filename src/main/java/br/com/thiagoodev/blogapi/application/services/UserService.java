package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.entities.User;

public interface UserService {
    User getByUuid(String uuid);
    User getByEmail(String email);
    User getByPhone(String phone);
    User create(User newUser);
    User update(User user);
    boolean delete(String uuid);
    User verify(String uuid);
}
