package org.trinjer.controllers.dto;

import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;

import lombok.Data;

@Data
@Dto
public class UserDto {
    @DtoField
    private int id;
    @DtoField
    private String firstName;
    @DtoField
    private String lastName;
    @DtoField
    private String email;
}
