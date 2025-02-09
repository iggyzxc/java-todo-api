package org.iggyzxc.javatodoapi.repositories;

import org.iggyzxc.javatodoapi.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}