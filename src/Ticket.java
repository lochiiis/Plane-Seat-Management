import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(String row,int seat,double price,Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow(){
        return row;
    }
    public void setRow(String row){
        this.row= this.row;
    }
    public int getSeat(){
        return seat;
    }

    public void setSeat(int seat){
        this.seat=seat;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public Person getPerson(){
        return person;
    }
    public void setPerson(Person person){
        this.person=person;
    }

    public void TicketInfo(){
        System.out.println("Ticket Information:");
        System.out.println("Row : "+row);
        System.out.println("Seat : "+seat);
        System.out.println("Price : "+price);
        System.out.println("Person Information:");
        person.printInfo();
    }
    public void save(){  //save to text file
        String fileName=row+ Integer.toString(seat) +".txt";
        try{
            FileWriter file = new FileWriter(fileName);
            file.write("Ticket Information");
            file.write("\nRow: "+row);
            file.write("\nSeat: "+seat);
            file.write("\nPrice: £"+price);
            file.write("\nName: "+person.getName());
            file.write("\nSurname: "+person.getSurname());
            file.write("\nEmail: "+person.getEmail());
            file.close();
        }catch(IOException e){
            System.out.println("error while writing in the file");
        }
    }


}
