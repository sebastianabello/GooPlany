package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

}
