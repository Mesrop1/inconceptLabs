package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.exception.UserNotFoundException;
import com.mesrop.bookstoreapp.helper.CSVHelper;
import com.mesrop.bookstoreapp.persistance.entity.RoleEntity;
import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import com.mesrop.bookstoreapp.persistance.repository.RoleRepository;
import com.mesrop.bookstoreapp.persistance.repository.UserRepository;
import com.mesrop.bookstoreapp.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> save(MultipartFile file) throws IOException {
        List<UserEntity> users = CSVHelper.parseUsers(file.getInputStream());
        return userRepository.saveAll(users.subList(0, 10000));
    }

    public List<UserEntity> getAllUsers(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<UserEntity> pagedResult = userRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<UserEntity>();
        }
    }

    public UserDto getUser(Long id) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return UserDto.mapUserEntityToUserDto(userEntity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        UserEntity user = userRepository.findByUsername(username);
        RoleEntity role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User {} found in database", user.getUsername());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
