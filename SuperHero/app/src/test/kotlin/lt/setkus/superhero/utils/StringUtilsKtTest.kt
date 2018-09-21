package lt.setkus.superhero.utils

import org.junit.Assert.*
import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun `should create correct md5 from string`() {
        assertEquals("47bce5c74f589f4867dbd57e9ca9f808", "aaa".md5())
    }
}