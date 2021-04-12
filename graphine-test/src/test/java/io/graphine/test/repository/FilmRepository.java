package io.graphine.test.repository;

import io.graphine.core.annotation.Repository;
import io.graphine.test.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Oleg Marchenko
 */
@Repository(Film.class)
public interface FilmRepository {
    Film findById(long id);
    Collection<Film> findAll();
    Collection<Film> findAllByYear(int year);
    Collection<Film> findAllByYearIsNot(int year);
    Collection<Film> findAllByBudgetBetween(long budget1, long budget2);
    Collection<Film> findAllByBudgetNotBetween(long budget1, long budget2);
    Collection<Film> findAllByBudgetLessThan(long budget);
    Collection<Film> findAllByBudgetLessThanEqual(long budget);
    Collection<Film> findAllByGrossGreaterThan(long gross);
    Collection<Film> findAllByGrossGreaterThanEqual(long gross);
    List<Film> findAllByTaglineIsEmpty();
    List<Film> findAllByTaglineIsNotEmpty();
    List<Film> findAllByTitleLike(String title);
    List<Film> findAllByTitleNotLike(String title);
    List<Film> findAllByTitleStartingWith(String title);
    List<Film> findAllByTitleEndingWith(String title);
    List<Film> findAllByTitleContaining(String title);
    List<Film> findAllByTitleNotContaining(String title);
    Set<Film> findAllByBudgetIsNull();
    Set<Film> findAllByGrossIsNotNull();
    Set<Film> findAllByWasReleasedIsTrue();
    Set<Film> findAllByWasReleasedIsFalse();
    Set<Film> findAllByYearIn(Collection<Integer> years);
    Set<Film> findAllByYearNotIn(int... years);
    Set<Film> findAllByBudgetGreaterThanEqualAndGrossGreaterThan(long budget, long gross);
    Set<Film> findAllByBudgetBetweenOrGrossBetween(long budget1, long budget2, long gross1, long gross2);
    long countAll();
    int countAllByYear(int year);
    void save(Film film);
    void saveAll(Film... films);
    void update(Film film);
    void updateAll(Film... films);
    void delete(Film film);
    void deleteById(long id);
    void deleteAll(Film... films);
    void deleteAllByYearIn(Set<Integer> years);
}
