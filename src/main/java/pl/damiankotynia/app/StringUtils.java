package pl.damiankotynia.app;

import pl.damiankotynia.app.exceptions.StringPreparingException;

public class StringUtils {

    public static String validateString(String variable) throws StringPreparingException {
        if(variable==null)
            throw new StringPreparingException();
        variable.replace("\\s", "");
        if ("".equals(variable))
            throw new StringPreparingException();
        return variable;
    }

    public static Double parseToDouble(String value)throws StringPreparingException {
        Double parsedValue = null;
        try{
             parsedValue = Double.parseDouble(validateString(value));
        }catch (NumberFormatException e){
            throw new StringPreparingException();
        }
        return parsedValue;
    }
}
