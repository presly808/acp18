package servlets.validator;

import org.springframework.stereotype.Component;
import servlets.model.ServUser;

import java.util.regex.Pattern;

@Component(value = "validator")
public class Validator {

    private final String emailPattern = ".+@.+\\..+"; // xxxxx@ttttt.eeee
    private final String passPattern ="^.{4,8}$"; // Password matching expression.
    // Password must be at least 4 characters, no more than 8 characters,
    // and must include at least one upper case letter, one lower case letter,
    // and one numeric digit.


    public boolean checkEmail(String email){
        if (email == null) return false;
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    public boolean checkPass(String pass){
        if (pass == null) return false;
        return Pattern.compile(passPattern).matcher(pass).matches();
    }

    public boolean checkUser(ServUser servUser){
        return checkEmail(servUser.getEmail()) && checkPass(servUser.getPass());
    }

}
