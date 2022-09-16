package mattmo.venusrestblog.repository;

import mattmo.venusrestblog.data.Category;
import mattmo.venusrestblog.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}