import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.hnikt.api.requests.FormRequest
import no.hnikt.domain.*
import no.hnikt.setup
import no.hnikt.setupPlugins
import org.junit.Assert
import kotlin.test.Test

class MiniFormRoutesTest {

    @Test
    fun `pong`() = testApplication {
        application {
            setup()
        }

        val newFormRequest: FormRequest = FormRequest(
            Ssn("1234578910"), MiniForm(
                Age(69), PainLevel.SeverePain, Medicated.Several, listOf("cocaine", "weed", "420blazeit")
            )
        )

        val json = Json.encodeToString(newFormRequest)

        val response = client.post("/mini") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }

        Assert.assertEquals("Good job", response.bodyAsText())
    }
}