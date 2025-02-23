package com.polla.bo.polla.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.lang.reflect.Field;

@Slf4j
public class ParameterSetter {

    public static void setParameters(PreparedStatement ps, Object entity, String... additionalParams) throws SQLException {
        if (entity == null) {
            throw new SQLException("Entity cannot be null");
        }

        try {
            int parameterIndex = setEntityFields(ps, entity);
            setAdditionalParameters(ps, parameterIndex, additionalParams);
        } catch (Exception e) {
            log.error("Error setting parameters for entity: {}", entity.getClass().getSimpleName(), e);
            throw new SQLException("Error setting parameters", e);
        }
    }

    private static int setEntityFields(PreparedStatement ps, Object entity) throws SQLException {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        int parameterIndex = 1;

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                setParameter(ps, parameterIndex++, value, field.getType());
                log.debug("Set parameter {} with value {} of type {}",
                        parameterIndex - 1, value, field.getType().getSimpleName());
            } catch (IllegalAccessException e) {
                log.error("Error accessing field: {}", field.getName(), e);
                throw new SQLException("Error accessing field: " + field.getName(), e);
            }
        }
        return parameterIndex;
    }

    private static void setAdditionalParameters(PreparedStatement ps, int startIndex, String... additionalParams)
            throws SQLException {
        if (additionalParams != null) {
            for (String param : additionalParams) {
                ps.setString(startIndex++, param);
                log.debug("Set additional parameter {} with value {}", startIndex - 1, param);
            }
        }
    }

    private static void setParameter(PreparedStatement ps, int index, Object value, Class<?> type)
            throws SQLException {
        if (value == null) {
            ps.setNull(index, getSqlType(type));
            return;
        }

        switch (type.getName()) {
            case "java.lang.String":
                ps.setString(index, (String) value);
                break;
            case "int":
            case "java.lang.Integer":
                ps.setInt(index, (Integer) value);
                break;
            case "long":
            case "java.lang.Long":
                ps.setLong(index, (Long) value);
                break;
            case "double":
            case "java.lang.Double":
                ps.setDouble(index, (Double) value);
                break;
            case "float":
            case "java.lang.Float":
                ps.setFloat(index, (Float) value);
                break;
            case "boolean":
            case "java.lang.Boolean":
                ps.setBoolean(index, (Boolean) value);
                break;
            case "java.math.BigDecimal":
                ps.setBigDecimal(index, (BigDecimal) value);
                break;
            case "java.time.LocalDate":
                ps.setDate(index, Date.valueOf((LocalDate) value));
                break;
            case "java.time.LocalDateTime":
                ps.setTimestamp(index, Timestamp.valueOf((LocalDateTime) value));
                break;
            case "java.util.Date":
                ps.setTimestamp(index, new Timestamp(((java.util.Date) value).getTime()));
                break;
            default:
                throw new SQLException("Unsupported type: " + type.getName());
        }
    }

    private static int getSqlType(Class<?> type) {
        if (type == String.class) return Types.VARCHAR;
        if (type == Integer.class || type == int.class) return Types.INTEGER;
        if (type == Long.class || type == long.class) return Types.BIGINT;
        if (type == Double.class || type == double.class) return Types.DOUBLE;
        if (type == Float.class || type == float.class) return Types.FLOAT;
        if (type == Boolean.class || type == boolean.class) return Types.BOOLEAN;
        if (type == BigDecimal.class) return Types.DECIMAL;
        if (type == LocalDate.class) return Types.DATE;
        if (type == LocalDateTime.class) return Types.TIMESTAMP;
        if (type == java.util.Date.class) return Types.TIMESTAMP;
        return Types.OTHER;
    }
}