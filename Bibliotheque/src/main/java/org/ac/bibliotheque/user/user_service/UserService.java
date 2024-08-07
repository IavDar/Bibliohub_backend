package org.ac.bibliotheque.user.user_service;

import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.role.Role;
import org.ac.bibliotheque.role.repository.RoleRepository;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public UserResponseDto registerNewUser(UserRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new IllegalArgumentException("Имеил уже используется");
        }
        UserData user = new UserData();
        user.setEmail(requestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        String roleTitle = requestDto.getRole();
        if (!roleTitle.equals("ROLE_USER") && !roleTitle.equals("ROLE_LIBRARY")) {
            throw new IllegalArgumentException("Недопустимая роль: " + roleTitle);
        }

        Role role = roleRepository.findByTitle(roleTitle);
        if (role == null) {
            role = new Role();
            role.setTitle(roleTitle);
            role = roleRepository.save(role);
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(role));
        user = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }


    public UserResponseDto deleteUser(UserRequestDto requestDto) {
        UserData userData = userRepository.findByEmail(requestDto.getEmail());

        if (userData == null) {
            throw new IllegalArgumentException("Такого пользователя не существует");
        }
        userData.getRoles().clear();
        userRepository.save(userData);
        userRepository.delete(userData);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());

        return userResponseDto;
    }

    public List<UserData> getAllUsers() {
        return userRepository.findAll();
    }


    public UserResponseDto changeRoleOnAdmin(UserRequestDto requestDto) {

        UserData userData = userRepository.findByEmail(requestDto.getEmail());

        String roleTitle = "ROLE_ADMIN";

        Role role = roleRepository.findByTitle(roleTitle);
        if (role == null) {
            role = new Role();
            role.setTitle(roleTitle);
            role = roleRepository.save(role);
        }
        userData.getRoles().clear();
        userData.getRoles().add(role);
        userData = userRepository.save(userData);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());
        return userResponseDto;

    }


    public UserResponseDto blockUser(UserRequestDto requestDto){
        UserData userData = userRepository.findByEmail(requestDto.getEmail());
        if (userData == null) {
            throw new IllegalArgumentException("пользователь не найден " + requestDto.getEmail());
        }
        userData.setActive(false);
        userData = userRepository.save(userData);


        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());

        return userResponseDto;
    }

    public UserResponseDto unlockUser(UserRequestDto requestDto){
        UserData userData = userRepository.findByEmail(requestDto.getEmail());
        if (userData == null) {
            throw new IllegalArgumentException("пользователь не найден " + requestDto.getEmail());
        }
        userData.setActive(true);
        userData = userRepository.save(userData);


        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());

        return userResponseDto;
    }

    public UserResponseDto findUsersByEmail(String email){

        UserData userData = userRepository.findByEmail(email);
        userData = userRepository.save(userData);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(userData.getEmail());
        userResponseDto.setId(userData.getId());
        return  userResponseDto;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserData user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден" + email);
        }
        return user;
    }
}
















