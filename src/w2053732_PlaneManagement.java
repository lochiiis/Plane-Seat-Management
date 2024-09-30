import java.util.*;
public class w2053732_PlaneManagement {
    static double price = 0;
    static int[] rowA = new int[14];   //static arrays used to store seating details
    static int[] rowB = new int[12];
    static int[] rowC = new int[12];
    static int[] rowD = new int[14];
    static int[][] rows = {rowA, rowB, rowC, rowD};  // 2D array to reference each row(allow easy access)
    static String[][] ticketSoldA = new String[14][6];   // 2D  array to store ticket information for each seat respective of the row
    static String[][] ticketSoldB = new String[12][6];
    static String[][] ticketSoldC = new String[12][6];
    static String[][] ticketSoldD = new String[14][6];


    public static void main(String[] args) {  //main program
        System.out.println("\nWelcome to the Plane Management application");
        int option; //input option
        initialization(); //making all the seats available by making it O
        while (true) {
            System.out.println("\n");
            for (int i = 1; i < 28; i++) {
                System.out.print("* ");
            }
            System.out.println("\n*                  MENU OPTION                     *");
            for (int i = 1; i < 28; i++) {
                System.out.print("* ");
            }
            System.out.println("\n");
            System.out.println("""
                                1) Buy a seat
                                2) Cancel a seat
                                3) Find first available seat
                                4) Show seating plan
                                5) Print tickets information and total sales
                                6) Search ticket
                                0) Quit
                            """);
            for (int i = 1; i < 28; i++) {
                System.out.print("* ");
            }
            Scanner input = new Scanner(System.in);

            try {
                System.out.print("\nPlease select an option :");  //user option input
                option = input.nextInt();
                switch (option) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Exiting program");
                        System.exit(0);   //program termination
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option");
            }
        }
    }

