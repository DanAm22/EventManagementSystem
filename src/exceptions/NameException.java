package exceptions;

public class NameException extends RuntimeException {

    public NameException(){
        super();
    }

    public NameException(String message){
        super(message);
    }

}
