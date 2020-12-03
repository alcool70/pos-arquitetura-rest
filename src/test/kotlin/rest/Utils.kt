package rest


fun mapOfQueryParams(query: String): Map<String, String> {
    val params = query.split('&').toTypedArray()
    var map: Map<String, String> = mutableMapOf()

    params.map {
        val nv = it.split('=')
        map += Pair(nv[0], nv[1])
    }

    return map
}