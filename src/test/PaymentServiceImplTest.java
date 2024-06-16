package test;

import main.DTO.BillDTO;
import main.data.StatusPayment;
import main.service.PaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PaymentServiceImplTest {
    private PaymentServiceImpl paymentService;
    private SimpleDateFormat sdf;

    @Before
    public void setUp() {
        paymentService = new PaymentServiceImpl();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    // Test CASH_IN
    @Test
    public void testCashIn() {
        Long balance = paymentService.cashIn(1000L);
        assertEquals(Long.valueOf(1000L), balance);
    }

    //Test CREATE_BILL
    @Test
    public void testCreateBill() throws ParseException {
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        BillDTO retrievedBill = paymentService.getBill(1);
        assertNotNull(retrievedBill);
        assertEquals(billDTO.getBillId(), retrievedBill.getBillId());
    }

    //Test UPDATE_BILL
    @Test
    public void testUpdateBill() throws ParseException {
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);

        BillDTO updatedBillDTO = new BillDTO(1, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "NEW_MOMO");
        paymentService.updateBill(updatedBillDTO);

        BillDTO retrievedBill = paymentService.getBill(1);
        assertNotNull(retrievedBill);
        assertEquals(updatedBillDTO.getProvider(), retrievedBill.getProvider());
        assertEquals(updatedBillDTO.getAmount(), retrievedBill.getAmount());
    }

    // Test DELETE_BILL
    @Test
    public void testDeleteBill() throws ParseException {
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        paymentService.deleteBill(1);
        BillDTO retrievedBill = paymentService.getBill(1);
        assertNull(retrievedBill);
    }

    // Test BILL
    @Test
    public void testGetBill() throws ParseException {
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        BillDTO retrievedBill = paymentService.getBill(1);
        assertNotNull(retrievedBill);
        assertEquals(billDTO.getBillId(), retrievedBill.getBillId());
    }

    // Test LIST_BILL
    @Test
    public void testGetBills() throws ParseException {
        BillDTO billDTO1 = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO1);
        paymentService.createBill(billDTO2);

        Set<BillDTO> bills = paymentService.getBills();
        assertEquals(2, bills.size());
    }

    // Test SEARCH_BILL_BY_MOMO
    @Test
    public void testGetBillsByMOMO() throws ParseException {
        BillDTO billDTO1 = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO1);
        paymentService.createBill(billDTO2);

        List<BillDTO> bills = paymentService.getBills("MOMO");
        assertEquals(2, bills.size());
    }

    //Test PAY
    @Test
    public void testPayBill() throws ParseException {
        paymentService.cashIn(3000L);
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);

        Long balance = paymentService.payBill(1);
        assertEquals(Long.valueOf(2000L), balance);
    }

    // Test PAY with list id
    @Test
    public void testPayBills() throws ParseException {
        paymentService.cashIn(3000L);
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");

        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        paymentService.createBill(billDTO2);
        Integer[] listId = new Integer[]{1, 2};
        Long balance = paymentService.payBills(listId);
        assertEquals(Long.valueOf(0L), balance);
    }

    @Test
    public void testAutoPayment() throws ParseException {
        paymentService.cashIn(3000L);
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);

        Date date = sdf.parse("16/06/2024 22:26:00");
        paymentService.autoPayment(date, 1);

        try {
            Thread.sleep(2000); // Wait for the scheduled task to run
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BillDTO retrievedBill = paymentService.getBill(1);
        assertNull(retrievedBill); // The bill should be paid and removed
    }

    //Test DUE_DATE
    @Test
    public void testOverDueBill() throws ParseException {
        paymentService.cashIn(3000L);
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("15/06/2024 22:26:00"), "NOT_PAID", "MOMO");

        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        paymentService.createBill(billDTO2);

        List<BillDTO> listDueDate = paymentService.overdueBill();
        assertEquals(1, listDueDate.size());
    }

    //Test PAY_BY_MOMO
    @Test
    public void testPayBillsByMOMO() throws ParseException {
        paymentService.cashIn(3000L);
        BillDTO billDTO = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO");

        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO");
        paymentService.createBill(billDTO);
        paymentService.createBill(billDTO2);
        Long balance = paymentService.payBillsByProvider("MOMO");
        assertEquals(Long.valueOf(0L), balance);
    }

    // Test LIST_PAYMENT
    @Test
    public void testPaymentHistory() throws ParseException {
        BillDTO billDTO1 = new BillDTO(1, "WATER", 1000L, sdf.parse("16/06/2024 22:26:00"), "NOT_PAID", "MOMO1");
        BillDTO billDTO2 = new BillDTO(2, "ELECTRICITY", 2000L, sdf.parse("17/06/2024 22:26:00"), "NOT_PAID", "MOMO2");
        paymentService.createBill(billDTO1);
        paymentService.createBill(billDTO2);
        paymentService.cashIn(3000L);
        paymentService.payBill(1);
        paymentService.payBill(2);
        Set<BillDTO> history = paymentService.getHistory();
        assertEquals(2, history.size());
        for (BillDTO bill : history) {
            assertEquals(StatusPayment.PROCESSED, bill.getState());
        }
    }
}
