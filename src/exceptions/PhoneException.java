package exceptions;

public class PhoneException extends RuntimeException {

    public PhoneException(){
        super();
    }

    public PhoneException(String message){
        super(message);
    }

}
