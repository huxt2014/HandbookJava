package handbook.statemachine.app.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent()
public class ShellCommand {

    @ShellMethod("hello world")
    public void hello(){
        System.out.println("world");
    }
}
