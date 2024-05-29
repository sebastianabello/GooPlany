package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileOutputMapper {

    ProfileEntity toProfileEntity(Profile profile);

    Profile toProfile(ProfileEntity profileEntity);

    List<Profile> toProfileList(List<ProfileEntity> profileEntities);

    Optional<Profile> toProfileOptional(Profile profile);

    Page<Profile> toProfilePage(Page<ProfileEntity> profileEntityPage);
}
