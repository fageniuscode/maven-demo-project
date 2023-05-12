package com.fageniuscode.mavendemo.service;

import com.fageniuscode.mavendemo.dao.IUserRepository;
import com.fageniuscode.mavendemo.dto.UserDTO;
import com.fageniuscode.mavendemo.exception.RequestException;
import com.fageniuscode.mavendemo.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ibrahima
 *
 */
@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        return StreamSupport.stream(iUserRepository.findAll().spliterator(), false)
                .map(userMapper::toUser)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUsers(Pageable pageable) {
        return iUserRepository.findAll(pageable).map(userMapper::toUser);
    }

    @Transactional(readOnly = true)
    public UserDTO getUser(int id) {
        return userMapper.toUser(iUserRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{id},
                        Locale.getDefault()))));
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.toUser(iUserRepository.save(userMapper.fromUser(userDTO)));
    }

    @Transactional
    public UserDTO updateUser(int id, UserDTO userDTO){
        return iUserRepository.findById(id)
                .map(entity -> {
                    userDTO.setId(id);
                    return userMapper.toUser(iUserRepository.save(userMapper.fromUser(userDTO)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user.notfound",
                        new Object[]{id},
                        Locale.getDefault())));
    }

    @Transactional
    public void deleteUser(int id) {
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("user.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }

}
