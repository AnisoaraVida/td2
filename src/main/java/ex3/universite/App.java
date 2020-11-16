package ex3.universite;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Map.*;
import static java.util.Map.of;
import static java.util.Set.*;
import static java.util.Set.of;

public class App {
    public static void afficheSi(String entete, Predicate<Etudiant> condition, Annee annee){
        StringBuilder rtr = new StringBuilder();
        rtr.append(String.format("** %s\n",entete));
        for(Etudiant es : annee.etudiants()) {
            if (condition.test(es)) {
                rtr.append(es);
            }
        }
        rtr.toString();
        System.out.println(rtr);
    }
    public static void afficheSi2(String entete, Predicate<Etudiant> condition, Annee annee){
        StringBuilder rtr = new StringBuilder();
        rtr.append(String.format("** %s\n",entete));

        annee.etudiants().forEach( e ->{
            if(condition.test(e)){
                rtr.append(e);
            }
        });
        rtr.toString();
        System.out.println(rtr);

    }


    public static final Set<Matiere> toutesLesMatieresAnnee(Annee a){
        Set<Matiere> rtr = new HashSet<>();
        for(UE ue : a.ues()){
            for(Matiere m : ue.ects().keySet()){
                rtr.add(m);

            }
        }
        return rtr;

        /*OU pour la boucle
         * for(UE ue : a.ues()){
         * rtr.addAll(ue.ects().keySet());
         * }
         *
         * */

    }
    /*
    public static final Set<Double> toutesLesNotesAnnee(Annee a) {
        Set<Double> rtr = new HashSet<Double>(){};
        for (Matiere m : a.) {
            for (Matiere m : ue.ects().keySet()) {
               rtr.add();
            }
        }
        return rtr;
    }
*/

    public static final Predicate<Etudiant> defaillant = e -> {
        Set<Matiere> toutesLesMatieresDeLetudiant =
                App.toutesLesMatieresAnnee(e.annee());
        for(Matiere m : toutesLesMatieresDeLetudiant){
            if(! e.notes().containsKey(m)){
                return true;
            }
        }
        return false;
    };

    public static final Predicate<Etudiant> noteEliminatoire = e -> {
        for(double note : e.notes().values()){
            if(note < 6) {
                return true;
            }
        }
        return false;
    };

    public static void question3_1() {
        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1); e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        //System.out.println(e1);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1); e2.noter(m1, 14.0);
        e2.noter(m3, 14.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1); e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);
        a1.etudiants();
        Predicate<Etudiant> tousLesEtudiants = etudiant -> true;
        afficheSi("TOUS LES ETUDIANTS", tousLesEtudiants, a1);
        //afficheSi2("TOUS LES ETUDIANTS", tousLesEtudiants, a1);
        afficheSi("ETDUDIANT DEF", defaillant, a1);
        afficheSi("ETUDIANTS AVEC NOTE ELIMINATOIRE", noteEliminatoire, a1);
    }

    public static void main(String[] args) {
        question3_1();
    }
}

