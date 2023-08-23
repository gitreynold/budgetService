package reynold.test.budgetservice

import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class BudgetService {
    private val budgetRepo: BudgetRepo by lazy {
        BudgetRepoImplement()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun totalAmount(startDate: LocalDate, endDate: LocalDate): BigDecimal {
        if (endDate.isBefore(startDate)) {
            return BigDecimal(0)
        }


        val budgetAll = budgetRepo.getAll()
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMM")
        val startDateFormat = YearMonth.from(startDate)
        val endDateFormat = YearMonth.from(endDate)
        var result = BigDecimal(0)
        budgetAll.forEach {
            if ((before(it.yearMonth, dateFormat, endDateFormat)) &&
                after(it.yearMonth, dateFormat, startDateFormat)
            ) {
                val yearMonth = YearMonth.parse(it.yearMonth, dateFormat)


                result += if (YearMonth.parse(
                        it.yearMonth,
                        dateFormat
                    ) == startDateFormat && startDate.month == endDate.month
                ) {
                    BigDecimal((it.amount / startDate.lengthOfMonth()) * (endDate.dayOfMonth - startDate.dayOfMonth + 1))
                } else if (isInRange(it, dateFormat, startDateFormat)) {
                    val resultAmount =
                        Math.abs((startDate.dayOfMonth - yearMonth.lengthOfMonth() + 1)) * ((it.amount) / yearMonth.lengthOfMonth())
                    BigDecimal(resultAmount)
                } else if (isInRange(it, dateFormat, endDateFormat)) {
                    val resultAmount =
                        ((endDate.dayOfMonth) * (it.amount) / yearMonth.lengthOfMonth())
                    BigDecimal(resultAmount)
                } else {
                    BigDecimal(it.amount)
                }
            }
        }

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isInRange(
        it: Budget,
        dateFormat: DateTimeFormatter?,
        endDateFormat: YearMonth?
    ) = YearMonth.parse(it.yearMonth, dateFormat).equals(endDateFormat)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun after(
        it: String,
        dateFormat: DateTimeFormatter?,
        startDateFormat: YearMonth?
    ) =
        (YearMonth.parse(it, dateFormat).isAfter(startDateFormat)) || YearMonth.parse(it, dateFormat)
            .equals(startDateFormat)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun before(
        it: String,
        dateFormat: DateTimeFormatter?,
        endDateFormat: YearMonth?
    ) =
        YearMonth.parse(it, dateFormat).isBefore(endDateFormat) || YearMonth.parse(it, dateFormat).equals(endDateFormat)
}