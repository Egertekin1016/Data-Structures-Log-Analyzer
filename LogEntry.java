public class LogEntry implements Comparable<LogEntry> {
    private String timestamp;
    private String sourceIp;
    private String destinationIp;
    private String protocol;
    private int port;
    private String threatLevel;
    private String action;

    public LogEntry(String timestamp, String sourceIp, String destinationIp, String protocol, int port, String threatLevel, String action) {
        this.timestamp = timestamp;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.protocol = protocol;
        this.port = port;
        this.threatLevel = threatLevel;
        this.action = action;
    }

    // Güncelleme işlemleri için setter metotları
    public void setThreatLevel(String threatLevel) { this.threatLevel = threatLevel; }
    public void setAction(String action) { this.action = action; }

    // Tehdit ağırlığını belirleme (PriorityQueue için)
    public int getThreatWeight() {
        switch (threatLevel) {
            case "Critical": return 4;
            case "High": return 3;
            case "Medium": return 2;
            case "Low": return 1;
            default: return 0;
        }
    }

    @Override
    public int compareTo(LogEntry other) {
        return Integer.compare(other.getThreatWeight(), this.getThreatWeight());
    }

    // Getter Metotları
    public String getTimestamp() { return timestamp; }
    public String getSourceIp() { return sourceIp; }
    public String getDestinationIp() { return destinationIp; }
    public int getPort() { return port; }
    public String getThreatLevel() { return threatLevel; }
    public String getAction() { return action; }

    @Override
    public String toString() {
        return String.format("[%s] %s:%d -> %s | Tehdit: %-8s | Durum: %s",
                timestamp, sourceIp, port, destinationIp, threatLevel, action);
    }
}