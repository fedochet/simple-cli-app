package ru.spbau.cliapp.task.grep

import picocli.CommandLine
import picocli.CommandLine.Option
import java.util.*

/**
 * This class represents options for [GrepTask].
 */
class GrepParams {
    @Option(
            names = ["-i", "--ignore-case"],
            description = ["Ignore case distinctions, so that characters that differ only in case match each other."]
    )
    var ignoreCase = false

    @Option(
            names = ["-w", "--word-regexp"],
            description = ["Select  only  those  lines containing matches that form whole words."]
    )
    var wordRegexp = false

    @Option(
            names = ["-A", "--after-context"],
            description = ["Print NUM lines of trailing context after matching lines."]
    )
    var afterContext: Int? = null

    @CommandLine.Parameters(index = "0")
    var pattern: String = ""

    @CommandLine.Parameters(index = "1..*")
    var fileNames: Array<String> = emptyArray()

    override fun toString(): String {
        return "GrepParams(ignoreCase=$ignoreCase, wordRegexp=$wordRegexp, afterContext=$afterContext, pattern='$pattern', fileNames=${Arrays.toString(fileNames)})"
    }
}
