package main.DTO;

import main.data.StatusPayment;
import main.utils.CommonService;
import main.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BillDTO {
    private Integer billId;
    private String type;
    private Long amount;
    private Date dueDate;
    private String state;
    private String provider;

    private Date paymentDate;

    public BillDTO() {
    }

    public BillDTO(Integer billId, String type, Long amount, Date dueDate, String state, String provider, Date paymentDate) {

        this.billId = billId;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
        this.paymentDate = paymentDate;
    }

    public BillDTO(Integer billId, String type, Long amount, Date dueDate, String state, String provider) {

        this.billId = billId;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public BillDTO(String[] input) {
        int index = 1;
        this.billId = Utils.parseInt(input[index]);
        this.type = Utils.parseString(input[index + 1]);
        this.amount = Utils.parseLong(input[index + 2]);
        this.dueDate = CommonService.convertStringDate("dd/MM/yyyy", input[index + 3]);
        this.state = StatusPayment.NOT_PAID;
        this.provider = Utils.parseString(input[index + 4]);

    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDTO billDTO = (BillDTO) o;
        return Objects.equals(billId, billDTO.billId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId);
    }

    public void printBillDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dueDateFormatted = (dueDate != null) ? dateFormat.format(dueDate) : "N/A";
        System.out.printf("%d. %s\t%d\t%s\t%s\t%s\n", billId, type, amount, dueDateFormatted, state, provider);
    }

    public static void printHeader() {
        System.out.println("Bill No.\tType\tAmount\tDue Date\tState\tPROVIDER");
    }

    public static void printBillList(Set<BillDTO> bills) {
        printHeader();
        for (BillDTO bill : bills) {
            bill.printBillDetails();
        }
    }

    public static void printBillList(List<BillDTO> bills) {
        printHeader();
        for (BillDTO bill : bills) {
            bill.printBillDetails();
        }
    }
}
