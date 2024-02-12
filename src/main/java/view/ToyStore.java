package view;

import mapping.dtos.ToyStoreDTO;
import model.Type;
import services.ToyStoreImpl;
import services.implementacion.ToyServiceImpl;

import java.util.Scanner;

public class ToyStore {
    public static void main(String[] args) throws Exception {
        String op = "1";
        ToyStoreImpl impl = new ToyServiceImpl();

        do {
            Scanner s = new Scanner(System.in);
            System.out.println("~~~MENU~~~");
            System.out.println("1.Add toy \n2.List toys \n3.Max toy \n4.Min toy \n5.Sort toys \n6.Show by Types\n7.Show Toys Above\n8.Exit" +
                    "\n9.Search \n10.delete \n11.Show total amount of Toys");
            op = s.next();
            switch (op) {
                case "1" -> {
                    System.out.println("~~~ADD TOY~~~");
                    System.out.println("Enter the name");
                    String name = s.next();
                    System.out.println("Choose the type: \n0.Female\n1.Male\n2.Unisex");
                    Type type = Type.getTypeByValue(Integer.parseInt(s.next()));
                    System.out.println("Enter the prize");
                    Integer prize = Integer.valueOf(s.next());
                    impl.addToy(new ToyStoreDTO(name, type, prize, +1));
                }
                case "2" -> {
                    System.out.println("~~~LIST TOYS~~~");
                    if (!impl.listToys().isEmpty()) {
                        System.out.println(impl.listToys());
                    } else {
                        System.out.println("There is not toys yet");
                    }
                }
                case "3" -> {
                    System.out.println("~~~MAX TOY~~~");
                    System.out.println(impl.maxToy());
                }
                case "4" -> {
                    System.out.println("~~~MIN TOY~~~");
                    System.out.println(impl.minToy());
                }
                case "5" -> {
                    System.out.println("~~~SORT TOYS~~~");
                    System.out.println(impl.sort(impl.listToys()));

                }
                case "6" -> {
                    System.out.println("~~~SHOW BY TYPES~~~");
                    System.out.println(impl.showByType());
                }
                case "7" -> {
                    System.out.println("~~~SHOW TOYS ABOVE~~~");
                    System.out.println("Enter the number");
                    double number = Integer.parseInt(s.next());
                    System.out.println(impl.showToysAbove(number));
                }
                case "9" -> {
                    System.out.println("~~~SEARCH~~~");
                    System.out.println("Enter the toy name");
                    String name = s.next();
                    System.out.println(impl.search(name));
                }
                case "10" -> {
                    System.out.println("~~~DELETE~~~");
                    System.out.println("Enter the toy name");
                    String name = s.next();
                    System.out.println("Deleting " + impl.search(name));
                    impl.deleteToys(name);
                }
                case "11"->{
                    System.out.println("~~~SHOW TOTAL AMOUNT OF TOYS~~~");
                    System.out.println("In the store are "+impl.totalToys());
                }
            }
        } while (!op.equals("8")) ;
    }
}
