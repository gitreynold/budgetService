package reynold.test.budgetservice

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class BudgetServiceTest {

    private lateinit var budgetService: BudgetService

    @Before
    fun setup() {
        budgetService = BudgetService()
    }

    @Test
    fun test_end_date_before_start_date_return_zero() {
        val amount = budgetService.totalAmount(LocalDate.of(2023, 8, 2), LocalDate.of(2023, 8, 1))
        assertEquals(BigDecimal(0), amount)
    }

    @Test
    fun test_no_data() {
        val amount = budgetService.totalAmount(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 2))
        assertEquals(BigDecimal(0), amount)
    }

    @Test
    fun test_half_data() {
        val amount = budgetService.totalAmount(LocalDate.of(2023, 1, 31), LocalDate.of(2023, 2, 1))
        assertEquals(BigDecimal(110), amount)
    }

    @Test
    fun test_half_two_month_data() {
        val amount = budgetService.totalAmount(LocalDate.of(2023, 1, 31), LocalDate.of(2023, 3, 1))
        assertEquals(BigDecimal(1380), amount)
    }

    @Test
    fun test_half_month_data() {
        val amount = budgetService.totalAmount(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15))
        assertEquals(BigDecimal(1500), amount)
    }
}