package reynold.test.budgetservice

interface BudgetRepo {
    fun getAll(): List<Budget>
}