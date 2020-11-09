package ex1;

public class App {
    public static void main(String[] args) {
        Somme c1 = new Somme() {
            @Override
            public int somme(Object t1, Object t2) {
                return 0;
            }
        }
        System.out.println(new Somme(1, 2));
    }
}
