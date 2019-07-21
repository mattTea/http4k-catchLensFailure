package product.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PlaygroundServerTest : Spek({

    val client = OkHttp()
    val server = PlaygroundServer(0)

    beforeEachTest {
        server.start()
    }

    afterEachTest {
        server.stop()
    }

    describe("Server") {

        it("responds to ping") {

            val response = client(Request(GET, "http://localhost:${server.port()}/ping"))

            assertThat(response.status).isEqualTo(OK)
        }

    }

})