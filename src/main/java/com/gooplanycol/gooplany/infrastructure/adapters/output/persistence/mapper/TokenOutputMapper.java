package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenOutputMapper {
    TokenEntity toTokenEntity(Token token);

    Token toToken(TokenEntity tokenEntity);

    List<Token> toTokenList(List<TokenEntity> tokenEntities);

    List<TokenEntity> toTokenEntityList(List<Token> tokens);
}
