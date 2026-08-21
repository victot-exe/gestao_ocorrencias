package com.victot.gestao_ocorrencias.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusOcorrenciaConverter implements AttributeConverter<StatusOcorrencia, String> {
    @Override
    public String convertToDatabaseColumn(StatusOcorrencia attribute) {
        return attribute == null ? null : attribute.getCodigo();
    }

    @Override
    public StatusOcorrencia convertToEntityAttribute(String dbData) {
        return StatusOcorrencia.doCodigo(dbData);
    }
}
