package dev.joshhalvorson.jobapptracker.util

class AddApplicationFormValidator  {
    companion object {
        val BOTH_FIELDS_WRONG_FLAG = listOf("Enter a company name", "Enter date applied")
        const val COMPANY_NAME_WRONG_FLAG = "Enter a company name"
        const val DATE_WRONG_FLAG = "Enter date applied"

        fun validateAddApplicationFormFields(
            companyName: String,
            dateApplied: String
        ): List<String>? {
            var companyNameWrong = false
            var dateAppliedWrong = false

            if (companyName == "") {
                companyNameWrong = true
            }

            // TODO("Make sure date is in the right pattern")
            if (dateApplied == "") {
                dateAppliedWrong = true
            }

            return if (companyNameWrong && dateAppliedWrong) {
                BOTH_FIELDS_WRONG_FLAG
            } else if (companyNameWrong) {
                listOf(COMPANY_NAME_WRONG_FLAG)
            } else if (dateAppliedWrong) {
                listOf(DATE_WRONG_FLAG)
            } else {
                null
            }
        }
    }
}