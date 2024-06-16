package main.service;
import main.DTO.BillDTO;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PaymentService {
    public Long cashIn(Long amount);

    public void createBill(BillDTO billDTO);

    public void updateBill(BillDTO billDTO);

    public void deleteBill(int id);

    public BillDTO getBill(int id);

    public List<BillDTO> getBills(String provider);

    public Set<BillDTO> getBills();

    public Long payBill(Integer id);

    public Long payBills(Integer[] listId);

    public Long payBills();

    public Long payBillsByProvider(String provider);

    public List<BillDTO> overdueBill();

    public void autoPayment(Date date, int id);

    public Set<BillDTO> getHistory();
}
