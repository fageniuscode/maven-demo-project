package com.fageniuscode.mavendemo;
import com.fageniuscode.mavendemo.dao.IUserRepository;
import com.fageniuscode.mavendemo.dto.UserDTO;
import com.fageniuscode.mavendemo.entities.User;
import com.fageniuscode.mavendemo.mapping.UserMapper;
import com.fageniuscode.mavendemo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class MavenDemoProjectApplicationTests {

    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Mocking
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "John", "Doe", "john@example.com", "password", 1));
        userList.add(new User(2, "Jane", "Smith", "jane@example.com", "password", 1));

        when(iUserRepository.findAll()).thenReturn(userList);

        UserDTO userDto1 = new UserDTO(1, "John", "Doe", "john@example.com", "password", 1);
        UserDTO userDto2 = new UserDTO(2, "Jane", "Smith", "jane@example.com", "password", 1);

        when(userMapper.toUser(any(User.class))).thenReturn(userDto1, userDto2);

        // Execution
        List<UserDTO> result = userService.getUsers();

        // Verification
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(userDto1, result.get(0));
        Assertions.assertEquals(userDto2, result.get(1));

        // Verification des appels aux méthodes Mockito
        verify(iUserRepository, times(1)).findAll();
        verify(userMapper, times(2)).toUser(any(User.class));
    }

    @Test
    void testGetUser() {
        // Mocking
        User user = new User(1, "John", "Doe", "john@example.com", "password", 1);
        UserDTO userDto = new UserDTO(1, "John", "Doe", "john@example.com", "password", 1);

        when(iUserRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toUser(user)).thenReturn(userDto);

        // Execution
        UserDTO result = userService.getUser(1);

        // Verification
        Assertions.assertEquals(userDto, result);

        // Verification des appels aux méthodes Mockito
        verify(iUserRepository, times(1)).findById(1);
        verify(userMapper, times(1)).toUser(user);
    }

    @Test
    public void testCreateUser() {
        // given
        UserDTO userDTO = new UserDTO(0, "John", "Doe", "john.doe@example.com", "password", 1);
        User user = new User(userDTO.getId(), userDTO.getNom(), userDTO.getPrenom(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getEtat());
        given(iUserRepository.save(any(User.class))).willReturn(user);

        // when
        UserDTO result = userService.createUser(userDTO);

        // then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getNom()).isEqualTo(user.getNom());
    }

    @Test
    public void testUpdateUser() {
        // given
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "John", "Doe", "john.doe@example.com", "password", 1);
        User user = new User(userDTO.getId(), userDTO.getNom(), userDTO.getPrenom(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getEtat());
        given(iUserRepository.findById(userId)).willReturn(Optional.of(user));
        given(iUserRepository.save(any(User.class))).willReturn(user);

        // when
        UserDTO result = userService.updateUser(userId, userDTO);

        // then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getNom()).isEqualTo(user.getNom());
    }

    @Test
    public void testDeleteUser() {
        // given
        int userId = 1;

        // when
        userService.deleteUser(userId);

        // then
        verify(iUserRepository, times(1)).deleteById(userId);
    }

}
