package org.trinjer.controllers.dto.assemblers;

import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinjer.domain.UserEntity;
import org.trinjer.controllers.dto.UserDto;
import org.trinjer.controllers.dto.geda.addapters.AdaptersFactory;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;

@Service
public class DtoAssemblerService {

    private final AdaptersFactory adaptersFactory;
    private final BeanFactory beanFactory;
    private final Assembler userAssembler;

    @Autowired
    public DtoAssemblerService(AdaptersFactory adaptersFactory, BeanFactory beanFactory) {
        this.adaptersFactory = adaptersFactory;
        this.beanFactory = beanFactory;
        this.userAssembler = DTOAssembler.newAssembler(UserDto.class, UserEntity.class, UserEntity.class.getClassLoader());
    }

    public UserDto assemble(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userAssembler.assembleDto(userDto, userEntity, adaptersFactory.getAllAdapters(), beanFactory);
        return userDto;
    }
}
