import java.util.*;
class ClasificadorFrutas {
    public static void main(String[] args) {
        List<Fruta> entrenamiento = Arrays.asList(
            new Fruta(1, 100, "Manzana"),
            new Fruta(2, 120, "Manzana"),
            new Fruta(3, 130, "Manzana"),
            new Fruta(1, 150, "Naranja"),
            new Fruta(2, 160, "Naranja"),
            new Fruta(3, 170, "Naranja")
        );
        String[] etiquetas = {"Manzana", "Naranja"};
        Fruta frutaDesconocida = new Fruta(2, 140, "");
        String etiquetaPredicha = predecir(etiquetas, entrenamiento, frutaDesconocida);
        System.out.println("La fruta es una: " + etiquetaPredicha);
    }
    static String predecir(String[] etiquetas, List<Fruta> entrenamiento, Fruta fruta) {
        return entrenamiento.stream()
            .collect(
                () -> new HashMap<String, Double>(),
                (map, f) -> {
                    map.putIfAbsent(f.etiqueta, 0.0);
                    map.put(f.etiqueta, map.get(f.etiqueta) + Math.log(f.probabilidad(fruta)));
                },
                (map1, map2) -> map2.forEach((k, v) -> map1.merge(k, v, Double::sum))
            )
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();
    }
}
class Fruta {
    int color, tamaño;
    String etiqueta;
    Fruta(int color, int tamaño, String etiqueta) {
        this.color = color;
        this.tamaño = tamaño;
        this.etiqueta = etiqueta;
    }
    double probabilidad(Fruta fruta) {
        double probColor = Math.abs(color - fruta.color) / 2.0;
        double probTamaño = Math.abs(tamaño - fruta.tamaño) / 20.0;
        return 1.0 / (1 + probColor * probTamaño);
    }
}
