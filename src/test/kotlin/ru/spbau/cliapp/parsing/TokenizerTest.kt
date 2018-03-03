package ru.spbau.cliapp.parsing

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test

class TokenizerTest {
    val tokenizer = Tokenizer()

    @Test
    fun `empty string tokenized to empty list`() {
        assertTrue(tokenizer.tokenize("").isEmpty())
    }

    @Test
    fun `one command is tokenized into string token`() {
        assertEquals(listOf(StringToken("echo")), tokenizer.tokenize("echo"))
    }

    @Test
    fun `many commands are tokenised into correct tokens`() {
        assertEquals(
                listOf(StringToken("echo"), StringToken("hello"), StringToken("one"), StringToken("two")),
                tokenizer.tokenize("echo hello one two")
        )
    }

    @Test
    fun `many spaces between words ignored`() {
        assertEquals(
                listOf(StringToken("echo"), StringToken("hello")),
                tokenizer.tokenize("     echo     hello    ")
        )
    }

    @Test
    fun `double quotes string is found`() {
        assertEquals(listOf(DoubleQuotesToken("echo")), tokenizer.tokenize("\"echo\""))
    }

    @Test
    fun `pipe symbol is recognized`() {
        assertEquals(listOf(VerticalBar), tokenizer.tokenize("|"))
    }

    @Test
    fun `pipe is recognised with other things`() {
        assertEquals(
                listOf(StringToken("echo"), DoubleQuotesToken("hello world"), VerticalBar, StringToken("cat"), SingleQuotesToken("-v")),
                tokenizer.tokenize("echo \"hello world\" | cat '-v'")
        )
    }
}