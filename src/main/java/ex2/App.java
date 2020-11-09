package ex2;

import java.util.function.Function;
import java.util.function.Predicate;

public class App {
    public static void question2_1() {
        Paire<Integer, Double> Pierre = new Paire<>(90, 80.00);
        Predicate<Paire<Integer, Double>> taillepetite = p -> p.fst < 100;
        System.out.println(taillepetite.test(Pierre));

        Predicate<Paire<Integer, Double>> taillegrande = p -> p.fst > 200;
        System.out.println(taillegrande.test(Pierre));

         Predicate<Paire<Integer, Double>> taillecorrecte = taillepetite.and(taillegrande);
        System.out.println(taillecorrecte.test(Pierre));

        Predicate<Paire<Integer, Double>> tailleincorrecte = taillepetite.negate().or(taillegrande.negate());
        System.out.println(tailleincorrecte.test(Pierre));

        Predicate<Paire<Integer, Double>> taillelourd = p -> p.snd > 150.0;
        System.out.println(taillelourd.test(Pierre));

        Predicate<Paire<Integer, Double>> poidscorrecte = taillelourd;
        System.out.println(poidscorrecte.test(Pierre));

        Predicate<Paire<Integer, Double>> poidsincorrecte = taillelourd.negate();
        System.out.println(poidsincorrecte.test(Pierre));




    }

    public static void main(String[] args) {
        question2_1();
    }
}
