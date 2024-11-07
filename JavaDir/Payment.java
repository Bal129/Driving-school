package JavaDir;

public class Payment {
    //private String studentIc;
    private float totalPrice;
    private float paid;
    private boolean completed;

    Payment() {}
    Payment(float totalPrice) {
        setTotalPrice(totalPrice);
    }

    Payment(float totalPrice, float paid, boolean completed) {
        setTotalPrice(totalPrice);
        setPaid(paid);
        this.completed = completed;
    }

    // getter
    public float getTotalPrice() {
        return totalPrice;
    }
    public float getPaid() {
        return paid;
    }
    public boolean getCompleted() {
        return completed;
    }

    // setter
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setPaid(float paid) {
        this.paid = paid;
    }
    public void switchCompleted() {
        completed = !completed;
    }
    public void updatePaid(float amountPaid) {
        setPaid(getPaid() + amountPaid);
    }
    public boolean isFinishedPaying() {
        return totalPrice - paid <= 0;
    }
}
