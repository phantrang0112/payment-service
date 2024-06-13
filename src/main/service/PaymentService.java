package main.service;

import main.DTO.BillDTO;
import main.DTO.PaymentResult;

import java.util.List;

public interface PaymentService {
    public Long cashIn(Long amount);
    public BillDTO createBill(BillDTO billDTO);
    public  String deleteBill(Integer id);
    public  BillDTO getBill(Integer id);
    public  List<BillDTO> getBill( String provider);
    public List<BillDTO> getBills();
    public PaymentResult payBill(Integer id);
    public  PaymentResult payBills(Integer[] listId);
    public  PaymentResult payBillsByProvider(String provider);
    public  List<BillDTO> overdueBill();
    public String autoPayment();
    public String getHistory();
}
