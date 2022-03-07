package org.company.user.service.homework.util;

import org.company.user.service.homework.domain.User;
import org.company.user.service.model.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BeanUtilsTest {

    @Test
    public void test_companyProperties() {
        var user = new User();
        user.setId(1);
        user.setFirstname("ABC");
        user.setSurname("xyz");
        user.setPosition("Boss");
        user.setCreatedOn(OffsetDateTime.now());
        user.setUpdatedOn(OffsetDateTime.now());
        user.setGitHubUrl("xyz.com");

        var userDto = BeanUtils.copyProperties(user, UserDTO.class);
        assertNotNull(userDto);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getFirstname(), user.getFirstname());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getPosition(), user.getPosition());
        assertEquals(userDto.getGitHubUrl(), user.getGitHubUrl());
        assertEquals(userDto.getCreatedOn(), user.getCreatedOn());
        assertEquals(userDto.getUpdatedOn(), user.getUpdatedOn());
    }

    @Test
    public void test_companyProperties_collection() {
        var user = new User();
        user.setId(1);
        user.setFirstname("ABC");
        user.setSurname("xyz");
        user.setPosition("Boss");
        user.setCreatedOn(OffsetDateTime.now());
        user.setUpdatedOn(OffsetDateTime.now());
        user.setGitHubUrl("xyz.com");

        List<UserDTO> userDtos = BeanUtils.copyProperties(List.of(user), UserDTO.class);
        assertNotNull(userDtos);
        assertEquals(userDtos.get(0).getId(), user.getId());
        assertEquals(userDtos.get(0).getFirstname(), user.getFirstname());
        assertEquals(userDtos.get(0).getSurname(), user.getSurname());
        assertEquals(userDtos.get(0).getPosition(), user.getPosition());
        assertEquals(userDtos.get(0).getGitHubUrl(), user.getGitHubUrl());
        assertEquals(userDtos.get(0).getCreatedOn(), user.getCreatedOn());
        assertEquals(userDtos.get(0).getUpdatedOn(), user.getUpdatedOn());
    }

}