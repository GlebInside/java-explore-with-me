package ru.practicum.admin.categories.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.categories.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
