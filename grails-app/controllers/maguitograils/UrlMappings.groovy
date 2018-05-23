package maguitograils

class UrlMappings {

    // mas info sobre url mappings en: http://docs.grails.org/latest/guide/theWebLayer.html#urlmappings
    static mappings = {
        delete "/$controller/$id(.$format)?"  (action:"delete")
        get    "/$controller(.$format)?"      (action:"index")
        get    "/$controller/$id(.$format)?"  (action:"show")
        post   "/$controller(.$format)?"      (action:"save")
        put    "/$controller/$id(.$format)?"  (action:"update")
        patch  "/$controller/$id(.$format)?"  (action:"patch")

        "/"(controller: 'application', action:'index')

        // Esto de arriba es la definicion de mapeos genericos que ya vienen dados por grails

        /*
               "/personajes"(resources:'personaje')


                Esto solo dice que en vez de utilizar la url "http://server:puerto/personaje"
                va a usar "http://server:puerto/personajes"

                escribir eso solo a secas es equivalente a escribir todo esto:

                   get    "/personajes"         (controller:"personaje", action:"index")
                   get    "/personajes/create"  (controller:"personaje", action:"create")
                   post   "/personajes"         (controller:"personaje", action:"save")
                   get    "/personajes/$id"     (controller:"personaje", action:"show")
                   get    "/personajes/$id/edit"(controller:"personaje", action:"edit")
                   put    "/personajes/$id"     (controller:"personaje", action:"update")
                   delete "/personajes/$id"     (controller:"personaje", action:"delete")

               */


        get    "/showpersonajes"                              (controller:"personaje", action:"index")
        post   "/dungeoneros/$id/itemDeBienvenida"            (controller:"personaje", action:"nuevoEquipamiento")

        get    "/personajes/create"  (controller:"personaje", action:"create")
        post   "/personajes"         (controller:"personaje", action:"save")
        get    "/personajes/$id"     (controller:"personaje", action:"show")
        get    "/personajes/$id/edit"(controller:"personaje", action:"edit")
        put    "/personajes/$id"     (controller:"personaje", action:"update")
        delete "/deletepersonaje/$id"     (controller:"personaje", action:"delete")

        get  "/personajes/name/$alias"                     (controller:"personaje", action:"showbyAlias")
        post "/personajes/$id/items"                       (controller:"personaje", action:"agarrarItem")

        post "/usuario/personajes"                         (controller:"personaje", action:"saveEnUsuario")
        post "/usuario"                                    (controller:"personaje", action:"registrarUsuario")
        get "/usuario/$id"                                (controller:"personaje", action:"showPersonajeDeUsuario")


        //aca especificas el mapeo de un error. Si devolves status:404, te va a mapear ese codigo a notfound.

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
