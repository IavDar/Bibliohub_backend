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

import java.util.Collections;


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
        user.setRoles(Collections.singleton(role));
        user = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserData user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + email);
        }
        return user;
    }
}


//    public UserResponseDto registerNewUser(UserRequestDto requestDto) {
//        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
//            throw new IllegalArgumentException("Имеил уже используется");
//        }
//        Users user = new Users();
//        user.setEmail(requestDto.getEmail());
//        user.setPassword(requestDto.getPassword());
//        Role role = roleRepository.findByTitle("ROLE_USER");
//        if (role == null) {
//            role = new Role();
//            role.setTitle("ROLE_USER");
//            role = roleRepository.save(role);
//        }
//        user.setRoles(Collections.singleton(role));
//        user=userRepository.save(user);
//
//        UserResponseDto userResponseDto = new UserResponseDto();
//        userResponseDto.setId(user.getId());
//        userResponseDto.setEmail(user.getEmail());
//        return userResponseDto;
//    }












