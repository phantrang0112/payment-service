import main.DTO.BillDTO;
import main.data.MenuPayment;
import main.service.PaymentService;
import main.service.PaymentServiceImpl;
import main.utils.CommonService;
import main.utils.Utils;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentServiceImpl();
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("=== MENU ===");
        System.out.println("CASH_IN: CASH IN");
        System.out.println("CREATE_BILL: CREATE BILL");
        System.out.println("UPDATE_BILL: UPDATE BILL WITH ID");
        System.out.println("DELETE_BILL: DELETE BILL WITH ID");
        System.out.println("BILL: VIEW BILL");
        System.out.println("LIST_BILL: VIEW LIST BILLS");
        System.out.println("SEARCH_BILL_BY_PROVIDER SEARCH BILLS BY PROVIDER");
        System.out.println("PAY: PAY BILL OR PAY BILLS");
        System.out.println("DUE_DATE VIEW DUE DATE");
        System.out.println("SCHEDULE: SET SCHEDULE");
        System.out.println("PAY_BY_PROVIDER: PAY BY PROVIDER");
        System.out.println("LIST_PAYMENT: GET HISTORY LIST PAYMENTS");
        System.out.println("0. EXIT");
        do {

            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            String[] choices = choice.split(" ");
            if (choices.length > 0) {

                switch (choices[0]) {
                    // Handle CASH_IN option: allows user to add balance to their account
                    case MenuPayment.CASH_IN -> {
                        if (choices.length > 1) {
                            Long balance = Utils.parseLong(choices[1]);
                            System.out.println("Your available balance: " + paymentService.cashIn(balance));
                        } else {
                            System.out.println("Your available balance: " + paymentService.cashIn(0L));
                        }
                    }
                    // Handle CREATE_BILL option: creates a new bill based on user input
                    case MenuPayment.CREATE_BILL -> {
                        if (validateInputBill(choices)) {
                            BillDTO billDTO = new BillDTO(choices);
                            paymentService.createBill(billDTO);
                            billDTO.printBillDetails();
                        }
                    }
                    // Handle UPDATE_BILL option: updates an existing bill with new information
                    case MenuPayment.UPDATE_BILL -> {
                        if (validateInputBill(choices)) {
                            BillDTO billDTO = new BillDTO(choices);
                            paymentService.updateBill(billDTO);
                            billDTO.printBillDetails();
                        }
                    }
                    // Handle DELETE_BILL option: deletes a bill based on its ID
                    case MenuPayment.DELETE_BILL -> {
                        if (choices.length > 1 && CommonService.isPositiveInteger(choices[1])) {
                            paymentService.deleteBill(Utils.parseInt(choices[1]));
                        } else
                            System.out.println("Input is not correct!");
                    }
                    // Handle BILL option: displays details of a specific bill based on its ID
                    case MenuPayment.BILL -> {
                        if (choices.length > 1 && CommonService.isPositiveInteger(choices[1])) {
                            paymentService.getBill(Utils.parseInt(choices[1]));
                        } else
                            System.out.println("Input is not correct!");
                    }
                    // Handle LIST_BILL option: displays a list of all bills
                    case MenuPayment.LIST_BILL -> {
                        Set<BillDTO> lisBillDTOS = paymentService.getBills();
                        if (!CommonService.listIsNull(lisBillDTOS)) {
                            BillDTO.printBillList(lisBillDTOS);
                        }
                    }
                    // Handle SEARCH_BILL_BY_PROVIDER option: search bills by provider name
                    case MenuPayment.SEARCH_BILL_BY_PROVIDER -> {
                        if (choices.length > 1 && !CommonService.isEmpty(choices[1])) {
                            paymentService.getBills(Utils.parseString(choices[1]));
                        } else
                            System.out.println("Input is not correct!");
                    }
                    // Handle PAY option: pays a bill based on its ID and displays current balance
                    case MenuPayment.PAY -> {
                        if (choices.length > 1 && CommonService.isPositiveInteger(choices[1])) {
                            Long balance = paymentService.payBill(Utils.parseInt(choices[1]));
                            System.out.println("Your current balance is: " + balance);
                        } else
                            System.out.println("Input is not correct!");
                    }
                    // Handle DUE_DATE option: displays a list of overdue bills
                    case MenuPayment.DUE_DATE -> {
                        List<BillDTO> lisBillDTOS = paymentService.overdueBill();
                        if (!CommonService.listIsNull(lisBillDTOS)) {
                            BillDTO.printBillList(lisBillDTOS);
                        }
                    }
                    // Handle SCHEDULE option: sets a schedule for automatic bill payment
                    case MenuPayment.SCHEDULE -> {
                        if (choices.length > 3) {
                            String dateS = choices[2] + " " + choices[3];
                            Date date = CommonService.convertStringDate("dd/MM/yyyy HH:mm:ss", dateS);
                            if (!CommonService.isNull(date) && CommonService.isPositiveInteger(choices[1])) {
                                paymentService.autoPayment(date, Utils.parseInt(choices[1]));
                            }
                        } else
                            System.out.println("Input is not correct!");

                    }
                    // Handle PAY_BY_PROVIDER option: pays bills by provider name and displays current balance
                    case MenuPayment.PAY_BY_PROVIDER -> {
                        if (!CommonService.isEmpty(choices[1])) {
                            Long balance = paymentService.payBillsByProvider(choices[1]);
                            if (!CommonService.isNull(balance)) {
                                System.out.println("Your current balance is: " + balance);
                            } else
                                System.out.println("Input is not correct!");
                        }
                    }
                    // Handle LIST_PAYMENT option: displays a list of payment history
                    case MenuPayment.LIST_PAYMENT -> {
                        Set<BillDTO> lisBillDTOS = paymentService.getHistory();
                        if (!CommonService.listIsNull(lisBillDTOS)) {
                            BillDTO.printBillList(lisBillDTOS);
                        }
                    }
                    case MenuPayment.EXIT -> System.out.println("You chose EXIT");
                    default -> System.out.println("Invalid choice, please try again.");
                }

                System.out.println(); // Print a blank line for readability
            }
        } while (!choice.equals(MenuPayment.EXIT));

        scanner.close();
        System.out.println("Application has exited.");


    }

    public static Boolean validateInputBill(String[] input) {

        if (input.length == 6) {
            if (CommonService.isPositiveInteger(input[1])) {
                return true;
            }
        }
        System.out.println("Input is not correct!");
        return false;
    }
}
