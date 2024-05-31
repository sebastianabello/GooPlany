package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileOutputMapper {

    ProfileEntity toProfileEntity(Customer customer);

    Customer toProfile(ProfileEntity profileEntity);

    List<Customer> toProfileList(List<ProfileEntity> profileEntities);

    Page<Customer> toProfilePage(Page<ProfileEntity> profileEntityPage);
}
