package main.DTO;

import java.util.HashSet;
import java.util.Set;

public class CustomerDTO {
    private Long balance;
    private Set<BillDTO> listBill;

    private Set<BillDTO> paymentHistory;

    public CustomerDTO() {
        this.balance = 0L;
        this.listBill = new HashSet<>();
        this.paymentHistory = new HashSet<>();
    }

    public CustomerDTO(Long balance, Set<BillDTO> listBill, Set<BillDTO> paymentHistory) {
        this.balance = balance;
        this.listBill = listBill;
        this.paymentHistory = paymentHistory;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Set<BillDTO> getListBill() {
        return listBill;
    }

    public void setListBill(Set<BillDTO> listBill) {
        this.listBill = listBill;
    }

    public Set<BillDTO> getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(Set<BillDTO> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }
}
