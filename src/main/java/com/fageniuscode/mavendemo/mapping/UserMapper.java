package com.fageniuscode.mavendemo.mapping;
import com.fageniuscode.mavendemo.dto.UserDTO;
import com.fageniuscode.mavendemo.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Ibrahima
 *	Permet de transformer les dto en entités ou vis versa
 */
@Mapper
@Component
public interface UserMapper {
    UserDTO toUser(User user);
    User fromUser(UserDTO userDTO);
}
