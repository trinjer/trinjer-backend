package org.trinjer.controllers.dto.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinjer.domain.UserEntity;
import org.trinjer.controllers.dto.UserDto;
import org.trinjer.controllers.dto.geda.addapters.AdaptersFactory;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;

@Service
public class DtoAssemblerService {

    @Autowired
    private AdaptersFactory adaptersFactory;
    @Autowired
    private BeanFactory beanFactory;

    public UserDto assemble(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        DTOAssembler.newAssembler(UserDto.class, UserEntity.class).assembleDto(userDto, userEntity, adaptersFactory.getAllAdapters(), beanFactory);
        return userDto;
    }
}
