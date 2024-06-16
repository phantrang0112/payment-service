package main.service;

import main.DTO.BillDTO;
import main.DTO.CustomerDTO;
import main.data.StatusPayment;
import main.utils.CommonService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class PaymentServiceImpl implements PaymentService {
    private final CustomerDTO customerDTO;

    public PaymentServiceImpl() {
        this.customerDTO = new CustomerDTO();

    }

    @Override
    public Long cashIn(Long amount) {
        if (amount > 0) {
            customerDTO.setBalance(customerDTO.getBalance() + amount);
            System.out.println("Set amount success!");
        }
        return customerDTO.getBalance();
    }

    @Override
    public void createBill(BillDTO billDTO) {
        if (!CommonService.isNull(billDTO)) {
            BillDTO bill = customerDTO.getListBill().stream().filter(customerDTO -> Objects.equals(customerDTO.getBillId(), billDTO.getBillId())).findAny().orElse(null);
            if (CommonService.isNull(bill)) {
                customerDTO.getListBill().add(billDTO);
                System.out.println("Import bill success!");
            } else
                System.out.println("Bill id is exist!");

        } else
            System.out.println("Bill is not found!");
    }

    @Override
    public void updateBill(BillDTO billDTO) {
        if (!CommonService.isNull(billDTO)) {
            if (CommonService.isNull(getBill(billDTO.getBillId())))
                System.out.println("BillId is not exist!");
            else {
                customerDTO.getListBill().remove(billDTO);
                customerDTO.getListBill().add(billDTO);
                System.out.println("Update bill id:" + billDTO.getBillId() + " is success");
            }

        } else
            System.out.println("Bill is not found!");
    }

    @Override
    public void deleteBill(int id) {
        if (id > 0) {
            BillDTO billDTO = getBill(id);
            if (!CommonService.isNull(billDTO)) {
                customerDTO.getListBill().remove(billDTO);
                System.out.println("Delete bill with id:" + id + "  success!");
            }
        }
        System.out.println(" Bill id:" + id + " not found!");


    }

    @Override
    public BillDTO getBill(int id) {
        if (id > 0 && !customerDTO.getListBill().isEmpty()) {
            return customerDTO.getListBill().stream().filter(customerDTO -> customerDTO.getBillId() == id).findAny().orElse(null);
        }
        System.out.println("Can't found by id:" + id);
        return null;
    }

    @Override
    public List<BillDTO> getBills(String provider) {
        if (!CommonService.isEmpty(provider) && customerDTO.getListBill().size() > 0) {
            return customerDTO.getListBill().stream().filter(item -> item.getProvider().equals(provider)).toList();
        }
        System.out.println("Can't found list bill with provider:" + provider);
        return new ArrayList<>();

    }

    @Override
    public Set<BillDTO> getBills() {
        if (!customerDTO.getListBill().isEmpty()) {
            return customerDTO.getListBill();
        }
        System.out.println("List bill is null!");
        return new HashSet<>();
    }

    @Override
    public Long payBill(Integer id) {
        if (id > 0) {
            BillDTO bill = getBill(id);
            if (!CommonService.isNull(bill)) {
                Long balance = payment(bill.getAmount(), customerDTO.getBalance());
                if (!CommonService.isNull(balance)) {
                    customerDTO.setBalance(balance);
                    customerDTO.getListBill().remove(bill);
                    saveHistoryPayment(StatusPayment.PROCESSED, bill);
                    System.out.println("Payment has been completed for Bill with id " + id);
                    return balance;
                } else {
                    saveHistoryPayment(StatusPayment.PENDING, bill);
                    return null;
                }

            }
        }
        System.out.println("Sorry! Not found a bill with such id!");
        return null;
    }


    @Override
    public Long payBills(Integer[] listId) {
        long totalAmount = 0L;
        if (!customerDTO.getListBill().isEmpty()) {
            for (Integer id : listId) {
                BillDTO bill = getBill(id);
                saveHistoryPayment(StatusPayment.PENDING, bill);
                totalAmount = !CommonService.isNull(bill) ? totalAmount + bill.getAmount() : totalAmount;
            }

            if (totalAmount > customerDTO.getBalance()) {
                System.out.println("Sorry! Not enough fund to proceed with payment!");
                return null;
            } else {
                for (Integer id : listId) {
                    payBill(id);
                }
            }
        }
        return customerDTO.getBalance();
    }

    public Long payBills() {
        long totalAmount = 0L;
        if (!customerDTO.getListBill().isEmpty()) {
            for (BillDTO billDTO : customerDTO.getListBill()) {
                saveHistoryPayment(StatusPayment.PENDING, billDTO);
                totalAmount = !CommonService.isNull(billDTO) ? totalAmount + billDTO.getAmount() : totalAmount;

            }
            if (totalAmount > customerDTO.getBalance()) {
                System.out.println("Sorry! Not enough fund to proceed with payment!");
                return null;
            } else {
                for (BillDTO billDTO : customerDTO.getListBill()) {
                    payBill(billDTO.getBillId());
                }
            }
        }
        return customerDTO.getBalance();
    }

    @Override
    public Long payBillsByProvider(String provider) {
        long totalAmount = 0L;
        if (!customerDTO.getListBill().isEmpty()) {
            List<BillDTO> listBill = getBills(provider);
            if (!listBill.isEmpty()) {
                for (BillDTO bill : listBill) {
                    if (!checkStatusPayment(bill)) {
                        totalAmount = !CommonService.isNull(bill) ? totalAmount + bill.getAmount() : totalAmount;
                    }
                }
                customerDTO.setBalance(payment(totalAmount, customerDTO.getBalance()));
                return customerDTO.getBalance();
            }

        }
        return null;
    }

    @Override
    public List<BillDTO> overdueBill() {
        if (customerDTO.getListBill().isEmpty()) {
            System.out.println("List bill not found!");
            return new ArrayList<>();
        } else {
            Date currentDate = new Date();
            List<BillDTO> overdueList = new ArrayList<>();
            for (BillDTO item : customerDTO.getListBill()) {
                if (item.getDueDate().before(currentDate)) {
                    overdueList.add(item);
                }
            }
            return overdueList;
        }
    }

    @Override
    public void autoPayment(Date date, int id) {
        Timer timer = new Timer();
        System.out.println("Payment for bill id: " + id + " is scheduled on " + CommonService.convertDateString(date));
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                BillDTO billDTO = getBill(id);
                Long balance = payBill(billDTO.getBillId());
                System.out.println("Your current balance is: " + balance);
            }
        };
        timer.schedule(task, date);

    }

    @Override
    public Set<BillDTO> getHistory() {
        if (!customerDTO.getPaymentHistory().isEmpty()) {
            return customerDTO.getPaymentHistory();
        }
        System.out.println("Can't found history!");
        return new HashSet<>();
    }

    public Long getTotalAmount(List<BillDTO> billDTOList) {
        long totalAmount = 0L;
        for (BillDTO bill : billDTOList) {
            totalAmount = !CommonService.isNull(bill) ? totalAmount + bill.getAmount() : totalAmount;
        }
        return totalAmount;
    }

    public Long payment(Long amount, Long balance) {
        if (amount > 0 && amount <= balance) {
            return balance - amount;
        } else if (amount > balance) {
            System.out.println("Sorry! Not enough fund to proceed with payment!");
        }
        System.err.println("Amount is not available!");
        return null;
    }

    public boolean checkStatusPayment(BillDTO billDTO) {
        return billDTO.getState().equals(StatusPayment.PROCESSED);
    }

    public void saveHistoryPayment(String status, int id) {
        BillDTO billDTO = getBill(id);
        billDTO.setState(status);
        customerDTO.getPaymentHistory().remove(billDTO);
        customerDTO.getPaymentHistory().add(billDTO);

    }

    public void saveHistoryPayment(String status, BillDTO billDTO) {
        if (!CommonService.isNull(billDTO)) {
            Date date = new Date();
            billDTO.setState(status);
            billDTO.setPaymentDate(date);
            customerDTO.getPaymentHistory().remove(billDTO);
            customerDTO.getPaymentHistory().add(billDTO);
        } else
            System.out.println(" Can't save history( bill is null)!");
    }
}
