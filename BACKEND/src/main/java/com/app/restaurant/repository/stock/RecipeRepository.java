// package com.app.restaurant.repository.stock;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.app.restaurant.model.master.Item;
// import com.app.restaurant.model.master.ItemAddon;
// import com.app.restaurant.model.master.ItemVariation;
// import com.app.restaurant.model.stock.Recipe;

// @Repository
// public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
//     Optional<Recipe> findByItemAndItemVariationAndItemAddon(
//             Item item, ItemVariation itemVariation, ItemAddon itemAddon);
// }