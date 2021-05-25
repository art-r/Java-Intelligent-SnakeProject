package logik;

//Exception that we need while checking our custom settings file
public class NoValidSettingsFileException extends Exception{
    public NoValidSettingsFileException(String message) {
        super(message);
    }

    public NoValidSettingsFileException() {
    }
}
