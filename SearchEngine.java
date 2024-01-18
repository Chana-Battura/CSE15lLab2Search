import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.

    ArrayList<String> vals = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String all_vals = "Welcome to Charan's Search Engine: \nCurrent Paths:\n";
            for (String i : vals){
                all_vals += i + "\n";
            }
            return String.format(all_vals);
        } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String output = "Here are the following results:\n";
                if (parameters[0].equals("s")) {
                    for (String i: vals){
                        if (i.contains(parameters[1])){
                            output += i + "\n";
                        }
                    }
                }
                return String.format(output); 
        } else {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    vals.add(parameters[1]);
                    return String.format("Thank you for adding %s!", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
