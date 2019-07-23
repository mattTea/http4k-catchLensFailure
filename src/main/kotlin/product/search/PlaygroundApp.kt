package product.search

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun PlaygroundServer(port: Int): Http4kServer = PlaygroundApp().asServer(Jetty(port))

fun PlaygroundApp(): HttpHandler = CatchLensFailure.then(
    routes(
        "/ping" bind GET to { _: Request -> Response(OK) },
        "/products" bind GET to { request: Request ->
            val productId = Query.optional("productid")(request)
            println("Product ID: ${productId}")
            Response(OK).body(productId.toString())
        }
    )

)
