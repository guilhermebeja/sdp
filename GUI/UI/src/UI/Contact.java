package UI;

public class Contact {
    public static final int FRIEND = 1, REQUEST_SENT = 2, REQUEST_RECEIVED = 3;

    private int status = 0;
    private String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact(int status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        String r = name;
        if(status == REQUEST_SENT){
            r += " (Request Sent)";
        }
        else if(status == REQUEST_RECEIVED){
            r += " (New Request)";
        }

        return r;
    }
}
