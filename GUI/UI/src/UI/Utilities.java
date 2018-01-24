package UI;

public class Utilities {
    public static int generateConversationID(String usernames){
        int result = 0;
        for(int i=0; i < usernames.length(); i++){
            result += usernames.charAt(i);
        }
        return result;
    }
}
