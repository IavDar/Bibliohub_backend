package org.ac.bibliotheque.user.user_service;

import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.cart.entity.Cart;
import org.ac.bibliotheque.role.Role;
import org.ac.bibliotheque.role.repository.RoleRepository;
import org.ac.bibliotheque.user.dto.UserEmailDto;
import org.ac.bibliotheque.user.exception_handing.Exceptions.*;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.dto.UserUpdateDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.ac.bibliotheque.reservedBooks.entity.ReservedList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public UserResponseDto registerNewUser(UserRequestDto requestDto) {
        if (requestDto.getEmail() == null || requestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("request cannot be null");
        }
        if (!isValidEmail(requestDto.getEmail())) {
            throw new EmailIsNotValid("invalid email format");
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailIsUsingException("email is already in use");
        }
        if (requestDto.getPassword()==null || requestDto.getPassword().isEmpty()){
            throw new IllegalArgumentException("password cannot be null");
        }
        if (!isValidPassword(requestDto.getPassword())) {
            throw new PasswordIsNotValid("The password must be a minimum of 8 characters, contain at least one capital letter, one number and one special character");
        }
        UserData user = new UserData();
        user.setEmail(requestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        user.setActive(true);

        Cart cart = new Cart();
        cart.setUserData(user);
        user.setCart(cart);
        ReservedList wishlist = new ReservedList();
        wishlist.setUserData(user);
        user.setReservedList(wishlist);
        String roleTitle = requestDto.getRole();
        if (!roleTitle.equals("ROLE_USER") && !roleTitle.equals("ROLE_LIBRARY")) {
            throw new InvalidRoleException("invalid role: " + roleTitle);
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
        userResponseDto.setMessage("you have successfully registered");
        return userResponseDto;
    }


    public UserResponseDto deleteUser(UserEmailDto requestDto) {
        if (requestDto == null || requestDto.getEmail() == null || requestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (!isValidEmail(requestDto.getEmail())) {
            throw new EmailIsNotValid("invalid email format");
        }
        UserData userData = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("user %s not found", requestDto.getEmail())));
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


    public UserResponseDto changeRoleOnAdmin(UserEmailDto email) {
        if (email.getEmail() == null || email.getEmail().isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("invalid email format");
        }
        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("user %s not found", email.getEmail())));

        if (userData.getRoles().iterator().next().getTitle().equals("ROLE_ADMIN")) {
            throw new AlreadyAdmin(String.format("this user %s is already assigned as an admin", email.getEmail()));
        }

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
        userResponseDto.setMessage(String.format("User %s has been successfully assigned as an administrator", email.getEmail()));
        return userResponseDto;

    }

    public UserResponseDto blockUser(UserEmailDto email) {

        if (email.getEmail() == null || email.getEmail().isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("invalid email format");
        }
        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException("user not found"));

        if (!userData.isActive()) {
            throw new UserAlredyIsBlockUnlock(String.format("This user %s is already blocked", email.getEmail()));
        }
        userData.setActive(false);
        userData = userRepository.save(userData);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());
        userResponseDto.setMessage(String.format("User %s has been successfully blocked", email.getEmail()));
        return userResponseDto;
    }

    public UserResponseDto unlockUser(UserEmailDto email) {
        if (email.getEmail() == null || email.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Invalid email format");
        }

        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", email)));

        if (userData.isActive()) {
            throw new UserAlredyIsBlockUnlock(String.format("This user %s is not blocked", email.getEmail()));
        }
        userData.setActive(true);
        userData = userRepository.save(userData);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());
        userResponseDto.setMessage(String.format(String.format("User %s successfully unblocked", email.getEmail())));
        return userResponseDto;
    }

    public UserUpdateDto findUsersByEmail(UserEmailDto email) {
        if (email.getEmail() == null || email.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Invalid email format");
        }
        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", email)));

        UserUpdateDto userInfo = new UserUpdateDto();
        userInfo.setEmail(userData.getEmail());
        userInfo.setName(userData.getName());
        userInfo.setSurname(userData.getSurname());
        userInfo.setCountry(userData.getCountry());
        userInfo.setCity(userData.getCity());
        userInfo.setStreet(userData.getStreet());
        userInfo.setNumber(userData.getNumber());
        userInfo.setZip(userData.getZip());
        userInfo.setPhone(userData.getPhone());
        userInfo.setActive(userData.isActive());
        return userInfo;
    }

    public UserData updateUser(UserUpdateDto updateDto) {
        if (updateDto.getEmail() == null || updateDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!isValidEmail(updateDto.getEmail())) {
            throw new EmailIsNotValid("Invalid email format");
        }
        UserData userData = userRepository.findByEmail(updateDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", updateDto.getEmail())));

        userData.setName(updateDto.getName());
        userData.setSurname(updateDto.getSurname());
        userData.setCity(updateDto.getCity());
        userData.setCountry(updateDto.getCountry());
        userData.setStreet(updateDto.getStreet());
        userData.setNumber(updateDto.getNumber());
        userData.setZip(updateDto.getZip());
        userData.setPhone(updateDto.getPhone());
        userData = userRepository.save(userData);
        return userData;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", email)));
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^(?!.*\\.\\.)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!_*])(?!.*\\s).{8,32}$";
        return password.matches(passwordRegex);
    }
}
