    private static void initialization() {  //initializing all the seats to available
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                rows[i][j] = 0;
            }
        }
    }

    private static void buy_seat() {
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("Please enter the row letter (A-D) : ");
            String row = input.nextLine().toUpperCase();
            if (!row.matches("[A-D]")) {
                System.out.println("invalid row letter");
                return;
            }
            if (row.equals("A") | row.equals("B") | row.equals("C") | row.equals("D")) {
                if (row.equals("A") | row.equals("D")) { //displaying the prices relevant to the seats in relevant rows
                    System.out.println("""
                            prices of seats in rows[A,D]:
                            seats     prices
                             1-5       £200
                             6-9       £150
                             10-14     £180
                            """);
                } else {System.out.println("""
                            prices of seats in rows[B,C]:
                            seats     prices
                             1-5       £200
                             6-9       £150
                             10-12     £180
                            """);
                }
            }

            int rowNum = row.charAt(0) - 'A';// convert row numbers to their corresponding index
            System.out.println("Enter the seat number:"); //user seat input
            int seat = input.nextInt();

            if (seat < 1 || seat > rows[rowNum].length) {
                System.out.println("Invalid Seat number");
                return;
            }
            if (rows[rowNum][seat - 1] == 1) { //check if the seat is already sold
                System.out.println("Seat is already sold");
                return;
            }
            System.out.println("Personal information");
            System.out.println("Enter your Name : ");    //user detail input
            String name = input.next();
            System.out.println("Enter your Surname : ");
            String surname = input.next();
            String email;
            while (true) {
                System.out.println("Enter your email : ");
                email = input.next();
                if (!email.contains("@")) {  //email validation
                    System.out.println("email invalid.@ symbol should be included!");
                } else {
                    break;
                }
            }
            Person person = new Person(name, surname, email); //creates Person object
            String [][]ticketSold=null;  //initialize ticketSold array to null(used to reduce repeating of the same code block)
            switch(rowNum){
                case 0:
                    ticketSold=ticketSoldA;
                    break;
                case 1:
                    ticketSold=ticketSoldB;
                    break;
                case 2:
                    ticketSold=ticketSoldC;
                    break;
                case 3:
                    ticketSold=ticketSoldD;
                    break;
            }
            rows[rowNum][seat-1]=1; //mark the seat as sold in the row array
            //update ticketSold array with the seat information
            ticketSold[seat-1][0]=row;
            ticketSold[seat - 1][1] = String.valueOf(seat);
            price = calcPrice(seat);  //calculate price of the seat
            ticketSold[seat - 1][2] = String.valueOf(price);

            ticketInfo(row, seat, price, person, ticketSold); //call ticketInfo method and update ticket information
            Ticket t = new Ticket(row, seat, price, person); //create a new object Ticket
            t.save(); //save the details in the text file

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input type");
        }
    }

    private static double calcPrice(int seat) { //price of a ticket respective to the seat number
        if (seat >= 1 && seat <= 5) {
            return 200.0;

        } else if (seat >= 6 && seat <= 9) {
            return 150.0;
        } else if (seat >= 10 && seat <= 14) {
            return 180.0;
        }
        return 0.0;

    }

    private static void cancel_seat() {
        try {
            System.out.println("Please enter the row letter (A-D):");
            Scanner input = new Scanner(System.in);
            String row = input.nextLine().toUpperCase();
            int cancelNum = row.charAt(0) - 'A';     // convert row numbers to their corresponding index

            System.out.println("Enter the seat number:"); //user seat input
            int seat = input.nextInt();
            if (rows[cancelNum][seat - 1] == 1) { //check if the seat is already booked
                switch (row) {
                    case "A" -> {
                        for (int i = 0; i < 6; i++) { //set each element to null
                            ticketSoldA[seat - 1][i] = null;
                        }
                        rows[cancelNum][seat - 1] = 0;  //make the seat available

                        System.out.println("Seat " + row + seat + " successfully cancelled");
                    }
                    case "B" -> {
                        for (int i = 0; i < 6; i++) {
                            ticketSoldB[seat - 1][i] = null; //set each element to null
                        }
                        rows[cancelNum][seat - 1] = 0;  //make the seat available

                        System.out.println("Seat " + row + seat + " successfully cancelled");
                    }
                    case "C" -> {
                        for (int i = 0; i < 6; i++) {
                            ticketSoldC[seat - 1][i] = null; //set each element to null
                        }
                        rows[cancelNum][seat - 1] = 0;  //make the seat available

                        System.out.println("Seat " + row + seat + " successfully cancelled");
                    }
                    case "D" -> {
                        for (int i = 0; i < 6; i++) {
                            ticketSoldD[seat - 1][i] = null; //set each element to null
                        }
                        rows[cancelNum][seat - 1] = 0;  //make the seat available

                        System.out.println("Seat " + row + seat + " successfully cancelled");
                    }
                }

            } else if (rows[cancelNum][seat - 1] == 0) {  //display if the seat is not booked
                System.out.println("Seat is not booked.");
            }
        } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("invalid input");
        }


    }

    private static void find_first_available() { //give the first available seat
        for (int i = 0; i < rows.length; i++) { //check each row
            for (int j = 0; j < rows[i].length; j++) { //check each seat
                if (rows[i][j] == 0) {
                    char firstAvailable = (char) ('A' + i);  //calculate the ASCII value for 'A' and add index i to it(gives the row)
                    int seat = j + 1;
                    System.out.println("First available seat is " + firstAvailable + seat);
                    return;
                }
            }
        }
        System.out.println("Sorry,There are no seats available");
    }

    private static void show_seating_plan() { //display seating plan
        System.out.println("\nHere is the seating plan\n");
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                if (rows[i][j] == 0) {
                    System.out.print("O ");  //available seats
                } else {
                    System.out.print("X ");  //booked seats
                }
            }
            System.out.println();

        }
    }

    private static void print_tickets_info() {
        double totPrice = 0;
        System.out.println("Ticket information");

        for (int i = 0; i < rows.length; i++) { //loop for all rows
            int[] row = rows[i];
            String[][] ticketSold = null;

            switch (i) { //determine ticketSold array based on the row index
                case 0:
                    ticketSold = ticketSoldA;
                    break;
                case 1:
                    ticketSold = ticketSoldB;
                    break;
                case 2:
                    ticketSold = ticketSoldC;
                    break;
                case 3:
                    ticketSold = ticketSoldD;
                    break;
            }

            for (int j = 0; j < row.length; j++) {
                if (row[j] == 1) { //printing information
                    double price = Double.parseDouble(ticketSold[j][2]);
                    System.out.println("Seat: " + ticketSold[j][0] + ticketSold[j][1]);
                    System.out.println("Price: " + ticketSold[j][2]);
                    System.out.println("Name: " + ticketSold[j][3] + " "+ticketSold[j][4]);
                    System.out.println("Email: " + ticketSold[j][5]+"\n");
                    totPrice += price;
                }
            }
        }
        System.out.println("Total price : £ " + totPrice); //print total sales price
    }

    private static void search_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the row letter (A-D):"); //row user input
        String row = input.nextLine().toUpperCase();
        int rowNum=row.charAt(0) - 'A'; // convert row numbers to their corresponding index
        System.out.println("Enter the seat number:"); //seat user input
        int seat = input.nextInt();

        if(rows[rowNum][seat-1]==1){ //check if the seat is booked
            String [][]ticketSold=null; //initialize array
            switch(rowNum){
                case 0:
                    ticketSold=ticketSoldA;
                    break;
                case 1:
                    ticketSold=ticketSoldB;
                    break;
                case 2:
                    ticketSold=ticketSoldC;
                    break;
                case 3:
                    ticketSold=ticketSoldD;
                    break;
            }
            if(ticketSold[seat-1][0]!=null){ //check for information
                System.out.println("ticket information:"); //display information
                System.out.println("Row:"+ticketSold[seat-1][0]);
                System.out.println("Seat:"+ticketSold[seat-1][1]);
                System.out.println("Name:"+ticketSold[seat-1][3]);
                System.out.println("Surname:"+ticketSold[seat-1][4]);
                System.out.println("Email:"+ticketSold[seat-1][5]);
            }else{
                System.out.println("This seat is available.");
            }
        }else{
            System.out.println("This seat is available.");
        }
    }
    private static void ticketInfo(String row,int seat,double price,Person person,String [][]ticketSold){
        Ticket t= new Ticket(row, seat, price, person); //create Ticket object
        t.setPrice(price);
        t.setRow(row);
        t.setSeat(seat);

        t.TicketInfo(); //print ticket information

        person=t.getPerson(); //get person information

        ticketSold[seat-1][3]=person.getName();   //update ticket sold array with person information
        ticketSold[seat-1][4]=person.getSurname();
        ticketSold[seat-1][5]=person.getEmail();
    }
}