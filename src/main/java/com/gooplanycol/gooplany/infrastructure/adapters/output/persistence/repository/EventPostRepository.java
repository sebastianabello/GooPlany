package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPostRepository extends JpaRepository<EventPostEntity, Long>{
}
