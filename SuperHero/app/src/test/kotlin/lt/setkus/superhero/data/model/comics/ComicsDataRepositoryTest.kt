package lt.setkus.superhero.data.model.comics

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import lt.setkus.superhero.data.http.ComicsService
import lt.setkus.superhero.data.model.Image
import lt.setkus.superhero.domain.Result
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue
import lt.setkus.superhero.domain.comics.Comic as ComicDomain

const val HERO_ID = 1
const val COMIC_ID = 2
const val COMIC_TITLE = "Be ware of mighty Hulk"
const val COMIC_DESCRIPTION = "Hulk got affected by radiation"
const val COMIC_THUMBNAIL = "thumbnail"
const val COMIC_THUMBNAIL_EXTENSION = "jpg"

class ComicsDataRepositoryTest {

    private val image = Image(COMIC_THUMBNAIL, COMIC_THUMBNAIL_EXTENSION)
    private val mockSuccessfulService = mockk<ComicsService>()
    private val erroneousService = mockk<ComicsService>()

    private lateinit var comicsDataRepository: ComicsDataRepository

    private val comics = listOf(Comic(COMIC_ID, COMIC_TITLE, COMIC_DESCRIPTION, image))

    @Before
    fun setUp() {
        every {
            mockSuccessfulService.getComics(HERO_ID)
        } returns CompletableDeferred(ComicDataWrapper(ComicDataContainer(comics)))

        coEvery {
            erroneousService.getComics(HERO_ID).await()
        } throws Exception()
    }

    @Test
    fun `when services successfully returns result then should map to Comics list`() {
        comicsDataRepository = ComicsDataRepository(mockSuccessfulService)
        runBlocking {
            val result = comicsDataRepository.loadComicsByHero(HERO_ID) as Result.Success
            assertThat(result.data).contains(
                ComicDomain(COMIC_ID, COMIC_TITLE, COMIC_DESCRIPTION, "$COMIC_THUMBNAIL.$COMIC_THUMBNAIL_EXTENSION")
            )
        }

        verify { mockSuccessfulService.getComics(HERO_ID) }
    }

    @Test
    fun `when error occurs on loading comics then should return error result`() {
        comicsDataRepository = ComicsDataRepository(erroneousService)
        runBlocking {
            val result = comicsDataRepository.loadComicsByHero(HERO_ID)
            assertTrue(result is Result.Error)
        }

        verify { erroneousService.getComics(HERO_ID) }
    }
}