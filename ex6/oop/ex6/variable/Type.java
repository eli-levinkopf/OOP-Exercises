package oop.ex6.variable;

import oop.ex6.parser.IllegalLineException;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    INT, DOUBLE, STRING, CHAR, BOOLEAN;

    private static final Map<String, Type> typeMap = new HashMap<>();

    static {
        typeMap.put("int", INT);
        typeMap.put("double", DOUBLE);
        typeMap.put("String", STRING);
        typeMap.put("char", CHAR);
        typeMap.put("boolean", BOOLEAN);
    }

    public static Type getType(String parameter) {
//        parameter = parameter.replace("\"", "").trim();
        if (parameter.matches("'.'")) {
            return Type.CHAR;
//        } else if (parameter.matches("\"[^\"]*\"")) {//"\"(.*)\""
        } else if (parameter.matches("\"\\s*(.*)\"\\s*")) {
            return Type.STRING;
        } else if (parameter.matches("^[+-]?[0-9]+$")) {
            return Type.INT;
        } else if (parameter.matches("(true)|(false)")) {
            return Type.BOOLEAN;
        } else if (parameter.matches("(\\-)?\\d*\\.?\\d*")){
            return Type.DOUBLE;
//        } else if (parameter.matches("((([-][0-9]+)|([0-9]+))\\.[0-9]*)|([0-9]*\\.[0-9]+)|([-][0-9]+)|([0-9]+)")) {
//            return Type.DOUBLE;
        }
        return null;
    }

    public static Type stringToType(String strToType) throws IllegalLineException {
        Type type = typeMap.get(strToType);
        if (type == null) {
            throw new IllegalLineException("ERROR_ILLEGAL_TYPE");
        }
        return type;
    }
}

class c {
    public static void main(String[] args) {
        String a = "\"Hello \"" ;
        Type.getType(a);
    }
}