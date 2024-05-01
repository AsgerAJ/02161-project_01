package example.cucumber.helpers;

public class ErrorMessageHolder {
    private String errorMessage = "";

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
