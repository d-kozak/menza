package io.dkozak.menza

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

data class Menza(var id: Long, var name: String, var meals: MutableMap<Long, Meal> = mutableMapOf())

data class Meal(var id: Long, var name: String, var price: Int)


fun getDefaultMenzas() = mutableMapOf(
        1L to Menza(1L, "Stary pivovar", mutableMapOf(
                1L to Meal(1L, "rizek", 50),
                2L to Meal(2L, "steak", 65)
        )),
        2L to Menza(2L, "PPV", mutableMapOf(
                1L to Meal(1L, "recky salat", 50),
                2L to Meal(2L, "pizza", 65)
        ))
)

@Service
class MenzaService {

    private val menzas = getDefaultMenzas()

    fun addMenza(m: Menza) {
        menzas[m.id] = m
    }

    fun listMenzas() = menzas.values

    fun updateMenza(m: Menza) {
        menzas[m.id] = m
    }

    fun removeMenza(id: Long) {
        menzas.remove(id)
    }

    fun addMeal(menzaId: Long, meal: Meal) {
        val menza = getMenzaById(menzaId)
        menza.meals[meal.id] = meal
    }

    fun updateMeals(menzaId: Long, meal: Meal) {
        val menza = getMenzaById(menzaId)
        menza.meals[meal.id] = meal
    }

    fun listMeals(menzaId: Long): MutableMap<Long, Meal> {
        val menza = getMenzaById(menzaId)
        return menza.meals
    }

    fun removeMeal(menzaId: Long, mealId: Long) {
        val menza = getMenzaById(menzaId)
        menza.meals.remove(mealId)
    }

    fun findMenza(id: Long): Menza? = menzas[id]

    private fun getMenzaById(menzaId: Long) =
            menzas[menzaId] ?: throw IllegalArgumentException("Unknown menzaid $menzaId")
}

@RestController
@RequestMapping("/menza")
class MenzaController(private val menzaService: MenzaService) {

    @GetMapping
    fun listMenza() = menzaService.listMenzas()

    @GetMapping("/{id}")
    fun oneMenza(@PathVariable id: Long) = menzaService.findMenza(id)

    @PostMapping("/{id}")
    fun addMenza(@RequestBody m: Menza) = menzaService.addMenza(m)

    @PutMapping("/{id}")
    fun updateMenza(@RequestBody m: Menza) = menzaService.updateMenza(m)

    @DeleteMapping("/{id}")
    fun deleteMenza(@PathVariable id: Long) = menzaService.removeMenza(id)

    @GetMapping("/{menzaId}/meal")
    fun getMeals(@PathVariable menzaId: Long) = menzaService.listMeals(menzaId)

    @PostMapping("/{menzaId}/meal/{mealId}")
    fun addMeal(@PathVariable menzaId: Long, @RequestBody meal: Meal) = menzaService.addMeal(menzaId, meal)

    @PutMapping("/{menzaId}/meal/{mealId}")
    fun updateMeal(@PathVariable menzaId: Long, @RequestBody meal: Meal) = menzaService.updateMeals(menzaId, meal)

    @DeleteMapping("/{menzaId}/meal/{mealId}")
    fun removeMeal(@PathVariable menzaId: Long, @PathVariable mealId: Long) = menzaService.removeMeal(menzaId, mealId)
}

@SpringBootApplication
class MenzaApplication

fun main(args: Array<String>) {
    runApplication<MenzaApplication>(*args)
}
