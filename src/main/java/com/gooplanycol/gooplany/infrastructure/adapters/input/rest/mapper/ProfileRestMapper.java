package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.ProfileCreateRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileRestMapper {
    Profile toProfile(ProfileCreateRequest request);
    ProfileResponse toProfileResponse(Profile profile);
    List<ProfileResponse> toProfileResponseList(List<Profile> profiles);
}
