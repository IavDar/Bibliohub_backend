package org.ac.bibliotheque.user.user_service;

import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.repository.BookRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BookRepository bookRepository;


    public UserResponseDto registerNewUser(UserRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Запрос не может быть null");
        }
        if (!isValidEmail(requestDto.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailIsUsingException("Имеил уже используется");
        }
        if (!isValidPassword(requestDto.getPassword())) {
            throw new PasswordIsNotValid("Пароль должен содержать как минимум одну заглавную букву, одну цифру и один специальный символ");
        }

        UserData user = new UserData();
        user.setEmail(requestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        user.setActive(true);


        Cart cart = new Cart();
        cart.setUserData(user);
        user.setCart(cart);
        String roleTitle = requestDto.getRole();
        if (!roleTitle.equals("ROLE_USER") && !roleTitle.equals("ROLE_LIBRARY")) {
            throw new InvalidRoleException("Недопустимая роль: " + roleTitle);
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
        userResponseDto.setMessage("Вы успешно зарегистрировались");
        return userResponseDto;
    }


    public UserResponseDto deleteUser(UserEmailDto requestDto) {
        if (requestDto == null || requestDto.getEmail() == null || requestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email не может быть null или пустым");
        }
        if (!isValidEmail(requestDto.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        UserData userData = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", requestDto.getEmail())));

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

        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", email.getEmail())));
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        if (userData.getRoles().iterator().next().getTitle().equals("ROLE_ADMIN")) {
            throw new IllegalArgumentException(String.format("Этот пользователь %s уже назначен админом", email.getEmail()));
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
        userResponseDto.setMessage(String.format("Пользователь %s успешно назначен администратором", email.getEmail()));
        return userResponseDto;

    }


    public UserResponseDto blockUser(UserEmailDto email) {
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException("Пользователь не найден"));


        if (!userData.isActive()) {
            throw new UserAlredyIsBlockUnlock(String.format("Этот пользователь %s уже заблокирован", email.getEmail()));
        }
        userData.setActive(false);
        userData = userRepository.save(userData);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());
        userResponseDto.setMessage(String.format("Пользователь %s успешно заблокирован", email.getEmail()));

        return userResponseDto;
    }

    public UserResponseDto unlockUser(UserEmailDto email) {
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", email)));


        if (userData.isActive()) {
            throw new UserAlredyIsBlockUnlock(String.format("Этот пользователь %s не заблокирован", email.getEmail()));
        }
        userData.setActive(true);
        userData = userRepository.save(userData);


        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userData.getId());
        userResponseDto.setEmail(userData.getEmail());
        userResponseDto.setMessage(String.format(String.format("Пользователь %s успешно разблокирован", email.getEmail())));
        return userResponseDto;
    }

    public UserUpdateDto findUsersByEmail(UserEmailDto email) {
        if (!isValidEmail(email.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }

        UserData userData = userRepository.findByEmail(email.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", email)));


        UserUpdateDto userInfo = new UserUpdateDto();
        userInfo.setId(userData.getId());
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
        if (!isValidEmail(updateDto.getEmail())) {
            throw new EmailIsNotValid("Некорректный формат email");
        }
        UserData userData = userRepository.findByEmail(updateDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", updateDto.getEmail())));

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

    @Transactional
    public void addBookToUserCart(Long userId, Long bookId) {
        UserData userData = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден PS Экземпшин от Юзера", userId))
        );
        if (userData.getCity() == null || userData.getCountry() == null || userData.getName() == null || userData.getUsername() == null
                || userData.getStreet() == null || userData.getNumber() == null || userData.getZip() == null || userData.getPhone() == null) {
            throw new UserForbidden("Заполните нужные поля");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new UserNotFoundException("Книга не найдена PS Экземпшин от Юзера"));
        if (book.getAvailable() <= 0) {
            throw new UserForbidden(String.format("Книги %s нет в наличии PS Экземпшин от Юзера", book.getTitle()));
        }
        userData.getCart().addBook(book);
        userRepository.save(userData);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь %s не найден", email)));
    }

    private boolean isValidEmail(String email) {
        // Регулярное выражение для проверки корректности email
        String emailRegex = "^(?!.*\\.\\.)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_*])(?!.*\\s).{8,32}$";
        return password.matches(passwordRegex);
    }
}
















