package ru.spbau.cliapp.task.grep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GrepWorkerTest {
    @Test
    fun `worker returns empty list when nothing is matched`() {
        val worker = GrepWorker("cat")

        assertThat(worker.findMatches("")).isEmpty()
        assertThat(worker.findMatches("dog")).isEmpty()
        assertThat(worker.findMatches("1234")).isEmpty()
    }

    @Test
    fun `worker can find many occurences`() {
        val worker = GrepWorker("cat")

        assertThat(worker.findMatches("cat")).containsExactly(GrepMatch("cat", 0 until 3))

        assertThat(worker.findMatches("cat cat cat")).containsExactly(
                GrepMatch("cat", 0 until 3),
                GrepMatch("cat", 4 until 7),
                GrepMatch("cat", 8 until 11)
        )

        assertThat(worker.findMatches("catocat ocato")).containsExactly(
                GrepMatch("cat", 0 until 3),
                GrepMatch("cat", 4 until 7),
                GrepMatch("cat", 9 until 12)
        )
    }

    @Test
    fun `worker can work with regex pattern`() {
        val worker = GrepWorker("[abc]{3}8?")

        assertThat(worker.findMatches("cba8 abc8 bbb cck")).containsExactly(
                GrepMatch("cba8", 0 until 4),
                GrepMatch("abc8", 5 until 9),
                GrepMatch("bbb", 10 until 13)
        )
    }

    @Test
    fun `worker can ignore case`() {
        val worker = GrepWorker("cat", ignoreCase = true)

        assertThat(worker.findMatches("CAT")).containsExactly(GrepMatch("CAT", 0 until 3))
    }

    @Test
    fun `worker can take only full words`() {
        val worker = GrepWorker("cat", wordRegexp = true)

        assertThat(worker.findMatches("cat mocat catmo cat")).containsExactly(
                GrepMatch("cat", 0 until 3),
                GrepMatch("cat", 16 until 19)
        )
    }
}