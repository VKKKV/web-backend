package com.example.demo.converter;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

//todo
@Mapper()
public interface UserConverter {

//    UserConverter MAPPER = Mappers.getMapper(UserConverter.class);
//
//    // 核心：将 User 实体转 UserDTO
//    @Mappings({
//            @Mapping(target = "username", source = "username"),
//            @Mapping(target = "phone", source = "phone"),
//    })
//    UserDTO toDTO(Users user);

//    // 反向转换
//    @Mappings({
//            @Mapping(target = "username", source = "username"),
//            @Mapping(target = "phone", source = "phone"),
//    })
//    Users toEntity(UserDTO dto);
}


