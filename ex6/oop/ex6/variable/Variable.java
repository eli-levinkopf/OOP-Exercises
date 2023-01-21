package oop.ex6.variable;

import oop.ex6.parser.IllegalLineException;

public class Variable {
    private boolean isInitialized = false;
    private boolean isFinal = false;
    private final Type type;
    private final String name;

    public Variable(boolean isFinal, Type type, String variableName) {
        this.isFinal = isFinal;
        this.type = type;
        name = variableName;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }


    public void checkValueType(String value) throws IllegalLineException {
        Type type = Type.getType(value);
        if (type == this.type ) {
            return;
        }
        if (type == null || (this.type == Type.BOOLEAN && type != Type.DOUBLE && type != Type.INT)) {
            throw new IllegalLineException("Incompatible types, expected " + this.type + " but got " + type + ".");
        }
        else if(this.type != Type.BOOLEAN && this.type != Type.DOUBLE && type != Type.INT) {
            throw new IllegalLineException("Incompatible types, expected " + this.type + " but got " + type + ".");
        }
    }

}
