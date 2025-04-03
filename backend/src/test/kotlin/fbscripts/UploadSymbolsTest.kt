import com.g5.fbscripts.UploadSymbols
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UploadSymbolsTest {
 private lateinit var uploader: UploadSymbols

 @Before
 fun setUp() {
  uploader = UploadSymbols()
 }

 @Test
 fun `Uploads Symbol Successfully`() = runBlocking {
  //uploader.uploadSymbols()
 }
}