// package com.app.restaurant.repository.stock;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.app.restaurant.model.master.Unit;

// @Repository
// public interface UnitRepository extends JpaRepository<Unit, Integer> {
//     Optional<Unit> findByNameAndStatusTrue(String name);
//     List<Unit> findByStatusTrue();
// }