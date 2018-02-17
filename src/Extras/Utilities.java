package Extras;

public class Utilities {
    public static int CreateConversationID(String... usernames){
        int val = 0;
        for(String username: usernames){
            for(Character c : username.toCharArray()){
                val+=c.charValue();
            }
        }

        return val;
    }
}
