package Service.ReturnResult;

public class VisitorInResult {
    private CheckInResult status; // Trạng thái
    private int ticket; // ID của lịch sử gửi xe (nếu thành công)

    // Constructor
    public VisitorInResult(CheckInResult status, int parkingHistoryId) {
        this.status = status;
        this.ticket = parkingHistoryId;
    }

    // Getter và Setter
    public CheckInResult getStatus() {
        return status;
    }

    public void setStatus(CheckInResult status) {
        this.status = status;
    }

    public int getTicket() {
        return ticket;
    }
}