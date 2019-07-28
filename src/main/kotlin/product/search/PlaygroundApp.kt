package product.search

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.lens.Path
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

            Response(OK).body(productId.toString())
        },

        "/products/{id:.*}" bind GET to { request: Request ->
            val id = Path.of("id")
            val pathId = id.extract(request)
            var responseBody = request.toString()
            val matchedProduct = mutableListOf<Pair<Int, String>>()
            var response : Response

            for (product in products) if (product.first.toString() == pathId) {
                matchedProduct.add(product)
                responseBody += product.second
            }

            response = if (matchedProduct.size == 0) {
                Response(NOT_FOUND)
            } else {
                Response(OK).body(responseBody)
            }

            response
        }
    )

)
