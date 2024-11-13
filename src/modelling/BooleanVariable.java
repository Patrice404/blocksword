package modelling;

import java.util.*;

/**
 * Represents a boolean variable in the Blocks World model.
 * 
 * This class is a specialized version of the {@link Variable} class where the domain 
 * of possible values is restricted to {@code true} and {@code false}.
 * 
 * It is typically used to represent boolean attributes of blocks, such as whether a 
 * block is "fixed" or "free".
 * 
 */
public class BooleanVariable extends Variable {

    /**
     * The domain of this boolean variable, restricted to {@code true} and {@code false}.
     */
    private static final Set<Object> DOMAIN = new HashSet<Object>() {
        {
            add(true);
            add(false);
        }
    };

    /**
     * Constructs a BooleanVariable with the specified name and a fixed domain of 
     * {@code true} and {@code false}.
     * 
     * @param name The unique identifier for this boolean variable.
     */
    public BooleanVariable(Integer name) {
        super(name, BooleanVariable.DOMAIN);
    }

    /**
     * Returns a string representation of this BooleanVariable, showing its name.
     * 
     * @return A string describing the boolean variable by its name.
     */
    @Override
    public String toString() {
        return "BooleanVariable : " + this.name;
    }

    /**
     * Determines if the given variable represents a "Stack Free" boolean variable.
     * 
     * A variable is considered a "Stack Free" variable if its name is a negative integer 
     * and it is an instance of the {@code BooleanVariable} class.
     * 
     * @param variable The variable to check.
     * @return {@code true} if the variable's name is negative and it is an instance of 
     *         {@code BooleanVariable}, {@code false} otherwise.
     */
    public static boolean isStackFreeVariable(Variable variable){
        if(variable.getName() < 0 && variable.getClass().equals(BooleanVariable.class)){
            return true;
        }
        return false;
    }

     /**
     * Determines if the given variable represents a "Block Fixed" boolean variable.
     * 
     * A variable is considered a "Block Fixed" variable if its name is a non-negative 
     * integer and it is an instance of the {@code BooleanVariable} class.
     * 
     * @param variable The variable to check.
     * @return {@code true} if the variable's name is non-negative and it is an instance of 
     *         {@code BooleanVariable}, {@code false} otherwise.
     */
    public static boolean isBlockFixedVariable(Variable variable){
        if(variable.getName() >= 0 && variable.getClass().equals(BooleanVariable.class)){
            return true;
        }
        return false;
    }
}
