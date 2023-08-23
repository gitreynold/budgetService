package reynold.test.budgetservice

class BudgetRepoImplement : BudgetRepo {
    override fun getAll(): List<Budget> {
        return listOf(
            Budget("202301", 3100),
            Budget("202302", 280),
            Budget("202303", 31000),
            Budget("202304", 6000)
        )
    }
}