//package view;
//
//import mapping.dtos.ToyDTO;
//import model.ToyType;
//import services.ToyStoreInt;
//import services.exceptions.ToysException;
//import services.threads.Threads;
//import services.impl.toy.ToyServiceImpl;
//
//import java.util.ArrayList;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//import java.util.concurrent.CompletableFuture;
//
//public class ToyStore {
//    public static void main(String[] args) throws Exception {
//        ToyStoreInt impl = new ToyServiceImpl();
//        Threads threads = new Threads();
//        Scanner s = new Scanner(System.in);
//        String menu = """
//                🎲MENU🎲
//                1.���Add toy
//                2.��List toys
//                3.���Max toy
//                4.���Min toy
//                5.���Sort toys
//                6.��Show by Types
//                7.��Show Toys Above
//                8.��Search
//                9.��Show total amount of Toys
//                10.��Increase
//                11.��Decrease
//                12.��Show total all toys price
//                13.�����Exit
//                """;
//        int op = 0;
//        do {
//            System.out.println(menu);
//                try {
//                    op=s.nextInt();
//                    s.nextLine();
//                    switch (op) {
//                        case 1 -> {
//                            System.out.println("~~~ADD TOY~~~");
//                            System.out.println("Enter the name");
//                            String name = s.next();
//                            System.out.println("Choose the type: \n0.Female\n1.Male\n2.Unisex");
//                            ToyType type = ToyType.getTypeByValue(Integer.parseInt(s.next()));
//                            System.out.println("Enter the price");
//                            Integer prize = Integer.valueOf(s.next());
//                            impl.addToy(new ToyDTO(name, type, prize, +1));
//                        }
//                        case 2 -> {
//                            System.out.println("~~~LIST TOYS~~~");
//                            CompletableFuture<List<ToyDTO>> future = CompletableFuture.supplyAsync(() -> {
//                                List<ToyDTO> list = new ArrayList<>();
//                                try {
//                                    for (ToyDTO t : impl.listToys()) {
//                                        System.out.println(t);
//                                        list.add(t);
//                                        threads.waiting();
//                                    }
//                                } catch (ToysException e) {
//                                    System.out.println(e.getMessage());
//                                }
//                                return list;
//                            });
//                            future.get();
//                        }
//                        case 3 -> {
//                            System.out.println("~~~MAX TOY~~~");
//                            System.out.println(impl.maxToy());
//                        }
//                        case 4 -> {
//                            System.out.println("~~~MIN TOY~~~");
//                            System.out.println(impl.minToy());
//                        }
//                        case 5 -> {
//                            System.out.println("~~~SORT TOYS~~~");
//                            System.out.println(impl.sort());
//
//                        }
//                        case 6 -> {
//                            System.out.println("~~~SHOW BY TYPES~~~");
//                            System.out.println(impl.showByType());
//                        }
//                        case 7 -> {
//                            System.out.println("~~~SHOW TOYS ABOVE~~~");
//                            System.out.println("Enter the number");
//                            double number = Integer.parseInt(s.next());
//                            System.out.println(impl.showToysAbove(number));
//
//                        }
//                        case 8 -> {
//                                System.out.println("~~~SEARCH~~~");
//                                System.out.println("Enter the toy name");
//                                String name = s.next();
//                                System.out.println(impl.search(name));
//
//                        }
//                        case 9 -> {
//                            System.out.println("~~~SHOW TOTAL AMOUNT OF TOYS~~~");
//                            System.out.println("In the store are " + impl.totalToys());
//                        }
//                        case 10 -> {
//                            System.out.println("~~~INCREASE~~~");
//                            System.out.println("Enter the toy name");
//                            String name = s.next();
//                            System.out.println("Enter the amount");
//                            int amount = Integer.parseInt(s.next());
//                            ToyDTO toyStoreDTO = impl.search(name);
//                            System.out.println(impl.increase(toyStoreDTO, amount));
//                        }
//                        case 11 -> {
//                            System.out.println("~~~DECREASE~~~");
//                            System.out.println("Enter the toy name");
//                            String name = s.next();
//                            System.out.println("Enter the amount");
//                            int amount = Integer.parseInt(s.next());
//                            ToyDTO toyStoreDTO = impl.search(name);
//                            System.out.println(impl.decrease(toyStoreDTO, amount));
//                        }
//                        case 12 -> {
//                            System.out.println("~~~SHOW TOTAL ALL TOYS PRICE~~~");
//                            System.out.println(impl.totalPriceAllToys());
//                        }
//                    }
//                }catch (InputMismatchException e){
//                    System.out.println("Invalid format, please, enter a number");
//                    s.nextLine();
//                }
//        } while (op !=13) ;
//        s.close();
//    }
//}
