package es.daw.foodexpressmvc.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private int number;          // Número de página actual
    private int size;            // Tamaño de página
    private long totalElements;  // Total de elementos en BD
    private int totalPages;      // Total de páginas
    private boolean first;
    private boolean last;
}
