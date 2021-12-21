package baboon;

import java.util.Scanner;

public class InputReader {
    Scanner scanner = new Scanner(System.in);

    public String nextLine()
    {
        String reset = scanner.nextLine();
        switch (reset){
            case "@":
                String[] arg = new String[] {};
                mainClass.main(arg);
                break;
        }
        return reset;
    }
}


