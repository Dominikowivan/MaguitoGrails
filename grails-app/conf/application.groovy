//Lo primero que hacemos es agregar las dependencias nuevas al build.graddle

//    compile 'org.grails.plugins:spring-security-core:3.2.1'
//    compile 'org.grails.plugins:spring-security-rest:2.0.0.RC1'

// despues de eso, en la consola de grails corremos el siguiente comando:

//  s2-quickstart nombreDelPaqueteDondeEstanTusClasesDeSeguridad NombreDelActor Role


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'myapp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'myapp.UserRole'
grails.plugin.springsecurity.authority.className = 'myapp.Role'

//Se agrega esta linea
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

//Se cambia "controllerAnnotations.staticRules" por "interceptUrlMap"
grails.plugin.springsecurity.interceptUrlMap = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	// se agrega esto para indicar que se necesita estar autenticado para acceder
	[pattern: '/api/login',            access: ['permitAll']],
	[pattern: '/api/logout',           access: ['isFullyAuthenticated()']],
	[pattern: '/showpersonajes',       access: ['permitAll']],
	[pattern: '/dungeoneros/**',       access: ["hasAnyRole('ROLE_DUNGEONERO_USER')"]],

	[pattern: '/deletepersonaje/**',        access:  ["hasRole('ROLE_ADMIN_USER')"]],

	[pattern: '/personajes',           access: ['permitAll']],
	[pattern: '/personajes/**',        access:  ["hasAnyRole('ROLE_AVENTURERO_USER')"]],
	[pattern: '/usuario',              access: ['permitAll']],
	[pattern: '/usuario/personajes',     access:  ["hasAnyRole('ROLE_AVENTURERO_USER')"]],
	[pattern: '/usuario/**',     access:  ["hasAnyRole('ROLE_AVENTURERO_USER')"]]

	// si queres que to do este autenticado escribis es[pattern: '/**',             access: ['isFullyAuthenticated()']]

]

grails.plugin.springsecurity.filterChain.chainMap = [

		//ojo aca, las declaraciones son de forma jerarquica. Primero lo especifico y despues lo generico
		// Si tenes personajes/algo, primero declaras el acceso a /personajes, y despues el de personajes/algo
		//fijate que los que tienen permiso se declaran con diferentes filtros a los que necesitan autenticacion
		[pattern: '/showpersonajes',       filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter' ],
		[pattern: '/usuario',              filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter' ],

		[pattern: '/dungeoneros/**',       filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'  ],
		[pattern: '/personajes'   ,        filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'  ],
		[pattern: '/usuario/**'   ,        filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'  ],
		[pattern: '/personajes/**',        filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'  ],

		[pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]


// Hace falta agregar esto para que no rompa:
grails.plugin.springsecurity.rest.token.storage.jwt.secret =	'qrD6h8K6S9503Q06Y6Rfk21TErImPYqa'

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
