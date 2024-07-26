package org.ac.bibliotheque.user.user_service;

import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.role.Role;
import org.ac.bibliotheque.role.repository.RoleRepository;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.entity.Users;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public UserResponseDto registerNewUser(UserRequestDto requestDto) {
//        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
//            throw new IllegalArgumentException("Имеил уже используется");
//        }
        Users user = new Users();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        Role role = roleRepository.findByTitle("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setTitle("CUSTOMER");
            role = roleRepository.save(role);
        }
        user.setRoles(Collections.singleton(role));
        user=userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

}
