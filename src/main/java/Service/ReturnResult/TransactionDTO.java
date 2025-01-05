package Service.ReturnResult;


import java.sql.Timestamp;

public class TransactionDTO {
    private Timestamp time;
    private int amount;
    private String typeTransaction;

    public TransactionDTO(Timestamp time, int amount, String typeTransaction) {
        super();
        this.time = time;
        this.amount = amount;
        this.typeTransaction = typeTransaction;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

}
