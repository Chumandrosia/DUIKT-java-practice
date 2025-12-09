import java.util.ArrayList;
import java.util.List;

public class ProductSearch {
    private List<Product> allProducts;

    public ProductSearch() {
        this.allProducts = new ArrayList<>();
    }

    public void addProduct(Product product) {
        allProducts.add(product);
    }

    public List<Product> searchByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            System.out.println("Пошуковий запит не може бути порожнім.");
            return new ArrayList<>();
        }
        String searchLower = searchTerm.toLowerCase().trim();
        List<Product> results = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchLower)) {
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> searchByCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("Назва категорії не може бути порожньою.");
            return new ArrayList<>();
        }
        String categoryLower = categoryName.toLowerCase().trim();
        List<Product> results = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getCategory().getName().toLowerCase().contains(categoryLower)) {
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            System.out.println("Некоректний ціновий діапазон.");
            return new ArrayList<>();
        }
        List<Product> results = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> searchByNameOrCategory(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            System.out.println("Пошуковий запит не може бути порожнім.");
            return new ArrayList<>();
        }
        String searchLower = searchTerm.toLowerCase().trim();
        List<Product> results = new ArrayList<>();
        for (Product product : allProducts) {
            boolean matchesName = product.getName().toLowerCase().contains(searchLower);
            boolean matchesCategory = product.getCategory().getName().toLowerCase().contains(searchLower);
            if (matchesName || matchesCategory) {
                if (!results.contains(product)) {
                    results.add(product);
                }
            }
        }
        return results;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts);
    }

    public List<Product> sortByPriceAsc(List<Product> products) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        return sorted;
    }

    public List<Product> sortByPriceDesc(List<Product> products) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        return sorted;
    }

    public void displaySearchResults(List<Product> results, String searchType) {
        if (results.isEmpty()) {
            System.out.println("За запитом \"" + searchType + "\" нічого не знайдено.");
            return;
        }
        System.out.println("\n=== РЕЗУЛЬТАТИ ПОШУКУ ===");
        System.out.println("Знайдено товарів: " + results.size());
        System.out.println("-------------------------");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }
        System.out.println("=========================\n");
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        for (Product product : allProducts) {
            String categoryName = product.getCategory().getName();
            if (!categories.contains(categoryName)) {
                categories.add(categoryName);
            }
        }
        return categories;
    }
}
