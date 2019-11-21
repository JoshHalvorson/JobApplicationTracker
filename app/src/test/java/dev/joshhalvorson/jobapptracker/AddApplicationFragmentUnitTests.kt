package dev.joshhalvorson.jobapptracker

import com.google.common.truth.Truth.assertThat
import dev.joshhalvorson.jobapptracker.util.AddApplicationFormValidator
import org.junit.Test

class AddApplicationFragmentUnitTests {
    @Test
    fun addApplicationFormValidator_CorrectApplication_ReturnsNull() {
        assertThat(
            AddApplicationFormValidator.validateAddApplicationFormFields(
                companyName = "Company Name",
                dateApplied = "11/21/2019"
            )
        ).isNull()
    }

    @Test
    fun addApplicationFormValidator_IncorrectApplication_BothFieldsWrong_ReturnsCompanyNameWrong_And_DateAppliedWrong_ErrorMessages() {
        assertThat(
            AddApplicationFormValidator.validateAddApplicationFormFields(
                companyName = "",
                dateApplied = ""
            )
        ).isEqualTo(AddApplicationFormValidator.BOTH_FIELDS_WRONG_FLAG)
    }

    @Test
    fun addApplicationFormValidator_IncorrectApplication_WrongCompanyName_ReturnsCompanyNameWrong_ErrorMessage() {
        assertThat(
            AddApplicationFormValidator.validateAddApplicationFormFields(
                companyName = "",
                dateApplied = "11/21/2019"
            )
        ).containsExactly(AddApplicationFormValidator.COMPANY_NAME_WRONG_FLAG)
    }

    @Test
    fun addApplicationFormValidator_IncorrectApplication_WrongDateApplied_ReturnsDateWrong_ErrorMessage() {
        assertThat(
            AddApplicationFormValidator.validateAddApplicationFormFields(
                companyName = "Company Name",
                dateApplied = ""
            )
        ).containsExactly(AddApplicationFormValidator.DATE_WRONG_FLAG)
    }
}