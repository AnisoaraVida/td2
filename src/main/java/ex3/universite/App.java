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

    public static void afficheSIV2(String entete, Predicate<Etudiant> condition, Annee annee, Affichage app) {
        StringBuilder rtr = new StringBuilder();
        rtr.append(String.format("** %s\n",entete));

        annee.etudiants().forEach( e ->{
            if(condition.test(e)){
                rtr.append(app.affichage(e));
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

    private static Moyenne moyenne = new Moyenne(){
        @Override
        public Double moyenne(Etudiant e) {
            Double rtr= null;
            double notes= 0;
            double coefficients= 0;
            if(!defaillant.test(e)){
                for(UE ue: e.annee().ues()){
                    for(Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                        Matiere mat = ects.getKey();
                        Integer etcs = ects.getValue();
                        notes += e.notes().get(mat) * etcs;
                        coefficients += etcs;
                    }
                }
                if(coefficients != 0) {
                    rtr = notes / coefficients;
                }
            }
            return rtr;
        }
    };



    public static final Predicate<Etudiant> naPasLaMoyennev1 =  e -> {
        if(moyenne.moyenne(e) < 10.00){
            return true;
        }
        return false;
    };

    public static final Predicate<Etudiant> naPasLaMoyennev2 = e -> {
        boolean rtr = false;
        if(defaillant.test(e)){
            rtr = true;
        }
        else {
            rtr = naPasLaMoyennev1.test(e);
        }
        return rtr;
    };

    public static final Predicate<Etudiant> session2v1 = e -> noteEliminatoire.or(defaillant).test(e);


    private static Affichage affiche1 = new Affichage(){
        @Override
        public String affichage(Etudiant x) {
            return x.toString();
        }
    };

    private static Affichage affiche2 = new Affichage(){
        @Override
        public String affichage(Etudiant x) {
            Double moyenne1 = moyenne.moyenne(x);
            return String.format("%s %s : %s \n", x.prenom(), x.nom(), moyenne1 != null ? moyenne1.toString() : "défaillant");
        }
    };

    private static Moyenne moyenneIndicative = new Moyenne(){
        @Override
        public Double moyenne(Etudiant e) {
            Double rtr= null;
            double notes= 0;
            double coefficients= 0;
                for(UE ue: e.annee().ues()){
                    for(Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                        Matiere mat = ects.getKey();
                        Integer etcs = ects.getValue();
                        if(e.notes().keySet().contains(mat)) {
                            notes += e.notes().get(mat) * etcs;
                        }
                        coefficients += etcs;
                    }
                }
                if(coefficients != 0) {
                    rtr = notes / coefficients;
                }
            return rtr;
            }
    };

    public static final Predicate<Etudiant> naPasLaMoyenneIndicative = e -> {
        if(moyenneIndicative.moyenne(e) < 10) {
            return true;
        }
        return false;
    };

    private static Affichage affiche3 = new Affichage(){
        @Override
        public String affichage(Etudiant e) {
            Double moyenne = moyenneIndicative.moyenne(e);
            return String.format("%s %s : %.2f\n", e.prenom(), e.nom(), moyenne);
        }
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
        System.out.println(moyenne.moyenne(e2));
        //afficheSi("ETUDIANTS SOUS LA MOYENNE (v1)", naPasLaMoyennev1, a1);
        afficheSi("ETUDIANTS SOUS LA MOYENNE (v2)", naPasLaMoyennev2, a1);
        afficheSi("ETUDIANTS EN SESSION 2", session2v1, a1);
        afficheSIV2("** TOUS LES ETUDIANTS (v1)", tousLesEtudiants, a1, affiche1);
        afficheSIV2("** TOUS LES ETUDIANTS (v2)", tousLesEtudiants, a1, affiche2);
        afficheSIV2("** TOUS LES ETUDIANTS (v2)", tousLesEtudiants, a1, affiche3);
        System.out.println(moyenneIndicative.moyenne(e2));
        afficheSIV2("** TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyenneIndicative, a1, affiche3);
    }

    public static void main(String[] args) {
        question3_1();
    }
}

