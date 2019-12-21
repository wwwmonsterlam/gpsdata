package com.william.gpsdata.service;

import com.william.gpsdata.dto.DataDto;
import com.william.gpsdata.dto.UserDto;
import com.william.gpsdata.po.Data;
import com.william.gpsdata.po.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface BeanConverter {

    static Data toData(DataDto source) {
        Mapper mapper = new DozerBeanMapper();
        return (Data) mapper.map(source, Data.class);
    }

    static DataDto toDataDto(Data source) {
        Mapper mapper = new DozerBeanMapper();
        return (DataDto) mapper.map(source, DataDto.class);
    }

    static List<Data> toData(List<DataDto> source) {
        return source.stream().map(BeanConverter::toData).collect(Collectors.toList());
    }

    static List<DataDto> toDataDto(List<Data> source) {
        return source.stream().map(BeanConverter::toDataDto).collect(Collectors.toList());
    }

    static User toUser(UserDto source) {
        Mapper mapper = new DozerBeanMapper();
        return (User) mapper.map(source, User.class);
    }

    static UserDto toUserDto(User source) {
        Mapper mapper = new DozerBeanMapper();
        return (UserDto) mapper.map(source, UserDto.class);
    }

    static List<User> toUser(List<UserDto> source) {
        return source.stream().map(BeanConverter::toUser).collect(Collectors.toList());
    }

    static List<UserDto> toUserDto(List<User> source) {
        return source.stream().map(BeanConverter::toUserDto).collect(Collectors.toList());
    }
}
