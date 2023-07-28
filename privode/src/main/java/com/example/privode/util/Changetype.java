package com.example.privode.util;

public class Changetype {
    public static Object createData(String dataType, String data) {
        if (dataType.equals("int")) {
            return Integer.parseInt(data);
        } else if (dataType.equals("float")) {
            return Float.parseFloat(data);
        } else if (dataType.equals("boolean")) {
            return Boolean.parseBoolean(data);
        } else if (dataType.equals("string")) {
            return data;
        }  else if (dataType.equals("short")) {
            Short.parseShort(data);
            return data;
        }else if (dataType.equals("long")) {
            Long.parseLong(data);
            return data;
        } else if (dataType.equals("double")) {
            Double.parseDouble(data);
            return data;
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }
    }
}
