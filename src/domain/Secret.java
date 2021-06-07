package domain;

public class Secret {
    private int secretId;
    private String username;
    private String filename;
    private String secretKey;
    private String encryptTime;

    public Secret() {
    }

    public Secret(String username, String filename, String secretKey, String encryptTime) {
        this.username = username;
        this.filename = filename;
        this.secretKey = secretKey;
        this.encryptTime = encryptTime;
    }

    public int getSecretId() {
        return secretId;
    }

    public void setSecretId(int secretId) {
        this.secretId = secretId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEncryptTime() {
        return encryptTime;
    }

    public void setEncryptTime(String encryptTime) {
        this.encryptTime = encryptTime;
    }

    @Override
    public String toString() {
        return "Secret---:" +
                "secretId=" + secretId +
                ", username='" + username + '\'' +
                ", filename='" + filename + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", encryptTime='" + encryptTime + '\'' +
                '}';
    }
}
