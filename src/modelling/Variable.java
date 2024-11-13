package modelling;

import java.util.Set;

/**
 * Represents a variable in the Blocks World model.
 * 
 * A variable is defined by its name (typically an integer) and its domain,
 * which is the set of possible values it can take. This class provides basic
 * functionality for accessing the name, domain, and comparing variables.
 * 
 * It is designed to be extended by more specific types of variables, such as
 * {@link BooleanVariable}.
 * 
 */
public class Variable {

    protected Integer name;
    protected Set<Object> domain;

    /**
     * Constructs a Variable with the given name and domain.
     * 
     * @param name   The name of the variable .
     * @param domain The set of possible values this variable can take.
     */

    public Variable(Integer name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    /**
     * Returns the name of this variable.
     * 
     * @return The unique identifier of this variable.
     */

    public Integer getName() {
        return this.name;
    }

    /**
     * Returns a string representation of this variable, including its name and
     * domain.
     * 
     * @return A string that describes the variable and its domain.
     */

    @Override
    public String toString() {
        return "Variable : " + this.name + " Domaine : " + this.domain + ".";
    }

    /**
     * Returns the domain of possible values for this variable.
     * 
     * @return A set containing the possible values this variable can take.
     */

    public Set<Object> getDomain() {
        return this.domain;
    }

    /**
     * Determines whether this variable is equal to another object.
     * 
     * Two variables are considered equal if they have the same name and belong
     * to the same class.
     * 
     * @param obj The object to compare this variable with.
     * @return {@code true} if the object is a variable with the same name and
     *         class, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        Variable var = (Variable) obj;
        return this.name.equals(var.getName()) && this.getClass().equals(obj.getClass());
    }

    /**
     * Returns the hash code of this variable.
     * 
     * The hash code is computed based on the variable's name and class.
     * 
     * @return The hash code for this variable.
     */

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.getClass().hashCode();
    }

     /**
     * Checks if a given variable is considered a "Block On" variable.
     * 
     * A variable is considered to be a "Block On" variable if its name is a
     * non-negative integer and it is an instance of the {@code Variable} class.
     * 
     * @param variable The variable to check.
     * @return {@code true} if the variable's name is non-negative and it is an
     *         instance of {@code Variable}, {@code false} otherwise.
     */

    public static boolean isBlockOnVariable(Variable variable){
        if(variable.getName()>=0 && variable.getClass().equals(Variable.class)){
            return true;
        }
        return false;
    }
}
