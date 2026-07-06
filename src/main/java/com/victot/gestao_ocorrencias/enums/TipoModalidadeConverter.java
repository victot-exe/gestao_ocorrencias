package com.victot.gestao_ocorrencias.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoModalidadeConverter implements AttributeConverter<TipoModalidade, String> {
    @Override
    public String convertToDatabaseColumn(TipoModalidade attribute) {
        if(attribute == null) return null;

        return attribute.getCodigo();
    }

    @Override
    public TipoModalidade convertToEntityAttribute(String dbData){
        if(dbData == null) return null;

        return Stream.of(TipoModalidade.values())
                .filter(c -> c.getCodigo().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + dbData));
    }
}
