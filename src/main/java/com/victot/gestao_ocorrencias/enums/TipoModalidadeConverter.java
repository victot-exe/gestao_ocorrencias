package com.victot.gestao_ocorrencias.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoModalidadeConverter implements AttributeConverter<TipoModalidade, String> {
    @Override
    public String convertToDatabaseColumn(TipoModalidade attribute) {
        return attribute == null ? null : attribute.getCodigo();
    }

    @Override
    public TipoModalidade convertToEntityAttribute(String dbData){
        if(dbData == null) return null;

        return TipoModalidade.doCodigo(dbData);
    }
}
