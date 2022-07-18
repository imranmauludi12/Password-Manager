package com.demoapp.passwordmanager.core.utils

import com.abhinav.passwordgenerator.PasswordGenerator

object RandomPasswordGenerator {

    private const val DEFAULT_PSW_LENGTH = 8
    private const val INCLUDE_LOWERCASE = true

    fun generateNewRandomPassword(
        length: Int = DEFAULT_PSW_LENGTH,
        isUseUppercase: Boolean = false,
        isUseNumbers: Boolean = false,
        isUseSpecialChar: Boolean = false,
    ): String {
        return PasswordGenerator(
            length = length,
            includeLowerCaseLetters = INCLUDE_LOWERCASE,
            includeUpperCaseLetters = isUseUppercase,
            includeNumbers = isUseNumbers,
            includeSymbols = isUseSpecialChar
        ).generatePassword()
    }

}