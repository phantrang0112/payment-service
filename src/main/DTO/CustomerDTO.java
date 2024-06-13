package main.DTO;

import java.util.List;

public class CustomerDTO {
    private Long balance;
    private List<BillDTO> listBill;

    public CustomerDTO() {
    }

    public CustomerDTO(Long balance, List<BillDTO> listBill) {
        this.balance = balance;
        this.listBill = listBill;
    }

    public Long getBalance() {
        return balance;
    }

    public List<BillDTO> getListBill() {
        return listBill;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setListBill(List<BillDTO> listBill) {
        this.listBill = listBill;
    }
}
