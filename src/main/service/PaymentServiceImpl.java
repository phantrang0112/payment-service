package main.service;

import main.DTO.BillDTO;
import main.DTO.CustomerDTO;
import main.DTO.PaymentResult;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
  private final CustomerDTO customerDTO;
  PaymentServiceImpl(){
      this.customerDTO= new CustomerDTO();
  }

    @Override
    public Long cashIn(Long amount) {
        if(amount>0){
            customerDTO.setBalance(customerDTO.getBalance()+amount);
        }
        return customerDTO.getBalance();


    }

    @Override
    public BillDTO createBill(BillDTO billDTO) {
        return null;
    }

    @Override
    public String deleteBill(Integer id) {
        return null;
    }

    @Override
    public BillDTO getBill(Integer id) {
        return null;
    }

    @Override
    public List<BillDTO> getBill(String provider) {
        return null;
    }

    @Override
    public List<BillDTO> getBills() {
        return null;
    }

    @Override
    public PaymentResult payBill(Integer id) {
        return null;
    }

    @Override
    public PaymentResult payBills(Integer[] listId) {
        return null;
    }

    @Override
    public PaymentResult payBillsByProvider(String provider) {
        return null;
    }

    @Override
    public List<BillDTO> overdueBill() {
        return null;
    }

    @Override
    public String autoPayment() {
        return null;
    }

    @Override
    public String getHistory() {
        return null;
    }
}
