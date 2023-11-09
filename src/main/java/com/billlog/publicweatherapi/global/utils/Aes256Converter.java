package com.billlog.publicweatherapi.global.utils;

import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Configuration
public class Aes256Converter implements AttributeConverter<String, String> {

    private final Aes256Utils aes256Utils;

    public Aes256Converter() {
        this.aes256Utils = new Aes256Utils();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!StringUtils.hasText(attribute)) {
            return attribute;
        }
        try {
            return aes256Utils.encrypt(attribute);
        } catch (Exception e) {
            throw new BLBusinessException("failed to encrypt data");
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aes256Utils.decrypt(dbData);
        } catch (Exception e) {
            return dbData;
        }
    }
}
