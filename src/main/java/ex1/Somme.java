package ex1;

@FunctionalInterface
public interface Somme<T> {
    int somme(T t1, T t2);

    Somme<Integer> ex1 = (Integer t1, Integer t2) -> t1+t2;

    Somme<Integer> nb2 =
            new Somme<Integer>() {
                @Override
                public int somme(Integer t1, Integer t2) {
                    return t1+t2;
                }
            };
    Somme<String> ex2 = (String s1, String s2) -> {
        StringBuilder sb = new StringBuilder();
        sb.append(s1);
        sb.append(s2);
        sb.toString();
        return 0;
    };

}



